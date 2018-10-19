package base.thread.forkJoin;

import org.junit.Test;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

/**
 * 达到千万级别的计算时，Java8的并行流效果性能很好
 * 低于千万级别的计算时，普通的循环性能最佳
 * Created by YScredit on 2018/10/19.
 */
public class ForkJoinWorkTest {


    /**  */
    private final static Long endLong  = 10000L;

    @Test
    public  void test() {
        //ForkJoin实现
        long l = System.currentTimeMillis();
        ForkJoinPool forkJoinPool = new ForkJoinPool();//实现ForkJoin 就必须有ForkJoinPool的支持
        ForkJoinTask<Long> task = new ForkJoinWork(0L,endLong);//参数为起始值与结束值
        Long invoke = forkJoinPool.invoke(task);
        long l1 = System.currentTimeMillis();
        System.out.println("invoke1 = " + invoke+"  time: " + (l1-l));
        //invoke = -5340232216128654848  time: 76474
    }

    @Test
    public void test2(){
        //普通线程实现
        Long x = 0L;
        Long y = endLong;
        long l = System.currentTimeMillis();
        for (Long i = 0L; i <= y; i++) {
            x+=i;
        }
        long l1 = System.currentTimeMillis();
        System.out.println("invoke2 = " + x+"  time: " + (l1-l));
        //invoke = -5340232216128654848  time: 160939
    }
    @Test
    public void test3(){
        //Java 8 并行流的实现
        long l = System.currentTimeMillis();
        long reduce = LongStream.rangeClosed(0, endLong).parallel().reduce(0, Long::sum);
        long l1 = System.currentTimeMillis();
        System.out.println("invoke3 = " + reduce+"  time: " + (l1-l));
        //invoke = -5340232216128654848  time: 15531
    }
}
