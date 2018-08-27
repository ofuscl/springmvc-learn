package thread;

import demo.util.comm.StringUtil;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
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
