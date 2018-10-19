package base.thread;

/**
 * Created by YScredit on 2018/10/19.
 */
public class SynchronizedTest {

    public static void main(String[] args) {

    }

    /**
     * 同步锁计数问题。
     */
    public static class Cache{
        public static int count = 0;
        public synchronized static void add() {
            count++;
        }
    }
}
