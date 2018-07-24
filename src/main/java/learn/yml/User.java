package learn.yml;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by YScredit on 2018/7/24.
 */
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
