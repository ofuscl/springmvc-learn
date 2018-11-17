package util.circuitbreaker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * 熔断器
 * Created by YScredit on 2018/11/1.
 */
public class CircuitBreakerMain {

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(CircuitBreakerMain.class);
    public static void main(String[] args) throws InterruptedException{

        final int maxNum = 200;
        // 主线程在等待所有其它的子线程完成后再往下执行
        final CountDownLatch countDownLatch = new CountDownLatch(maxNum);
        final CircuitBreaker circuitBreaker = new LocalCircuitBreaker("5/20",10,"10/600",10);
        for (int i = 0; i < maxNum; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(new Random().nextInt(20)*1000);
                    } catch(Exception e){
                        logger.error("",e);
                    }
                    try {
                        if (circuitBreaker.canPassCheck()){
                            System.out.println("正常业务逻辑操作");
                            if(countDownLatch.getCount() >= maxNum/2){
                                if(new Random().nextInt(2) == 1){
                                    throw new Exception("mock error");
                                }
                            } else {
                                System.out.println("拦截业务逻辑操作");
                            }
                        }
                    } catch(Exception e){
                        System.out.println("业务执行失败了");
                        circuitBreaker.countFailNum();
                        countDownLatch.countDown();
                        logger.error("",e);
                    }
                }
            }).start();

            try {
                Thread.sleep(new Random().nextInt(5)*100);
            } catch(Exception e){
                logger.error("",e);
            }
        }

        countDownLatch.await();
        System.out.println("end");
    }
}
