package java8.fuction;

import java.util.function.BiConsumer;

/**
 * BiConsumer接口表示一个带有两个参数(T，U)且不返回结果的操作
 * Created by YScredit on 2018/10/17.
 */
public class BiConsumerTest {

    public static void main(String[] args) {
        test1();
        System.out.println("----------------------------------");
        test2();
    }

    private static void test1(){
        BiConsumer<Integer,String> consumer = (a,b) -> {
            System.out.println(a + b);
        };
        consumer.accept(5," Chapters");
    }

    private static void test2(){
        BiConsumer<Integer,Integer> addition = (a,b) -> {
            System.out.println(a + b);
        };

        BiConsumer<Integer,Integer> subtraction = (a,b) -> {
            System.out.println(a - b);
        };
        // Using andThen()
        addition.andThen(subtraction).accept(10,6);
    }
}
