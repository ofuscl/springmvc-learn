package javaagent;

import java.lang.instrument.Instrumentation;

/**
 * java的代理，在main方法前执行 ,必须变成jar
 * Created by YScredit on 2019/2/15.
 */
public class JavaAgent {


    /**
     * 该方法在main方法之前运行，与main方法运行在同一个JVM中
     *
     * @param agentArgs
     * @param inst
     * @author xifeijian
     * @create  2018年4月18日
     */
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("=========premain方法执行start========");
        inst.addTransformer(new Transformer());
        System.out.println("=========premain方法执行end========");
        System.out.println(agentArgs);

    }


    /**
     * 如果不存在 premain(String agentArgs, Instrumentation inst)
     * 则会执行 premain(String agentArgs)
     *
     * @param agentArgs
     * @author xifeijian
     * @create  2018年4月18日
     */
    public static void premain(String agentArgs) {
        System.out.println("=========premain方法执行2========");
        System.out.println(agentArgs);
    }


}
