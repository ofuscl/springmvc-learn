package learn.designPattern.factory;

/**
 * Created by yunfan on 2018/7/18.
 */
public class Circle implements Shape {

    @Override
    public void draw() {
        System.out.println("Inside Circle::draw() method.");
    }
}
