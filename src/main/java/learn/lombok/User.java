package learn.lombok;


import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by YScredit on 2018/7/18.
 */
@Data
@AllArgsConstructor

public class User {

    private boolean sex;
    private int age;

    private String name;
}
