package cn.brightasdream.authorization.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于拦截url访问,用该注解标注的匹配方法需要进行token验证后才能访问
 * @see cn.brightasdream.authorization.intercepter
 */

 //添加了这两个注释以后就可以用method的getAnnotation方法获取方法的注释信息了
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Authorization{

}