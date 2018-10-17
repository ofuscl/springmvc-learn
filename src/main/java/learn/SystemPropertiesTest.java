package learn;

public class SystemPropertiesTest {

    public static void main(String[] args) {

        // 系统参数
        System.out.println(System.getProperty("http.maxConnections", "5"));
        System.out.println(System.getProperty("http.maxConnections"));
        System.out.println(System.getProperty("java.version"));
    }
}
