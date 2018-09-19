package learn.file;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ClassPath {



    public static void main(String[] args) {
        Properties config = new Properties();
        String type = "three";
        getPath(config,type);
        System.out.println(type+" : "+config.getProperty("package"));
    }

    private static void getPath(Properties config,String num){

        try {
            if("one".equals(num)){
                // 从根目录下查找文件
                InputStream is1 = ClassPath.class.getResourceAsStream("/properties/servletContext.properties");
                config.load(is1);
                is1.close();
            } else if("two".equals(num)){
                // 从根目录下查找文件
                InputStream is2 = ClassPath.class.getClassLoader().getResourceAsStream("properties/servletContext.properties");
                config.load(is2);
                is2.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
