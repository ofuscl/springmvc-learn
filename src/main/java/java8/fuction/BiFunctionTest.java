package java8.fuction;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created by YScredit on 2018/11/26.
 */
public class BiFunctionTest {

    public static void main(String[] args) {

        BiFunction<String, String,String> biFunction = (x, y) -> {
            return x+"==="+y;
        };

        Function<String,String> fun = x ->x + " after8";
        System.out.println(biFunction.andThen(fun).apply("tpyyes.com ", " java8"));
    }
}
