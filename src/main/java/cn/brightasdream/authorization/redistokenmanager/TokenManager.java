package cn.brightasdream.authorization.redistokenmanager;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import cn.brightasdream.authorization.model.TokenModel;

/**管理Token的类，用于创建，获取，验证，删除token
 * @see cn.brightasdream.authorization.redistokenmanager.TokenManager
 * @author bright
 * @date 2018-4-27
 * */
@Component
public class TokenManager{

    @Autowired
    private StringRedisTemplate redis;

    /**
     * 创建Token
     * @param userName 表示传入的用户名
     * @return 返回创建的TokenModel
     */
    
    public TokenModel createToken(String userName){
        String token = UUID.randomUUID().toString().replaceAll("-", "");//随机生成Token
        TokenModel tokenModel = new TokenModel(userName, token);
        //将token储存到redis并且设置30分钟后过期
        redis.boundValueOps(token).set(userName,30,TimeUnit.MINUTES);//将userName和token绑定到token中
        return tokenModel;
    }

    public TokenModel getToken(String authorization){
        if(authorization == null || authorization.length() == 0){
            return null;
        }
        String userName = redis.boundValueOps(authorization).get();
        if(userName == null) return null;
        return new TokenModel(userName,authorization);
    }

    public boolean checkToken(TokenModel tokenModel){// 先调用getToken之后在 调用checkToken
        if(tokenModel == null) return false;//如果为空，说明token不存在
        redis.expire(tokenModel.getToken(), 30, TimeUnit.MINUTES);//将token重新设置未30分钟
        return true;
    }

    public void deleteToken(String token){
        redis.delete(token);
    }
}