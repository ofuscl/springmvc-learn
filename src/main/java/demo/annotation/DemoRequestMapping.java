package demo.annotation;

/**
 * Created by yunfan on 2018/4/20.
 */
public @interface DemoRequestMapping {
    String value() default "";
}
