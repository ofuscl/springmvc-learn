package demo.design;

/**
 * Created by yunfan on 2018/4/23.
 */
public class Apple {

    private String color;
    private String weight;

    public Apple(String color,String weight){
        this.color = color;
        this.weight = weight;
    }


    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
