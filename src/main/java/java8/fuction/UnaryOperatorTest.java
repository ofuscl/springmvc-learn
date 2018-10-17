package java8.fuction;

import java.util.function.UnaryOperator;

/**
 * 接口表示一个操作，它接受一个参数并返回与其输入参数相同类型的结果
 * Created by YScredit on 2018/10/17.
 */
public class UnaryOperatorTest {

    public static void main(String[] args) {

        test1();
        System.out.println("----------------------------------");
        test2();
    }

    private static void test1(){
        UnaryOperator<Integer> operator = t -> t * 2;
        System.out.println(operator.apply(5));
        System.out.println(operator.apply(10));
        System.out.println(operator.apply(15));
    }

    private static void test2(){
        UnaryOperator<Integer> operator1 = t -> t + 10;
        UnaryOperator<Integer> operator2 = t -> t * 10;

        // 先执行operator1后执行operator2：t = (t+10)*10
        int a = operator1.andThen(operator2).apply(5);
        System.out.println(a);

        // 先执行operator2后执行operator1：t = (t*10)+10
        int b = operator1.compose(operator2).apply(2);
        System.out.println(b);
    }
}
