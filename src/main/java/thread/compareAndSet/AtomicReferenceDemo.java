package thread.compareAndSet;

import java.util.concurrent.atomic.AtomicReference;

/**
 *  AtomicReference和AtomicInteger非常类似，不同之处就在于AtomicInteger是对整数的封装，而AtomicReference则对应普通的对象引用。
 *  也就是它可以保证你在修改对象引用时的线程安全性。
 *  在介绍AtomicReference的同时，我希望同时提出一个有关原子操作的逻辑上的不足。
 * @url https://www.cnblogs.com/skywang12345/p/3514623.html
 * // 使用 null 初始值创建新的 AtomicReference。
 * AtomicReference()
 * // 使用给定的初始值创建新的 AtomicReference。
 * AtomicReference(V initialValue)
 *
 * // 如果当前值 == 预期值，则以原子方式将该值设置为给定的更新值。
 * boolean compareAndSet(V expect, V update)
 * // 获取当前值。
 * V get()
 * // 以原子方式设置为给定值，并返回旧值。
 * V getAndSet(V newValue)
 * // 最终设置为给定值。
 * void lazySet(V newValue)
 * // 设置为给定值。
 * void set(V newValue)
 * // 返回当前值的字符串表示形式。
 * String toString()
 * // 如果当前值 == 预期值，则以原子方式将该值设置为给定的更新值。
 * boolean weakCompareAndSet(V expect, V update)
 */
public class AtomicReferenceDemo {

    private Thread reactorThread;
    private final AtomicReference<Status> status = new AtomicReference<>();

    enum Status {INACTIVE, ACTIVE, STOPPED}

    public static void main(String[] args){

        // 创建两个Person对象，它们的id分别是101和102。
        Person p1 = new Person(101);
        Person p2 = new Person(102);
        // 新建AtomicReference对象，初始化它的值为p1对象
        AtomicReference ar = new AtomicReference(p1);
        // 通过CAS设置ar。如果ar的值为p1的话，则将其设置为p2。
        ar.compareAndSet(p1, p2);

        Person p3 = (Person)ar.get();
        System.out.println("p3 is "+p3);
        System.out.println("p3.equals(p1)="+p3.equals(p1));
    }

    public void start() {
        status.set(Status.INACTIVE);
        if (this.status.compareAndSet(Status.INACTIVE, Status.ACTIVE)) {
            if (this.reactorThread != null) {
                this.reactorThread.start();
            }
        }
    }
}
