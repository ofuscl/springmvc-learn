package demo.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Created by YScredit on 2018/4/23.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface DemoService {
    String value() default "";
}
