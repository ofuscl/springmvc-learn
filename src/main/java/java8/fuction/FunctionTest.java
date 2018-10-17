package java8.fuction;

import java.util.function.Function;

/**
 * Function是一个功能接口，其功能方法(单个抽象方法)是R apply(T t)。 Function接口表示采用单个参数T并返回结果R的操作
 * Created by YScredit on 2018/10/17.
 */
public class FunctionTest {

    public static void main(String[] args) {

        test1();
        System.out.println("----------------------------------");
        test2();
    }

    private static void test1(){
        Function<Integer,String> function = (t) -> {
            if (t % 2 == 0) {
                return t+ " is even number";
            } else {
                return t+ " is odd number";
            }
        };

        System.out.println(function.apply(5));
        System.out.println(function.apply(8));
    }

    private static void test2(){
        Function<Integer,Integer> function1 = t -> (t - 5);
        Function<Integer,Integer> function2 = t -> (t * 2);

        //Using andThen() method
        int a=function1.andThen(function2).apply(50);
        System.out.println(a);

        //Using compose function
        int c=function1.compose(function2).apply(50);
        System.out.println(c);
    }
}
