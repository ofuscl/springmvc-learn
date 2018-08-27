package learn.file.word;

/**
 * Created by yunfan on 2018/4/28.
 */
public class Student {

    private Integer id;//主键
    private String name;//姓名
    private String sex;//性别
    private String age;//年龄

    public Student(){

    }

    public Student(Integer id,String name,String sex,String age){
        this.id=id;
        this.name=name;
        this.sex=sex;
        this.age=age;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getAge() {
        return age;
    }
    public void setAge(String age) {
        this.age = age;
    }
}
