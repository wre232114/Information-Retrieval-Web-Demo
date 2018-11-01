package cn.brightasdream.common;

import java.util.Map;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;

/**
 * 全局的词项表管理器，用于管理全局的词项表信息
 */

@Component
public class TokenSchemaManager implements Serializable {
    private static final long serialVersionUID = 1633607388512833316L;
    private Map<String, LinkedList<IndexStructure>> tokenSchema = new HashMap<String, LinkedList<IndexStructure>>();

    /**
     * 添加token，如果token在词项表中已经存在，那么将token对应的结构信息添加到索引结构中
     * 如果token不存在，那么添加新的token信息，然后将token对应的结构信息添加到索引结构中
     * @param token 需要添加的词项
     * @param structure 词项对应的索引结构
     */
    public void addToken(String token, IndexStructure structure){
        if(checkToken(token)){
            // 获取token对应的索引链表,并添加到索引链表中
            LinkedList<IndexStructure> temp = tokenSchema.get(token);
            temp.add(structure);
        }else{
            // 创建新的词项及索引并添加索引信息
            tokenSchema.put(token, new LinkedList<IndexStructure>());
            tokenSchema.get(token).add(structure);
        }
        
    }

    /**
     * 检查词项表中是否有词项
     * @param token
     * @return
     */
    public boolean checkToken(String token){
        // 如果在词项表中拥有该此项，返回true
        if(tokenSchema.get(token) != null){
            return true;
        }
        // 否则返回false
        return false;
    }

    /**
     * 获取词项表
     * @return
     */
    public Map<String,LinkedList<IndexStructure>> getTokenSchema(){
        return tokenSchema;
    }
    

    /**
     * 根据token返回对应的索引表
     * @param token
     * @return
     */
    public LinkedList<IndexStructure> getTokenIndex(String token) {
        return tokenSchema.get(token);
    }
}