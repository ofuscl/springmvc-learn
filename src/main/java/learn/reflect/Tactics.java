package learn.reflect;

import java.lang.annotation.*;

/**
 * Created by yunfan on 2018/5/14.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Tactics {

    String type() default "";
}
