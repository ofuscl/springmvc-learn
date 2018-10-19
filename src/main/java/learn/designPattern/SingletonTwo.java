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
    /**
     * 提供对外方法。
     * 有使用synchronized关键字来同步获取实例，保证单例的唯一性，
     * 但是代码在每一次执行时都要进行同步和判断，无疑会拖慢速度，使用双重加锁机制正好可以解决这个问题
     */

    public static SingletonTwo getInstance() {
        if (singleton == null) {        //这里的双重判断值得看一下
            return singleton;
        }
        synchronized (SingletonTwo.class) {
            if (singleton == null) {   //这里的双重判断值得看一下  为了防止并发访问
                singleton = new SingletonTwo();
            }
        }
        return singleton;
    }
}
