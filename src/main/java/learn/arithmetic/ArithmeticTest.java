package learn.arithmetic;

import biz.file.excel.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 算法。
 */
public class ArithmeticTest {

    public static void main(String[] args) {


        List<User> list = new ArrayList<>();
        list.add(new User("张三","10","s"));
        list.add(new User("张三","9","s"));
        list.add(new User("张三","8","s"));
        list.add(new User("张三","7","s"));
        list.add(new User("张三","6","s"));
        list.add(new User("张三","5","s"));
//        list.add(new User("张三","4","s"));
        List<User> newlist = sortByMiddle(list);

        for (User u : newlist) {
            System.out.println(u.getBirthDay());
        }
    }


    /**
     * Java驼峰排序（两边小，中间集中最大）
     */
    private static void sortByMiddle() {

        Integer[] array = {10,9,8,7,6,5,4,3,2,1};
//        int[] array = {9,8,7,6,5,4,3,2,1};
        int middleLength = array.length/2 ==0? array.length/2:array.length/2+1;

        int a=middleLength-1,b=middleLength-1;

        Integer[] ass = new Integer[array.length];
        for (int i = 0; i < array.length; i++){
            if(i%2 ==0){
                if(b <= array.length){
                    ass[b] = array[i];
                }
                b++;
            } else {
                a--;
                if(a > -1){
                    ass[a] = array[i];
                }
            }
        }

        for (int i = 0; i < ass.length; i++){
            System.out.println(ass[i]);
        }

    }

    public static <T> List<T> sortByMiddle(List<T> list) {

        if(list.size() < 3){
            return list;
        }
        int middleLength = list.size()/2 ==0? list.size()/2:list.size()/2+1;

        int a=middleLength-1,b=middleLength-1;

        Object[] ass = new Object[list.size()];
        for (int i = 0; i < list.size(); i++){
            if(i%2 ==0){
                if(b < list.size()){
                    ass[b] = list.get(i);
                }
                b++;
            } else {
                a--;
                if(a > -1){
                    ass[a] = list.get(i);
                }
            }
        }

        return (List<T>)Arrays.asList(ass);
    }
}
