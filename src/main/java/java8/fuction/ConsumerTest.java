package java8.fuction;

import java.util.function.Consumer;

/**
 * Consumer接口表示采用单个参数T并且不返回任何结果的操作
 * Created by YScredit on 2018/10/17.
 */
public class ConsumerTest {

    public static void main(String[] args) {

        test1();
        System.out.println("----------------------------------");
        test2();
    }

    private static void test1(){
        Consumer<String> consumerStr=s->{
            System.out.println(s);
        };
        consumerStr.accept("Hello Java.");
        consumerStr.accept("Hello World.");

        Consumer<Integer> consumerInt=i->{
            System.out.println("Integer value="+i);
        };
        consumerInt.accept(5);
        consumerInt.accept(8);
    }

    private static void test2(){
        Consumer<String> consumer1= s->{
            System.out.println(s+" World.");
        };
        Consumer<String> consumer2=s->{
            System.out.println(s+" Java.");
        };

        //Using andThen method
        consumer1.andThen(consumer2).accept("Hello");
    }
}
