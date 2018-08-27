package learn.lombok;

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
}
