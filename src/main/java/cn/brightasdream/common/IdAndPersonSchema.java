package cn.brightasdream.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

/**
 * 与个人的对应表，用于将id与个人完整信息对应
 */

@Component
public class IdAndPersonSchema implements Serializable {
    private static final long serialVersionUID = -6464258411832620358L;
    private Map<String, JSONObject> scheme = new HashMap<String, JSONObject>();

    public void add(String id,JSONObject object){
        if(!check(id)) scheme.put(id, object);
    }
    
    public boolean check(String id) {
        if(scheme.get(id)!=null){
            return true;
        }
        return false;
    }

    public JSONObject get(String id){
        if(check(id)){
            return scheme.get(id);
        }
        return null;
    }

    public Map<String,JSONObject> getIdPeronSchema(){
        return scheme;
    }
}