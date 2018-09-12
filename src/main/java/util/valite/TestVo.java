package util.valite;

import util.valite2.Phone;

import java.io.Serializable;

/**
 * Created by yunfan on 2018/8/1.
 */
@ItemValidator(required = true)
public class TestVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private  String name;


    @ParamValidator("age,年龄,V_range")
    private  int age;
    private  String date;
    private  String phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
