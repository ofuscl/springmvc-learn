package learn.schema;

public class User {

    private long age;
    private String name;
    private  String bname;

    public User(){}

    public User(long age,String name){
        this.age=age;
        this.name=name;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getAge() {
        return age;
    }

    public void setAge(long age) {
        this.age = age;
    }


    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }
}
