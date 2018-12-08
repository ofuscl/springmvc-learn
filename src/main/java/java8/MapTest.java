package java8;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * 如果不存在key则做A，不存在则做B
 * Created by YScredit on 2018/11/26.
 */
public class MapTest {

    public static void main(String[] args) {

        Map<String, String> myMap = new HashMap<>();
        String keyA = "A";
        String keyB = "B";
        String keyC = "C";
        String keyD = "D";
        String keyE = "E";
        String keyF = "F";
        String keyG = "G";
        String keyH = "H";
        myMap.put(keyA, "str01A");
        myMap.put(keyB, "str01B");
        myMap.put(keyC, "str01C");
        myMap.put(keyF, null);

        // 如果key不存在,则设为value,存在执行BiFunction
        myMap.merge(keyD, "merge01", String::concat);

        String msg = "msgCompute";
        // 如果key不存在,则添加key,value执行BiFunction；
        // 如果key存在，则value执行BiFunction
        myMap.compute(keyB, (k, v) -> (v == null) ? msg : v.concat(msg));
        myMap.compute(keyF, (k, v) -> (v == null) ? msg : v.concat(msg));
        System.out.println("Map customized BiFunction compute demo content:"+ myMap);

//        myMap.computeIfAbsent(keyC, k -> genValue(k));
        // 缺少key,对key进行赋值
        myMap.computeIfAbsent(keyG, k -> genValue(k));
        // 包含key,对key进行赋值
        myMap.computeIfPresent(keyH, (k,v) -> genValue(v));
        System.out.println("Map customized Function computeIfAbsent demo content:"+ myMap);

//        BiFunction<String, String, String> biFunc = new BiFunction<String, String, String>(){
//            @Override
//            public String apply(String t, String u) {
//                String result = t;
//                if (t == null) {
//                    result = u;
//                }
//                else {
//                    result += "," + u;
//                }
//                return result;
//            }
//        };
//
//        myMap.computeIfPresent(keyC, biFunc);
//        myMap.computeIfPresent(keyH, biFunc);
//        System.out.println("Map customized biFunc computeIfPresent demo content:"+ myMap);
    }

    static String genValue(String str) {
        System.out.println("===");
        return str + "2";
    }
}
