package learn;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 队列
 */
public class QueueTest {

    public static void main(String[] args) {
        Queue<String> strQueue = new ConcurrentLinkedDeque<>();

        strQueue.offer("A");
        strQueue.offer("B");

//        strQueue.forEach(b ->{
//            System.out.println(strQueue.poll());
//        });


        for(int i =0; i < 100;i++){
            if(strQueue.size() == 0){
                strQueue.offer("C");
            }
            System.out.println(strQueue.poll());
        }

    }
}
