package demo.annotation;

import java.lang.annotation.*;

/**
 * Created by YScredit on 2018/4/20.
 */
@Target({ElementType.CONSTRUCTOR,ElementType.FIELD,ElementType.METHOD,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DemoAutowired {
    String value() default "";
}
