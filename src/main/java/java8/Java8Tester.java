package java8;

import org.junit.Test;

import java.util.*;

/**
 * Created by YScredit on 2018/5/17.
 */
public class Java8Tester {

//    public static void main(String[] args) {
//
//        Java8Tester tester = new Java8Tester();
////        tester.testOne();
//
//        List<String> lambdas = Arrays.asList("学习","仔细");
//        lambdas.forEach(Lambda::test);
//        lambdas.forEach(System.out::println);
//        System.out.println(Lambda.test("xuexi"));
//
//    }

    @Test
    public void testOne(){

        List<String> names1 = new ArrayList<>();
        names1.add("Google ");
        names1.add("Runoob ");
        names1.add("Taobao ");
        names1.add("Baidu ");
        names1.add("Sina ");

        List<String> names2 = new ArrayList<>();
        names2.add("Google ");
        names2.add("Runoob ");
        names2.add("Taobao ");
        names2.add("Baidu ");
        names2.add("Sina ");

        Java8Tester tester = new Java8Tester();
        System.out.println("使用 Java 7 语法: ");

        tester.sortUsingJava7(names1);
        System.out.println(names1);
        System.out.println("使用 Java 8 语法: ");

        tester.sortUsingJava8(names2);
        System.out.println(names2);

        System.out.println("--------------------------------------------------");
        names2.forEach(System.out::println);
    }

    // 使用 java 7 排序
    public void sortUsingJava7(List<String> names){
        Collections.sort(names, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareTo(s2);
            }
        });
    }

    // 使用 java 8 排序
    public void sortUsingJava8(List<String> names){
        Collections.sort(names, (s1, s2) -> s1.compareTo(s2));
    }
}
