package cn.brightasdream.authorization.intercepter;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.brightasdream.authorization.annotation.Authorization;
import cn.brightasdream.authorization.model.TokenModel;
import cn.brightasdream.authorization.redistokenmanager.TokenManager;

@Component
public class AuthorizationIntercepter extends HandlerInterceptorAdapter{
    @Autowired
    private TokenManager manager;//将TokenManager类声明为Component，会自动扫描并注入该类

    @Override
    public boolean preHandle(HttpServletRequest request,HttpServletResponse response,Object handler) throws Exception{
        //如果不是映射到方法直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod)handler;
        Method method = handlerMethod.getMethod();      //通过反射包中的方法类可以得到方法中的有关信息，如注释等
        String authorization = request.getHeader("authorization");

        //获取并验证token
        TokenModel model = manager.getToken(authorization);
        //如果token验证成功
        if(manager.checkToken(model)){
            return true;
        }
        //如果token验证不成功，同时访问的url方法注释为@Authorization，返回401（return false表示不会传给下一个处理了）
        if(method.getAnnotation(Authorization.class) != null){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        //没有添加权限认证的url可以直接访问
        return true;
    }
}