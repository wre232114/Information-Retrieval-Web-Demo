package cn.brightasdream.common;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;

/**
 * 词条处理类，不能实例化和继承，只能使用静态方法获取词条化的token信息
 */
public final class TokenParser {

    private TokenParser(){

    }

    /**
     * 获取输入文本词条化后的详细信息
     * @param str 输入的文本
     * @param attrID 文本所在的属性ID
     * @param personID 属性所在的人的ID
     * @return Map<String,IndexStructure> 词条和词条信息对应的匹配
     */
    public static Map<String,IndexStructure> getTokenStringMap(String str,String attrID,String personID){
        
        Map<String,IndexStructure> map = new HashMap<String,IndexStructure>();
        try {
            // 创建一个分析器对象
            Analyzer analyzer = new SmartChineseAnalyzer();
            // 从分析器获取String对象
            // 参数1，域的名称，可以是null，或者""
            // 参数2，要分析的文本
            TokenStream tokenStream = analyzer.tokenStream("", str);
            // 对关键词，偏移量等的引用
            CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
            // 关键词在文本中出现的位置，后续用于高亮显示
            OffsetAttribute offsetAttribute = tokenStream.addAttribute(OffsetAttribute.class);
            // 不调用该方法会抛出异常
            tokenStream.reset();
            while (tokenStream.incrementToken()) {
                // 创建索引保存结构
                IndexStructure structure = new IndexStructure();
                structure.setPersonID(personID);
                structure.setAttrID(attrID);
                // 获取文本出现的开始位置
                structure.setStartPos(offsetAttribute.startOffset());
                // 获取文本出现的结束位置
                structure.setEndPos(offsetAttribute.endOffset());

                // 添加到map中
                map.put(charTermAttribute.toString(), structure);
            }
            tokenStream.close();
            analyzer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 将输入字符串词条化，返回词条列表
     */
    public static LinkedList<String> getTokenStringList(String tokenStr) {
        LinkedList<String> list = new LinkedList<>();
        try {
            Analyzer analyzer = new SmartChineseAnalyzer();
            TokenStream tokenStream = analyzer.tokenStream("", tokenStr);

            CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);

            tokenStream.reset();
            while(tokenStream.incrementToken()){
                list.add(charTermAttribute.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}