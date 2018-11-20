package java8;

import learn.schema.User;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * 删除符合条件的数据
 * Created by yunfan on 2018/7/16.
 */
public class RemoveIfTest {

    public static void main(String[] args) {

        System.out.println("test1-----------------------------");
        test1();
        System.out.println("test2-----------------------------");
        test2();
        System.out.println("test3-----------------------------");
        test3();
        System.out.println("test4-----------------------------");
        test4();
    }

    /**
     * 删除列表中满足条件的数据
     */
    private static void test1(){
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.removeIf(s -> s%2==0); // 删除列表中满足条件的数据
        list.forEach(s -> System.out.println(s));
    }

    /**
     * 定义字符串删除过滤器 Predicate
     */
    private static void test2(){
        List<String> strings = new ArrayList<>();
        strings.add("ab");
        strings.add("ac");
        strings.add("bc");
        strings.add("cd");
        Predicate<String> predicate = (s) -> s.startsWith("a"); // 这里单独定义了过滤器
        strings.removeIf(predicate);                            // 过滤掉以"a"开头的元素
        strings.forEach(s -> System.out.println(s));            // 输出 bc cd
    }

    /**
     * 定义字符串删除过滤器 Predicate
     */
    private static void test3(){
        List<String> list1 = new ArrayList<>();
        list1.add("a");
        list1.add("b");
        list1.add("c");

        List<String> list2 = new ArrayList<>();
        list2.add("a");
        list2.add("b");

        Predicate<String> p = (s) -> list2.contains(s);
        list1.removeIf(p);
        list1.forEach(System.out::println);
    }

    /**
     * 定义对象删除过滤器 Predicate
     */
    private static void test4(){
        List<User> users = new ArrayList<>();
        users.add(new User(25,"xiaowang"));
        users.add(new User(35,"libai"));

        Predicate<User> predicate2 = (u) -> u.getAge() > 30; // 这里单独定义了过滤器
        users.removeIf(predicate2);
        users.forEach(user -> {
            System.out.println(user.getName());
        });
    }
}
