package demo.design;

/**
 * Created by yunfan on 2018/4/23.
 */
public class Fruit<T> {

    private T x;

    public Fruit(T x){
        this.x = x;
    }

    public T getX() {
        return x;
    }

    public void setX(T x) {
        this.x = x;
    }
}
