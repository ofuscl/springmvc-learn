package java8;

import learn.schema.User;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by YScredit on 2018/7/16.
 */
public class RemoveIf {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.removeIf(s -> s%2==0); // 去除满足条件的数据
        list.forEach(s -> System.out.println(s));

        List<String> strings = new ArrayList<>();
        strings.add("ab");
        strings.add("ac");
        strings.add("bc");
        strings.add("cd");
        Predicate<String> predicate = (s) -> s.startsWith("a"); // 这里单独定义了过滤器
        strings.removeIf(predicate);                            // 过滤掉以"a"开头的元素
        strings.forEach(s -> System.out.println(s));            // 输出 bc cd

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
