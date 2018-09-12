package java8;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 流处理方式
 * 包含：
 * 1、条件过滤
 * 2、统计
 * 3、筛选出对象集合中的字段
 * 4、按照对象中某个字段分组
 */
public class StreamTest {

    public static void main(String[] args) {

        Map<String,List<User>> allMap = new HashMap<>();
        allMap.put("a",new ArrayList<>());
        allMap.get("a").add(new User("xiaoming","男",3));
        allMap.get("a").add(new User("xiaogang","男",3));
        allMap.get("a").add(new User("xiaohua","女",3));

        // 设置空值场合的默认值
        System.out.println(Optional.ofNullable(allMap.get("b")).orElse(new ArrayList<>()));

        List<User> userList = allMap.get("a");
        // 筛选出 性别为男的，用户名称 ，放入List集合中
        List<String> nameList = userList.stream().filter(u -> u.getSex().equals("男")).map(User::getName).collect(Collectors.toList());
        System.out.println(nameList);
        // 筛选出 按照性别分组，存入List集合中
        Map<String,List<User>> sexMap= userList.stream().collect(Collectors.groupingBy(User::getSex,Collectors.toList()));
        System.out.println(sexMap.toString());
    }
}
