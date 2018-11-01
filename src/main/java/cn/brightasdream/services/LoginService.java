package cn.brightasdream.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.brightasdream.authorization.model.TokenModel;
import cn.brightasdream.authorization.redistokenmanager.TokenManager;
import cn.brightasdream.authorization.types.Constants;
import cn.brightasdream.dao.UserDao;
import cn.brightasdream.model.User;

@Service
public class LoginService{
    @Autowired
    UserDao userDao;

    @Autowired
    TokenManager manager;
    /**
     * 处理登陆操作，检查数据库中是否有匹配的数据，如果有则将处理结果返回给controller，
     */
    public int handleLogin(String userName,String password,TokenModel result){
        User user = new User(userName, password, "");
        TokenModel rs = new TokenModel(); //需要创建一个新的变量来获取结果，java对象传参时引用类型，此方法中result指向的地址和传入的地址一致
        if(userDao.isUserMatch(user)){  //如果账号和密码匹配，则为用户创建Token
            rs = manager.createToken(userName);
            result.setToken(rs.getToken());
            result.setUserName(rs.getUserName());
            return Constants.LOGIN_SUCCESS;
        }
        return Constants.LOGIN_FAIL;
    }

    /**
     * 处理注册用户请求
     */
    public int handleRegister(User user){
        if(userDao.registerUser(user)){
            return Constants.REGISTER_SUCCESS;
        }
        return Constants.REGISTER_FAIL;
    }
}