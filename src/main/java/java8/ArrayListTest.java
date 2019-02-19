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


        // TODO 验证两个列表相互包含
        List<String> alist = new ArrayList<>();
        alist.add("a");
        alist.add("b");
        alist.add("c");

        List<String> blist = new ArrayList<>();
        blist.add("a1");
        blist.add("b1");
        blist.add("c");

        alist.retainAll(blist);

        if(alist.size() > 0){
            System.out.println("这两个集合有相同的交集");
            alist.forEach(a -> {
                System.out.println(a);
            });
        } else {
            System.out.println("这两个集合没有相同的交集");
        }
        System.out.println("-------------------------------------");

        List<String> clist = new ArrayList<>();
        clist.add("a");
        clist.add("b");
        clist.add("c");

        List<String> dlist = new ArrayList<>();
        dlist.add("a1");
        dlist.add("b1");
        dlist.add("c");
        clist.removeAll(dlist);
        clist.forEach(c -> {
            System.out.println(c);
        });
    }
}
