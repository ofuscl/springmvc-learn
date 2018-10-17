package learn;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by yunfan on 2018/7/18.
 */
@Data
public class LombokTest {
    public static void main(String[] args) {
        User user = new User(true,1,"夏利");
        System.out.println(user);
    }

    @Data
    @AllArgsConstructor

    public static class User {

        private boolean sex;
        private int age;

        private String name;
    }
}
