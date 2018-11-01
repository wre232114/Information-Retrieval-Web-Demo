package cn.brightasdream.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.brightasdream.authorization.annotation.Authorization;
import cn.brightasdream.authorization.model.TokenModel;
import cn.brightasdream.authorization.redistokenmanager.TokenManager;
import cn.brightasdream.authorization.types.Constants;
import cn.brightasdream.model.ResBody;
import cn.brightasdream.model.User;
import cn.brightasdream.services.LoginService;

@RestController
@CrossOrigin // 允许跨域请求
public class LoginController{
    @Autowired
    LoginService loginService;//导入的处理登陆相关的服务

    @Autowired
    TokenManager manager;//导入token管理类
    /**
     * 处理登陆操作，发送post请求
     * @author bright
     * @param reqBody post传入的请求结构，用于解析JSON请求数据
     */
    @RequestMapping("/login")
    public ResponseEntity<ResBody> login(@RequestBody Map<String,String> reqBody){
        String userName= reqBody.get("userName");
        String password = reqBody.get("psword");
        TokenModel result = new TokenModel();
        int status = loginService.handleLogin(userName, password,result);//将login操纵转到service层处理
        if(status == Constants.LOGIN_SUCCESS){
            return new ResponseEntity<>(new ResBody(Constants.LOGIN_SUCCESS,result),HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResBody(Constants.LOGIN_FAIL,null),HttpStatus.OK);
    }
    /**
     * 处理注册操作，发送post请求
     */
    @RequestMapping("/register")
    public ResponseEntity<ResBody> register(@RequestBody Map<String,String> resquestBody){  //使用requestBody才能获取到POST发送的JSON数据
        String userName = resquestBody.get("userName");
        String password = resquestBody.get("psword");
        String email = resquestBody.get("email");
        User user = new User(userName, password, email);
        int status = loginService.handleRegister(user);
        return new ResponseEntity<>(new ResBody(status, new String("注册成功了！")),HttpStatus.OK);
    }
    /**
     * 注销操作，需要进行token认证
     * @author bright
     * @param reqHeader 获取请求头信息
     */
    @Authorization
    @RequestMapping("/logout")
    public ResponseEntity<ResBody> logout(@RequestHeader Map<String,String> reqHeader){
        String token = reqHeader.get("authorization");
        manager.deleteToken(token);//删除token，登陆失效
        return new ResponseEntity<>(new ResBody(Constants.LOGIN_SUCCESS, new String("注销成功!")),HttpStatus.OK);
    }
    /**
     * token认证，经过了认证的的请求才能访问前端路由
     */
    @Authorization
    @RequestMapping("/checkToken")
    public ResponseEntity<ResBody> checkToken(@RequestHeader Map<String,String> reqHeader){
        String authorization = reqHeader.get("authorization");
        return new ResponseEntity<>(new ResBody(Constants.CHECK_TOKEN_SUCCESS, manager.getToken(authorization)),HttpStatus.OK);
    }
}