package cn.brightasdream.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.json.simple.JSONObject;


/**
 * 处理客户端发过来的查询请求，将关键词词条化，找到对应的文档。
 */


public class QueryProcessor {

   
    private IdAndPersonSchema schema;

    
    private TokenSchemaManager manager;

    private int size = 10000000; //用于找出最短的索引链表
    // 用于进行合并的第一个LinkedList
    private LinkedList<IndexStructure> list = null;
    // 合并完成只有只有一个索引表，存整句话对应的文档索引
    private LinkedList<IndexStructure> mergedIndex = null;
    // 存id较大的索引项信息
    private IndexStructure structure = null;
    // 标记当前哪一个较大,1标识第一个较大，2表示第二个较大
    int mark = 0;

    /**
     * 根据查询字符串查询对应的信息，并添加加粗
     * @param queryStr
     * @return
     */

    public QueryProcessor(String filepath) {
        try {
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(filepath));

            manager = (TokenSchemaManager)input.readObject();
            schema = (IdAndPersonSchema)input.readObject();
            input.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
     }
    public LinkedList<JSONObject> getMappedPerson(String queryStr) {
        LinkedList<JSONObject> jsonList = new LinkedList<JSONObject>();
        LinkedList<String> tokenList = TokenParser.getTokenStringList(queryStr); // 获取词条化链表

        // 查询模式，1表示与查询，2表示或查询
        int queryMode = 1;
        Iterator<String> it = tokenList.iterator();
        while(it.hasNext()){
            String token = it.next();
            // 按照不同的关键字查询
            if(token.equals("OR") || token.equals("or")){
                queryMode = 2;
                it.remove();
            }else if(token.equals("AND") || token.equals("and")){
                it.remove();
            }
        }

        if(queryMode == 1){
            // 存储输入词条的词条表
            Map<String,LinkedList<IndexStructure>> map = new HashMap<String,LinkedList<IndexStructure>>();

            tokenList.forEach(new Consumer<String>(){

                @Override
                public void accept(String t) {
                    if(manager.getTokenIndex(t) != null)
                        map.put(t, manager.getTokenIndex(t));
                }

            });
            // 如果没有查询到，那么返回null
            if(map.isEmpty()) return null;

            mergePersonIndex(map);

            
            // 对查询到的索引进行加粗，并且合并同一个人的信息
            jsonList = strongAndMergeSamePerson();

            // 查询完毕，将list置空
            list = null;
            return jsonList;
        }else {
            // 存储输入词条的词条表
            Map<String,LinkedList<IndexStructure>> map = new HashMap<String,LinkedList<IndexStructure>>();

            tokenList.forEach(new Consumer<String>(){

                @Override
                public void accept(String t) {
                    if(manager.getTokenIndex(t) != null)
                        map.put(t, manager.getTokenIndex(t));
                }

            });
            // 如果没有查询到，那么返回null
            if(map.isEmpty()) return null;

            mergePersonIndexInOR(map);

            
            // 对查询到的索引进行加粗，并且合并同一个人的信息
            jsonList = strongAndMergeSamePerson();

            // 查询完毕，将list置空
            list = null;
            return jsonList;
        }
        
    }

