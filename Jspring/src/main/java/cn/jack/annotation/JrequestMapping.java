package cn.jack.annotation;

import java.lang.annotation.*;

/**
 * @Auther: ZhangXing
 * @Date: 2019/1/6 * @Description: cn.jack.annotation * @version: 1.0
 */
@Target({ElementType.TYPE, ElementType.METHOD})//修饰的对象范围
@Retention(RetentionPolicy.RUNTIME)// Annotation的生命周期
@Documented//是一个标记注解
public @interface JrequestMapping {
    String value() default "";
}
