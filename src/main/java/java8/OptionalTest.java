package java8;

import java.util.*;

/**
 *
 */
public class OptionalTest {

    public static void main(String[] args) {

        Map<String,List<User>> allMap = new HashMap<>();
        allMap.put("a",new ArrayList<>());
        allMap.get("a").add(new User("xiaoming","男",3));
        allMap.get("a").add(new User("xiaogang","男",3));
        allMap.get("a").add(new User("xiaohua","女",3));

        // 设置空值场合的默认值
        System.out.println(Optional.ofNullable(allMap.get("b")).orElse(new ArrayList<>()));
    }
}
