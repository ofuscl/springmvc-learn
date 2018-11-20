package java8.fuction;

import java.util.ArrayList;
import java.util.List;
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
        System.out.println("----------------------------------");
        test3();
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
        System.out.println("1 : " + predicateAnd.test("Hello"));

        //OR logical operation
        Predicate<String> predicateOr=predicate.or(s->s.length()==10);
        System.out.println("2 : " + predicateOr.test("Hello"));

        //非 即相反的意思
        Predicate<String> predicateNegate=predicate.negate();
        System.out.println("3 : " + predicateNegate.test("Hello"));
    }


    private static void test3(){
        List<String> list1 = new ArrayList<>();
        list1.add("a");
        list1.add("b");
        list1.add("c");

        List<String> list2 = new ArrayList<>();
        list2.add("a");
        list2.add("b");

        Predicate<String> p = (eid) -> list2.contains(eid);
        list1.removeIf(p);
        list1.forEach(System.out::println);
    }
}
