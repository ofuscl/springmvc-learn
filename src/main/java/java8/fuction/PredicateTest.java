package java8.fuction;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Predicate接口表示采用单个输入并返回布尔值的操作
 * Created by YScredit on 2018/10/17.
 */
public class PredicateTest {

    public static void main(String[] args) {

        test1();
        System.out.println("----------------------------------");
        test2();
    }

    private static void test1(){
        //Predicate String
        Predicate<String> predicateString = s -> {
            return s.equals("Hello");
        };

        System.out.println(predicateString.test("Hello"));
        System.out.println(predicateString.test("Hello World"));

        //Predicate integer
        Predicate<Integer> predicateInt = i -> {
            return i > 0;
        };

        System.out.println(predicateInt.test(5));
        System.out.println(predicateInt.test(-5));
    }

    private static void test2(){
        Predicate<String> predicate=s->{
            return s.equals("Hello");
        };

        //AND logical operation
        Predicate<String> predicateAnd=predicate.and(s->s.length()>4);
        System.out.println(predicateAnd.test("Hello"));

        //OR logical operation
        Predicate<String> predicateOr=predicate.or(s->s.length()==10);
        System.out.println(predicateOr.test("Hello"));

        //非 即相反的意思
        Predicate<String> predicateNegate=predicate.negate();
        System.out.println(predicateNegate.test("Hello"));
    }
}
