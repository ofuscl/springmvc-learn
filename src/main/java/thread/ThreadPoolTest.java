package thread;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ThreadPoolExecutor
 *
 * 说明：
 * corePoolSize：线程池核心线程数（平时保留的线程数） ；
 * maximumPoolSize：线程池最大线程数（当workQueue都放不下时，启动新线程，最大线程数）；
 * keepAliveTime：超出corePoolSize数量的线程的保留时间；
 * unit：keepAliveTime单位 ；
 * workQueue：阻塞队列，存放来不及执行的线程  -- LinkedBlockingQueue：构造函数不传大小会默认为65536（Integer.MAX_VALUE ），当大量请求任务时，容易造成 内存耗尽。
 * Created by yunfan on 2018/7/13.
 */
public class ThreadPoolTest {

    private ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(150, 150, 500L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

    public static void main(String[] args) {

        ThreadPoolTest test = new ThreadPoolTest();

        for (int i =0; i < 10; i++){
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    int count = 0;
                    while (true){
                        count++;
                        if(count > 6){//2分钟爬取两分钟
                            break;
                        }
                        try {
                            System.out.println(Thread.currentThread().getName()+ " | count = "+count +" | now="+test.poolExecutor.getTaskCount());
                            Thread.sleep(1000);
                        }catch (Exception e){
                            System.out.println(e);
                        }
                    }

                }
            });

            test.poolExecutor.execute(thread);
        }
    }
}
