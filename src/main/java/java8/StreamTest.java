package java8;

import org.junit.Test;
import util.JsonUtil;

import java.util.*;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 流处理方式 : stream的延迟执行特性，在聚合操作执行前修改数据源是允许的
 * 包含：
 * 1、条件过滤
 * 2、统计
 * 3、筛选出对象集合中的字段
 * 4、按照对象中某个字段分组
 * 5、输出有序的 forEachOrdered 比 forEach 效率高
 * 6、原始对象流比例 IntStream 比 基本数据类型（int,double...）包装成相应对象流 高效
 * 7、并行串行
 */
public class StreamTest {

    public static void main(String[] args) {

        Map<String,List<User>> allMap = new HashMap<>();
        allMap.put("a",new ArrayList<>());
        allMap.get("a").add(new User("xiaoming","男",7));
        allMap.get("a").add(new User("xiaogang","男",5));
        allMap.get("a").add(new User("xiaogang","男",2));
        allMap.get("a").add(new User("xiaohua","女",2));

        List<User> userList = allMap.get("a");

        List<String> list = new ArrayList<>();
        System.out.println(list.stream().collect(Collectors.joining("','", "'", "'")).equals("''"));
//        transformToObjects1(userList);
//        transformToObjects2(userList);
        //        testFilter(userList);
//        testFlapMap(userList);
//        testStream(userList);
//        testGroupBy(userList);
        testSorted(userList);
//        testMaxMin(userList);
//        testSum(userList);
//        testList(userList);
//        testMap(userList);
//        testJoin(userList);
    }

    private static void transformToObjects1(List<User> users) {

        Function<User, Object[]> f = new Function<User, Object[]>() {
            @Override
            public Object[] apply(User q) {
                return new Object[]{q.getName(), q.getSex()};
            }
        };

        List<Object[]> list = users.stream().map(f).collect(Collectors.toList());
        System.out.println("transfor1 :" + JsonUtil.toJsonFromObject(list));
    }

    private static void transformToObjects2(List<User> users) {

        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> m = new HashMap<>();
        m.put("a","1");
        m.put("b","2");
        m.put("c","3");
        list.add(m);

        Map<String,Object> m2 = new HashMap<>();
        m2.put("a","11");
        m2.put("b","21");
        m2.put("c","31");
        list.add(m2);
        Function<Map, Map<String,String>> f = new Function<Map, Map<String,String>>() {
            @Override
            public Map<String,String> apply(Map m) {
                Map<String,String> mx = new HashMap<>();
                mx.put(m.get("a").toString(),m.get("c").toString());
                return mx;
            }
        };

        List<Map<String,String>> list2 = list.stream().map(f).collect(Collectors.toList());
        System.out.println("transfor2 :" + JsonUtil.toJsonFromObject(list2));
    }

    /**
     * 生成Stream。
     */
    public static void testFilter(List<User> userList){
        // 判断是否存在，存在则取值
        Optional<User> op = userList.stream().filter(u -> "xiaoming".equals(u.getName())).findFirst();
        if (op.isPresent()){
            System.out.println(op.get().getName());
        }

        System.out.println(userList.stream().filter(u -> "xiaoming".equals(u.getName())).findFirst().orElse(new User()).getName());
    }

    /**
     * 生成Stream。
     */
    public static void testStream(List<User> userList){

        //创建普通流
        Stream<User> s1  = userList.stream();
        //创建并行流
        Stream<User> s2 = userList.parallelStream();
        //创建无限流，通过limit提取指定大小
        Stream.generate(()->"number"+new Random().nextInt()).limit(10).forEach(System.out::println);
        //产生规律的数据
        Stream.iterate(1, x->x+1).limit(10).forEach(System.out::println);
    }

    /**
     * flapMap：拆解流，将流中每一个元素拆解成一个流。
     */
    public static void testFlapMap(List<User> userList){
        String[] arr1 = {"a", "b", "c", "d"};
        String[] arr2 = {"e", "f", "c", "d"};
        String[] arr3 = {"h", "j", "c", "d"};
        // Stream.of(arr1, arr2, arr3).flatMap(x -> Arrays.stream(x)).forEach(System.out::println);
        Stream.of(arr1, arr2, arr3).flatMap(Arrays::stream).forEach(System.out::println);
    }

    /**
     * 生成Map。
     */
    public static void testMap(List<User> userList){

        // Collectors.toMap 引号前定义Key,引号后定义value
        Map<String,Integer> map = userList.stream().collect(Collectors.toMap(u -> u.getName(), u -> u.getAge()));
        System.out.println("testMap 111 :" + map.toString());
    }

    /**
     * 生成集合（List、Set）。
     */
    public static void testList(List<User> userList){
        // 筛选出 性别为男的，用户名称 ，放入List集合中
        // 方法引用 User::getName
        List<String> nameList1 = userList.stream().filter(u -> u.getSex().equals("男")).map(User::getName).collect(Collectors.toList());
        System.out.println("testList 111 :" + nameList1.toString());
        // Lambda表达式 u -> u.getName()
        List<String> nameList2 = userList.stream().filter(u -> u.getSex().equals("男")).map(u -> u.getName()).collect(Collectors.toList());
        System.out.println("testList 222 :" + nameList2.toString());
        // Lambda表达式 u -> u.getName()
        List<String> nameList3 = userList.stream().filter(u -> u.getSex().equals("男")).map(u -> u.getName()).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        System.out.println("testList 333 :" + nameList3.toString());
        // 使用toCollection()指定规约容器的类型
        List<String> nameList4 = userList.stream().filter(u -> u.getSex().equals("男")).map(u -> u.getName()).collect(Collectors.toCollection(ArrayList::new));
        System.out.println("testList 444 :" + nameList4.toString());
    }

