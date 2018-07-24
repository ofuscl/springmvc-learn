package learn.reflect;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * Created by YScredit on 2018/4/20.
 */
public class ReflectTest {

    public static void main(String[] args) {
        ReflectTest reflect = new ReflectTest();
        reflect.getClassName();
        reflect.getClassPath();
        reflect.testClassLoader1();

//        try{
//            Class<?> clz = Class.forName("learn.reflect.ReflectDemo");
//        Object o = clz.newInstance();
//        Method m = clz.getMethod("setXx", String.class);
//        for (int i = 0; i < 16; i++) {
//            m.invoke(o, Integer.toString(i));
//        }
//
//        }catch (Exception e){
//            System.out.println(e);// setXx
//        }

    }

    /**
     * 获取实例名
     *
     */
    private Class<?> getClass(String type){
        if("class".equals(type)){
            return ReflectDemo.class;
        }else if("getClass".equals(type)){
            return new ReflectDemo().getClass();
        }else if("forName".equals(type)){
            try{
                return Class.forName("learn.reflect.ReflectDemo");
            }catch (Exception e){
                System.out.println(e);
            }
        }
        return null;
    }

    private void getClassName(){
        Class<?> clazz = getClass("getClass");

        System.out.println(clazz.getName());// learn.reflect.ReflectDemo
        System.out.println(clazz.getSimpleName());// ReflectDemo
        System.out.println(clazz.getConstructors());// [Ljava.lang.reflect.Constructor;@19d7047
        System.out.println(clazz.getDeclaredConstructors());// [Ljava.lang.reflect.Constructor;@19d7047
        System.out.println(clazz.getDeclaredFields()[0].getName());// xx
        try{
            ReflectDemo instance = (ReflectDemo)clazz.newInstance();
            Method method = clazz.getMethod("setXx",new Class[]{String.class});
            method.invoke(instance,new Object[]{"好好学习"});
            System.out.println(instance.getXx());// setXx
        }catch (Exception e){
            System.out.println(e);// setXx
        }

    }

    private void getClassPath(){
        Class<?> clazz = getClass("forName");
        System.out.println(clazz.getResource(""));// file:/F:/workspace/spring-mvc-demo/target/classes/learn/reflect/
        System.out.println(clazz.getResourceAsStream(""));// java.io.ByteArrayInputStream@198a335
        System.out.println(clazz.getResource("/"));// file:/F:/workspace/spring-mvc-demo/target/classes/
        System.out.println(clazz.getResourceAsStream("/"));// java.io.ByteArrayInputStream@b98e56
        // 当前类(class)所在的包目录
        System.out.println(clazz.getClassLoader().getResource(""));// file:/F:/workspace/spring-mvc-demo/target/classes/
        // class path根目录
        System.out.println(clazz.getClassLoader().getResource("learn/reflect/ReflectDemo.java"));// file:/F:/workspace/spring-mvc-demo/target/classes/

        //5、关于类加载器的一个主要方法
        //调用getResourceAsStream 获取类路径下的文件对应的输入流
        try{
            InputStream in = this.getClass().getClassLoader().getResourceAsStream("/resources/properties/servletContext.properties");
            System.out.println("in: " +in);
            Properties properties = new Properties();
            properties.load(in);

            String driverClass = properties.getProperty("dirver");
            String jdbcUrl = properties.getProperty("jdbcUrl");
            //中文可能会出现乱码，需要转换一下
            String user = new String(properties.getProperty("user").getBytes("ISO-8859-1"), "UTF-8");
            String password = properties.getProperty("password");

            System.out.println("diverClass: "+driverClass);
            System.out.println("user: " + user);
        }catch (IOException e){
            System.out.println(e);
        }
    }

    /**
     * ClassLoader类装载器
     */
    public void testClassLoader1() {
        //1、获取一个系统的类加载器
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        System.out.println("系统的类加载器-->" + classLoader);

        //2、获取系统类加载器的父类加载器(扩展类加载器（extensions classLoader）)
        classLoader = classLoader.getParent();
        System.out.println("扩展类加载器-->" + classLoader);

        //3、获取扩展类加载器的父类加载器
        //输出为Null,无法被Java程序直接引用
        classLoader = classLoader.getParent();
        System.out.println("启动类加载器-->" + classLoader);

        //
        try {
            //4、测试当前类由哪个类加载器进行加载 ,结果就是系统的类加载器
            classLoader = Class.forName("learn.reflect.ReflectDemo").getClassLoader();
            System.out.println("当前类由哪个类加载器进行加载-->"+classLoader);

            //5、测试JDK提供的Object类由哪个类加载器负责加载的
            classLoader = Class.forName("java.lang.Object").getClassLoader();
            System.out.println("JDK提供的Object类由哪个类加载器加载-->" + classLoader);
        }catch(Exception e){

        }

    }
}
