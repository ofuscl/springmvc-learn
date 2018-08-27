package java8;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yunfan on 2018/7/18.
 */
public class ArrayListTest {

    public static void main(String[] args) {

        Map<String ,String> xxmap = new HashMap<>();
        xxmap.put("a","1");
        xxmap.put("b","2");
        xxmap.put("c","3");

        List<String> xlist = new ArrayList<>(xxmap.values());
        xlist.forEach(k ->System.out.println(k));


    }
}
