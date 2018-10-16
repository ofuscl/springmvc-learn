package thread.Future;

import java.util.concurrent.*;

/**
 * 多线程结束处理。
 *  @url https://blog.csdn.net/u014209205/article/details/80598209
 *  需求场景：等早餐过程中，包子需要3秒，凉菜需要1秒，
 *  普通的多线程需要四秒才能完成。先等凉菜，再等包子，
 *  因为等凉菜时，普通多线程启动start()方法，执行run()中具体方法时，没有返回结果，
 *  所以如果要等有返回结果，必须是要1秒结束后才知道结果
 */
public class FutureDemo {

    public static void main(String[] args) {

        try {
//            oldDone();//旧的处理方法
            System.out.println("------------------------------------------------------------");
            futureDone();//新的处理方法
//            System.out.println("------------------------------------------------------------");
//            threadPool();
            threadPool();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void futureDone() throws Exception {
        long start = System.currentTimeMillis();

        // 等凉菜
        FutureTask<String> ft1 = new FutureTask<String>(() ->{
            System.out.println("准备q");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "凉菜准备完毕";
        });
        new Thread(ft1).start();

        // 等包子 -- 必须要等待返回的结果，所以要调用join方法
        FutureTask<String> ft2 = new FutureTask<String>(() -> {
            System.out.println("准备v");
            String x = null;
            if (x.equals("1")){
                return  "xx";
            }
            try {
                Thread.sleep(1000*3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "包子准备完毕";
        });
        new Thread(ft2).start();

        System.out.println(ft1.get()); // get方法可以当任务结束后返回一个结果，如果调用时，工作还没有结束，则会阻塞线程，直到任务执行完毕
        System.out.println(ft2.get());

        long end = System.currentTimeMillis();
        System.out.println("准备完毕时间："+(end-start));
    }

    private static void oldDone() throws Exception{

        long start = System.currentTimeMillis();

        // 等凉菜 -- 必须要等待返回的结果，所以要调用join方法
        Thread t1 = new ColdDishThread();
        t1.start();
        t1.join();

        // 等包子 -- 必须要等待返回的结果，所以要调用join方法
        Thread t2 = new BumThread();
        t2.start();
        t2.join();

        long end = System.currentTimeMillis();
        System.out.println("准备完毕时间："+(end-start));
    }

    private static void threadPool(){
        long start = System.currentTimeMillis();
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5, 5, 500L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
        poolExecutor.execute(new ColdDishThread());
        poolExecutor.execute(new BumThread());

        // 判断线程完结
        while (true) {
            if(poolExecutor.isTerminated() || poolExecutor.getActiveCount() ==0){
                long end = System.currentTimeMillis();
                System.out.println("准备完毕时间："+(end-start));
                break;
            }
        }
    }
}
