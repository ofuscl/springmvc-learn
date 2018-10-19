package base.thread;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by YScredit on 2018/10/19.
 */
public class ScheduledExecutorTest {

    public static void main(String[] args) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);

        Runnable task = new Runnable() {
            @Override
            public void run() {
                System.out.println("每5秒执行一次");
            }
        };
        //5秒后第一次执行，之后每隔3秒执行一次
        executor.scheduleAtFixedRate(task,5,3, TimeUnit.SECONDS);
    }
}
