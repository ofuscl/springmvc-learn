package learn.designPattern;

/**
 * 懒汉模式
 * Created by YScredit on 2018/10/17.
 */
public class SingletonTwo {

    //声明变量
    private static volatile SingletonTwo singleton = null;
    //私有构造函数
    private SingletonTwo(){
    }
    //提供对外方法
    public static SingletonTwo getInstance() {
        if (singleton == null) {        //这里的双重判断值得看一下
            synchronized (SingletonTwo.class) {
                if (singleton == null) {   //这里的双重判断值得看一下  为了防止并发访问
                    singleton = new SingletonTwo();
                }
            }
        }
        return singleton;
    }
}
