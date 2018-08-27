package learn.yml;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created by yunfan on 2018/7/24.
 */
public class YmlTest {

    public static void main(String[] args) throws Exception {

        // 读入ymal的配置文件
        Yaml yaml = new Yaml();
        User user1 = yaml.loadAs(new FileInputStream(new File("src/main/resources/yml/test.yml")), User.class);
        System.out.println(user1.toString());

    }
}
