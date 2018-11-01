package cn.brightasdream.common;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * 读取json文件，并返回读取到的json对象
 */
public final class JsonFileResolver {
    // 使用单例模式
    private static JsonFileResolver jsonFileResolver = new JsonFileResolver();
    private JsonFileResolver(){
        
    }

    /**
     * 读出指定位置的json文件并将其转为JSONArray对象
     * @param filePath 文件的存放路劲
     * @return JSONArray json数组
     */
    public JSONArray getJsonObjFromFile(String filePath) {
        try {
            // 字符流文件读取
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(new 
            FileInputStream(filePath),"UTF-8"));
            // JSON对象转换器，可以将文件或者字符串转为JSON对象
            JSONParser jsonParser = new JSONParser();
            try {
                // 将json文件转换成json数组
                JSONArray jsonArray = (JSONArray) jsonParser.parse(fileReader);
                
                return jsonArray;
            } catch (IOException | ParseException e) {
				e.printStackTrace();
			}
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
		}
        return null;
    }
    /**
     * 获取单体实例对象
     * @return
     */
    public static JsonFileResolver getInstance(){
        return jsonFileResolver;
    }
}