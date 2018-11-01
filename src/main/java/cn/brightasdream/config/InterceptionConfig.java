package cn.brightasdream.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import cn.brightasdream.authorization.intercepter.AuthorizationIntercepter;

/**
 * 配置类，用于配置拦截器
 */
@Configuration
public class InterceptionConfig extends WebMvcConfigurerAdapter{

    @Bean
    public AuthorizationIntercepter getAuthorizationIntercepter(){
        return new AuthorizationIntercepter();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptor = registry.addInterceptor(getAuthorizationIntercepter());

        // 排除配置
        addInterceptor.excludePathPatterns("/error");
        addInterceptor.excludePathPatterns("/login**");

        // 拦截配置
        addInterceptor.addPathPatterns("/**");
    }
}