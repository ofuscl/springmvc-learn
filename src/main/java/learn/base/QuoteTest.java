package learn.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import util.JsonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 应用。
 * 最重要最容易被忽略的就是对象引用。
 */
public class QuoteTest {

    public static void main(String[] args) {

        System.out.println("test1 : 由于触发了对象引用，数据被覆盖所以出现了重复的情况");
        test1();
        System.out.println("----------------------------------------------------");
        System.out.println("test2 : 未触发对象引用，数据正确");
        test2();
    }

    private static void test1(){
        List<Fruit> fruits = new ArrayList<>();

        List<Apple> apples = new ArrayList<>();
        apples.add(new Apple("红富士","红色","大"));
        apples.add(new Apple("红士","青色","小"));
        apples.add(new Apple("红富士","大红色","大"));
        apples.add(new Apple("青皮","青色","大"));
        for (int i = 0; i < 2; i++){
            Fruit fruit = new Fruit(); // 位置差异
            fruit.setName("苹果"+i); // 位置差异
            Map<String,StringBuffer> map = getMap(apples);
            for (String key : map.keySet()){
                fruit.setType(key);
                fruit.setColor(map.get(key).toString());
                fruits.add(fruit);
            }
        }

        fruits.forEach( f -> {
            System.out.println(JsonUtil.toJsonFromObject(f));
        });
    }

    private static void test2(){
        List<Fruit> fruits = new ArrayList<>();

        List<Apple> apples = new ArrayList<>();
        apples.add(new Apple("红富士","红色","大"));
        apples.add(new Apple("红士","青色","小"));
        apples.add(new Apple("红富士","大红色","大"));
        apples.add(new Apple("青皮","青色","大"));
        for (int i = 0; i < 2; i++){

            Map<String,StringBuffer> map = getMap(apples);
            for (String key : map.keySet()){
                Fruit fruit = new Fruit(); // 位置差异
                fruit.setName("苹果"+i); // 位置差异
                fruit.setType(key);
                fruit.setColor(map.get(key).toString());
                fruits.add(fruit);
            }
        }

        fruits.forEach( f -> {
            System.out.println(JsonUtil.toJsonFromObject(f));
        });
    }

    private static Map<String,StringBuffer> getMap(List<Apple> apples){
        Map<String,StringBuffer> map = new HashMap<>();
        for(Apple apple : apples){
            if(map.containsKey(apple.getType())){
                map.get(apple.getType()).append(",").append(apple.getColor());
            } else {
                StringBuffer sb = new StringBuffer();
                sb.append(apple.getColor());
                map.put(apple.getType(),sb);
            }
        }

        return map;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Fruit {

        private String name;
        private String type;
        private String color;

        public Fruit(){}
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Apple {

        private String type;
        private String color;
        private String size;
    }
}
