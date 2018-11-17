package base.thread;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * AtomicInteger 并发计数器
 * Created by YScredit on 2018/11/1.
 */
public class AtomicIntegerTest {

    public static void main(String[] args) {

        AtomicInteger count = new AtomicInteger(0);
        CountDownLatch countDownLatch = new CountDownLatch(100);
        for (int i = 0; i < 100; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        count.incrementAndGet();
                        Thread.sleep(new Random().nextInt(10)*100);
                        countDownLatch.countDown();
                    } catch(Exception e){
                        System.out.println(e);
                    }
                }
            }).start();
        }

        try {
            countDownLatch.await();
            System.out.println("总数："+count.get());
        } catch(Exception e){
            System.out.println(e);
        }
    }
}
