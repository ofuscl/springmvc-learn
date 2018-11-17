package base.thread;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * 闭锁
 * CountDownLatch保证主线程下全部线程都执行完
 * Created by YScredit on 2018/11/1.
 */
public class CountDownLatchTest {

    public static void main(String[] args) {
        final int threadNum = 10;
        CountDownLatch countDownLatch = new CountDownLatch(threadNum);
        for (int i = 0; i < threadNum; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        long x = System.currentTimeMillis();
                        int xx = new Random().nextInt(10)*100;
                        System.out.println(xx);
                        Thread.sleep(xx);
                        System.out.println("休息时间："+String.valueOf(System.currentTimeMillis()-x));
                        countDownLatch.countDown();
                    } catch(Exception e){
                        System.out.println(e);
                    }
                }
            }).start();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    long x = System.currentTimeMillis();
                    int xx = new Random().nextInt(10)*100;
                    System.out.println(xx);
                    Thread.sleep(xx);
                    System.out.println("休息时间："+String.valueOf(System.currentTimeMillis()-x));
                    countDownLatch.countDown();
                } catch(Exception e){
                    System.out.println(e);
                }
            }
        }).start();

        try {
            // 等待10个线程全部执行完毕再进行
            countDownLatch.await();
            System.out.println("执行完毕啦");
        } catch(Exception e){
            System.out.println(e);
        }
    }
}
