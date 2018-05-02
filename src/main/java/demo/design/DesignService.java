package demo.design;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by YScredit on 2018/4/23.
 */
public class DesignService {

    private static List<Apple> appleList = Arrays.asList(new Apple[]{
        new Apple("red", "80"), new Apple("green", "100"), new Apple("yellow", "120")
    });
    private static List<Banala> banalaList = Arrays.asList(new Banala[]{
            new Banala("yellow", "80","s"), new Banala("green", "100","b"), new Banala("yellow", "120","b")
    });

    private static List<Fruit> fruitList = Arrays.asList(new Fruit[]{
            new Fruit(new Banala("yellow", "80","s")), new Fruit(new Banala("green", "100","b")), new Fruit(new Banala("yellow", "120","b")),
            new Fruit(new Apple("green", "100")),new Fruit(new Apple("yellow", "120"))
    });

    public static void main(String[] args) {
//        Fruit<Apple> apple = new Fruit<>(new );
//        System.out.println(getApple("red","80").toString());
//        System.out.println(getBanala("yellow","80","s").toString());
//        System.out.println();
//        System.out.println(getFruit("red","80","s").toString());
    }

    public static Fruit getFruit(Fruit f){

//        for(Fruit fruit : fruitList){
//            if(fruit.getX()){
//                System.out.println(fruit.getX());
//            }
//        }
        return null;
    }

    public static Apple getApple(String red ,String weight){
        for(Apple apple : appleList){
            if(apple.getColor().equals(red) && apple.getWeight().equals(weight)){
                return apple;
            }
        }
        return null;
    }

    public static Banala getBanala(String red ,String weight,String size){
        for(Banala banala : banalaList){
            if(banala.getColor().equals(red) && banala.getWeight().equals(weight)  && banala.getSize().equals(size)){
                return banala;
            }
        }
        return null;
    }
}
