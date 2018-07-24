package java8;

import sun.plugin.com.BeanClass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by YScredit on 2018/7/24.
 */
public class CollectionSort {

    /**
     * @param args
     */
    public static void main(String[] args) {

        CollectionSort sort = new CollectionSort();

        sort.test1();
        System.out.println("-----------------------------------------------------");
        sort.test2();
        System.out.println("-----------------------------------------------------");
        sort.test3();
    }

    // 第一种方法，Bean中实现Comparator接口
    public void test1(){
        List<BeanClass> list = new ArrayList<BeanClass>();  //BeanClass 需实现Comparable接口
        BeanClass bc1 = new BeanClass();
        BeanClass bc2 = new BeanClass();
        bc1.para = 1;
        bc2.para = 2;
        list.add(bc1);
        list.add(bc2);

        Collections.sort(list);
        System.out.println(list.get(0).para);
        System.out.println(list.get(1).para);
    }

    // 第二种方法，自定义比较器
    public void test2(){
        List<BeanClass> list = new ArrayList<BeanClass>();
        BeanClass bc1 = new BeanClass();
        BeanClass bc2 = new BeanClass();
        bc1.para = 1;
        bc2.para = 2;
        list.add(bc1);
        list.add(bc2);

        Collections.sort(list,new MyComparator());

        System.out.println(list.get(0).para);
        System.out.println(list.get(1).para);

    }


    // 第二种方法，自定义比较器
    public void test3(){
        List<BeanClass> list = new ArrayList<BeanClass>();
        BeanClass bc1 = new BeanClass();
        BeanClass bc2 = new BeanClass();
        bc1.para = 1;
        bc2.para = 2;
        list.add(bc1);
        list.add(bc2);

        Collections.sort(list,new Comparator<BeanClass>(){
            @Override
            public int compare(BeanClass o1, BeanClass o2) {
                return o1.para - o2.para;  //升序
//                return o2.para - o1.para;  //降序
            }
        });
        System.out.println(list.get(0).para);
        System.out.println(list.get(1).para);
    }


    public class BeanClass implements Comparable<BeanClass>{
        int para;

        public int compareTo(BeanClass bc) {
//		return this.para - bc.para; //升序
            return bc.para-this.para; //降序
        }
    }


    public class MyComparator implements Comparator<BeanClass>{

        public int compare(BeanClass bc1, BeanClass bc2) {
            //return bc1.para-bc2.para; //升序
            return bc2.para-bc1.para; //降序
        }
    }
}

