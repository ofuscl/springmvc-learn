package learn;

import lombok.Data;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.List;

/**
 * yml特殊写法
 * Created by yunfan on 2018/7/24.
 */
public class YmlTest {

    public static void main(String[] args) throws Exception {

        // 读入ymal的配置文件
        Yaml yaml = new Yaml();
        User user1 = yaml.loadAs(new FileInputStream(new File("src/main/resources/yml/test.yml")), User.class);
        System.out.println(user1.toString());

    }

    @Data
    public class User {

        private Long id;
        private String name;
        private Integer age;
        private Date birthday;
        private float height;
        private double score;
        private boolean isVip;

        private String[] hobbies;
        private List<Address> addresses;
        private UserInfo userInfo;
    }

    @Data
    public class Address {

        private Long id;
        private String address;
    }

    @Data
    public class UserInfo {

        private Long userId;
        private String username;
    }
}
