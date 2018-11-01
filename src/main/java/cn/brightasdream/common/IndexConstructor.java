package cn.brightasdream.common;

import java.util.LinkedList;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("prototype")
@Component
public class IndexConstructor {

    @Autowired
    TokenSchemaManager manager;

    @Autowired
    IdAndPersonSchema schema;

    public void constructFromJSONArray(JSONArray array) {
        array.forEach(new Consumer<JSONObject>() {

            @Override
            public void accept(JSONObject t) {
                constructFromJSONObject(t);
            }
        });

        // 完成所有的构建以后对每一个词项对应的索引列表排序
        Map<String,LinkedList<IndexStructure>> map = manager.getTokenSchema();
        map.forEach(new BiConsumer<String,LinkedList<IndexStructure>>(){

            @Override
            public void accept(String t, LinkedList<IndexStructure> u) {
                u.sort(new IndexComparator());
            }

        });
    }

    public void constructFromJSONObject(JSONObject object) {
        // 随机生成personID
        String personID = UUID.randomUUID().toString().replaceAll("-", "");
        // 存储到文档对应表中
        schema.add(personID, object);

        object.forEach(new BiConsumer<String,String>(){

            @Override
            public void accept(String t, String u) {
                constructFromText(personID, t, u);
            }
        });
    }

    public void constructFromText(String personID,String attrID,String text){
        // 词条处理器处理并返回词条相信信息
        Map<String,IndexStructure> map = TokenParser.getTokenStringMap(text, attrID, personID);

        map.forEach(new BiConsumer<String,IndexStructure>(){

            @Override
            public void accept(String t, IndexStructure u) {
                // 将分析出的词项添加到词表中
                
                manager.addToken(t, u);
            }
        });
    }

}