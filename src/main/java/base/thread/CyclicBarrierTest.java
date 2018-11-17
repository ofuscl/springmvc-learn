package base.thread;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 栅栏，只有当线程都到齐了，才能各就各位起跑
 * Created by YScredit on 2018/11/1.
 */
public class CyclicBarrierTest {

    public static void main(String args[]) throws InterruptedException {
        // 所有线程先准备完毕，只有达到所有指定线程数时才可以执行
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("线程1准备好了");
                try {
                    Thread.sleep(new Random().nextInt(1000));
                    cyclicBarrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("阻塞结束，线程1开始执行");
            }
        };
        Runnable runnable2 = new Runnable() {
            @Override
            public void run() {
                System.out.println("线程2准备好了");
                try {
                    Thread.sleep(new Random().nextInt(1000));
                    cyclicBarrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("阻塞结束，线程2开始执行");
            }
        };
        Runnable runnable3 = new Runnable() {
            @Override
            public void run() {
                System.out.println("线程3准备好了");
                try {
                    Thread.sleep(new Random().nextInt(1000));
                    cyclicBarrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("阻塞结束，线程3开始执行");
            }
        };
        Runnable runnable4 = new Runnable() {
            @Override
            public void run() {
                System.out.println("线程4准备好了");
                try {
                    Thread.sleep(new Random().nextInt(1000));
                    cyclicBarrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("阻塞结束，线程4开始执行");
            }
        };
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
        fixedThreadPool.execute(runnable1);
        fixedThreadPool.execute(runnable2);
        fixedThreadPool.execute(runnable3);
        fixedThreadPool.execute(runnable4);
        fixedThreadPool.shutdown();
    }
}
