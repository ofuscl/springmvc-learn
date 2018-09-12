package util.valite2;

import java.io.Serializable;

/**
 * Created by yunfan on 2018/8/1.
 */
@ItemValidator(required = true)
public class TestVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private  String name;


    @Range(min = 18,max = 65,message = "年龄不符合要求！")
    @Phone
    private  int age;
    private  String date;
    @Phone
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