    /**
     * 求最大/小值。
     */
    public static void testMaxMin(List<User> userList){
        // 筛选出 性别为男的，用户名称 ，放入List集合中
        User max = userList.stream().reduce((u1,u2) -> u1.getAge() > u2.getAge() ? u1:u2).get();
        System.out.println("testMax 111 :" + max.getAge());

        Integer maxInt = userList.stream().mapToInt(u -> u.getAge()).max().getAsInt();
        System.out.println("testMax 222 :" + maxInt);

        User user = userList.stream().max(Comparator.comparing(u -> u.getAge())).get();
        System.out.println("testMax 333 :" + user.getName());
    }

    /**
     * 求连接。
     */
    public static void testJoin(List<User> userList){
        // 筛选出 性别为男的，用户名称 ，放入List集合中
        String str1 = userList.stream().map(u -> u.getName()).collect(Collectors.joining("\",\"", "\"", "\""));
        System.out.println("testJoin 111 :" + str1);
        String str2 = userList.stream().map(u -> u.getName()).collect(Collectors.joining());
        System.out.println("testJoin 222 :" + str2);
        String str3 = userList.stream().map(u -> u.getName()).collect(Collectors.joining(","));
        System.out.println("testJoin 333 :" + str3);
        String str4 = userList.stream().map(u -> u.getName()).collect(Collectors.joining("','", "'", "'"));
        System.out.println("testJoin 444 :" + str4);

//        userList.stream().max()
    }

    /**
     * 求合计。
     */
    public static void testSum(List<User> userList){
        // 筛选出 性别为男的，用户名称 ，放入List集合中
        Integer sum = userList.stream().mapToInt(u -> u.getAge()).sum();
        System.out.println("testSum 111 :" + sum);

//        userList.stream().max()
    }

    /**
     * 分组。如果只有两类，使用partitioningBy会比groupingBy更有效率
     */
    public static void testGroupBy(List<User> userList){
        // 筛选出 按照性别分组，存入List集合中
        Map<String,List<User>> sexMap1= userList.stream().collect(Collectors.groupingBy(User::getSex));
        System.out.println("testGroupBy 111 :" + sexMap1.toString());
        // 筛选出 按照性别分组，统计数量
        Map<String,Long> sexMap2= userList.stream().collect(Collectors.groupingBy(User::getSex,Collectors.counting()));
        System.out.println("testGroupBy 222 :" + sexMap2.toString());
        // 筛选出 按照性别分组，存入List集合中
        Map<String,List<Integer>> sexMap3= userList.stream().collect(Collectors.groupingBy(User::getSex,Collectors.mapping(User::getAge,Collectors.toList())));
        System.out.println("testGroupBy 333 :" + sexMap3.toString());
    }
    /**
     * 排序。
     */
    public static void testSorted(List<User> userList){
        System.out.println("testSorted  start----------------------");

        // stream - 自然顺序 -条件
        userList.stream().sorted(Comparator.comparing(User::getAge)).forEach(u ->{
            System.out.println("stream条件排序-ASC: " + u.getName());
        });
        // stream - 倒序排列 -条件
        userList.stream().sorted(Comparator.comparing(User::getAge).reversed()).forEach(u ->{
            System.out.println("stream条件排序-DESC: " + u.getName());
        });
        // stream - 正序排列 -条件
        userList.sort(Comparator.comparing(User::getAge));
        userList.forEach(u -> {
            System.out.println("直接条件排序-ASC: " + u.getName());
        });
        // stream - 倒序排列 -条件
        userList.sort(Comparator.comparing(User::getAge).reversed());
        userList.forEach(u -> {
            System.out.println("直接条件排序-DESC: " + u.getName());
        });

        // stream -联合排序 ，多条件
        userList.sort(Comparator.comparing(User::getName).thenComparing(User::getAge));
        userList.forEach(u -> {
            System.out.println("联合排序-: " + u.getName() +" | "+ u.getAge());
        });
    }

    public void peek1(int x) {
        System.out.println(Thread.currentThread().getName() + ":->peek1->" + x);
    }

    public void peek2(int x) {
        System.out.println(Thread.currentThread().getName() + ":->peek2->" + x);
    }

    public void peek3(int x) {
        System.out.println(Thread.currentThread().getName() + ":->final result->" + x);
    }

    /**
     * peek，监控方法
     * 串行流和并行流的执行顺序
     */
    @org.junit.Test
    public void testPeek() {
        Stream<Integer> stream = Stream.iterate(1, x -> x + 1).limit(10);
        stream.peek(this::peek1).filter(x -> x > 5)
                .peek(this::peek2).filter(x -> x < 8)
                .peek(this::peek3)
                .forEach(System.out::println);
    }

    /**
     * 并行
     */
    @Test
    public void testPeekPal() {
        Stream<Integer> stream = Stream.iterate(1, x -> x + 1).limit(10).parallel();
        stream.peek(this::peek1).filter(x -> x > 5)
                .peek(this::peek2).filter(x -> x < 8)
                .peek(this::peek3)
                .forEach(System.out::println);
    }
}
