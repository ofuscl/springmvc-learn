package learn;

import java.util.ArrayList;
import java.util.List;

/**
 * 钩子
 * 作用：1、应用程序正常退出，在退出时执行特定的业务逻辑，或者关闭资源等操作
 * 作用：2、虚拟机非正常退出，比如用户按下ctrl+c、OutofMemory宕机、操作系统关闭等。在退出时执行必要的挽救措施
 *
 */
public class HookTest {

    static String xx = "";

    public static void start(){
        System.out.println("The JVM is started");
        Runtime.getRuntime().addShutdownHook(new Thread(){
            public void run(){
                try{
                    while (true){
                        Thread.sleep(30000);
                        //do something
                        if ("sss".equals(xx)){
                            System.out.println("The JVM Hook is execute");
                        } else {
                            System.out.println("The JVM Hook is hhahah");
                        }

                    }

                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println("The Application is doing something");
    }

    public static void main(String[] args) {

        start();
        try {
            List<HookTest> xxList = new ArrayList<>();
            while (true){
                xxList.add(new HookTest());
            }

//            System.out.println("The Application continues to do something");
//            Thread.sleep(3000);
        } catch (Exception e) {
            xx = "sss";
            e.printStackTrace();
        }
    }

}
