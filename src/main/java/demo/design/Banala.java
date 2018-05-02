package demo.design;

/**
 * Created by YScredit on 2018/4/23.
 */
public class Banala {

    private String color;
    private String weight;
    private String size;


    public Banala(String color, String weight, String size){
        this.color = color;
        this.weight = weight;
        this.size = size;
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