    /**
     * 合并文档索引
     * @param map 查询词项对应的文档map
     * @return
     */    
    private LinkedList<IndexStructure> mergePersonIndex(Map<String,LinkedList<IndexStructure>> map){

        // 实例化
        list = new LinkedList<IndexStructure>();

        size = 10000000;
        // 第一次循环，找出第一个LinkedList
        map.forEach(new BiConsumer<String,LinkedList<IndexStructure>>(){

            @Override
            public void accept(String t, LinkedList<IndexStructure> u) {
                if(u.size() < size){
                    size = u.size();
                    list = u;
                }
            }
        });
        
        // 第二次循环，合并LinkedList
        map.forEach(new BiConsumer<String,LinkedList<IndexStructure>>(){

            @Override
            public void accept(String t, LinkedList<IndexStructure> u) {
                mergedIndex = new LinkedList<>();
                if(list == u) return; //如果相等，则不进行合并
                Iterator<IndexStructure> i1 = list.iterator();
                Iterator<IndexStructure> i2 = u.iterator();

                while(i1.hasNext() && i2.hasNext()){
                    IndexStructure structure1=null;
                    IndexStructure structure2=null;
                    // 如果是第一次进入或者是找到同一个person的信息，取下一个
                    if(structure == null && mark == 0){
                        structure1 = i1.next();
                        structure2 = i2.next();
                    }
                    // 如果1较大
                    if(mark == 1){
                        structure1 = structure;
                        structure2 = i2.next();
                    }else if(mark == 2){
                        structure2 = structure;
                        structure1 = i1.next();
                    }
                    // 比较下一个谁大
                    if(structure1.getPersonID().compareTo(structure2.getPersonID()) == 0){
                        // 获取拷贝
                        IndexStructure t1 = (IndexStructure)structure1.clone();
                        IndexStructure t2 = (IndexStructure)structure2.clone();
                        // 如果是同一个人的信息，加到表中
                        mergedIndex.add(t1);
                        mergedIndex.add(t2);
                        structure = null;
                        mark = 0;
                    }else if(structure1.getPersonID().compareTo(structure2.getPersonID()) < 0){
                        // 将较大的存起来
                        structure = structure2;
                        mark = 2;
                    }else{
                        structure = structure1;
                        mark = 1;
                    }
                }

                list = mergedIndex;
                mergedIndex = null;
                //本轮循环结束之后重置
                structure = null;
                mark = 0;
            }

        });
        return mergedIndex;
    }

    /**
     * 进行加粗并且合并同一个人的项
     * @param jsonObjects
     * @return
     */
    private LinkedList<JSONObject> strongAndMergeSamePerson(){
        LinkedList<JSONObject> objects = new LinkedList<JSONObject>();
        Map<String,JSONObject> map = new HashMap<>(); //用于临时保存已经拿到的某一个Person的信息。

           // 得到合并过后的索引链表
        list.forEach(t -> {
            String personID = t.getPersonID();
            String attrID = t.getAttrID();
            int startPos = t.getStartPos();
            int endPos = t.getEndPos();
            String attrValue = (String)schema.get(personID).get(attrID);
            if(map.get(personID)==null){
                JSONObject object1 = schema.get(personID);
                JSONObject object2 = new JSONObject();
                // 赋值object1中的每一个属性到object2中
                object1.forEach((attr,value)->{
                    object2.put((String)attr, (String)value);
                });
                String attrValue1 = (String)object2.get(attrID);
                String replacedValue = attrValue.substring(startPos, endPos);
                attrValue1 = attrValue1.replace(replacedValue, "<strong style=\"color:#67C23A;\">"+replacedValue+"</strong>");
                object2.put(attrID, attrValue1);
                map.put(personID, object2);
            }else{
                // 如果对应的person已经存在，那么取出该person
                JSONObject object1 = map.get(personID);
                String attrValue1 = (String)object1.get(attrID);
                String replacedValue = attrValue.substring(startPos, endPos);
                attrValue1 = attrValue1.replace(replacedValue, "<strong style=\"color:#67C23A;\">"+replacedValue+"</strong>");
                object1.put(attrID, attrValue1);
            }
        });

        map.forEach((k,v)->{
            objects.add(v);
        });
        return objects;
    }

    /**
     * 合并文档索引,按照或查询
     * @param map 查询词项对应的文档map
     * @return
     */    
    private LinkedList<IndexStructure> mergePersonIndexInOR(Map<String,LinkedList<IndexStructure>> map){

        // 实例化
        list = new LinkedList<IndexStructure>();

        
        
        // 第二次循环，合并LinkedList
        map.forEach(new BiConsumer<String,LinkedList<IndexStructure>>(){

            @Override
            public void accept(String t, LinkedList<IndexStructure> u) {
                Iterator<IndexStructure> i2 = u.iterator();

                while(i2.hasNext()){
                    IndexStructure structure1=i2.next();
                    list.add(structure1);
                }
            }

        });
        return mergedIndex;
    }
}