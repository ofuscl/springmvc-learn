package java8.fuction;

/**
 * Created by YScredit on 2018/10/17.
 */
public class LambdaThreadTest {

    public static void main(String[] args) {
        // Implementing Runnable using anonymous class (Old way)
        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread name1 : " + Thread.currentThread().getName());
            }
        };
        Thread thread1 = new Thread(runnable1);

        // Implementing Runnable using Lambda expression
        Runnable runnable2 = () -> {
            System.out.println("Thread name2 : " + Thread.currentThread().getName());
        };
        Thread thread2 = new Thread(runnable2);

        // Start Threads
        thread1.start();
        thread2.start();
    }
}
