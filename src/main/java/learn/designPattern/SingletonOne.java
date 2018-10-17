package learn.designPattern;

/**
 * 饿汉模式
 * Created by YScredit on 2018/10/17.
 */
public class SingletonOne {


    //直接创建对象
    public static SingletonOne instance = new SingletonOne();
    //私有构造函数
    private SingletonOne() {
    }
}
