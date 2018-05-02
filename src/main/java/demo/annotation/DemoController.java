package demo.annotation;

import java.lang.annotation.*;

/**
 * Created by YScredit on 2018/4/20.
 */
@Target(ElementType.TYPE)

@Retention(RetentionPolicy.RUNTIME)

@Documented //说明该注解将被包含在javadoc中
public @interface DemoController {
    // 说明：1、Java8的新特性：default
    // 说明：2、正常接口是不能有方法体的；default关键字在接口中修饰方法时，方法可以有方法体。
    String value() default "";
}
