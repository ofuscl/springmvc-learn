package learn.designPattern.factory;

/**
 * Created by YScredit on 2018/7/18.
 */
public class Rectangle implements Shape {

    @Override
    public void draw() {
        System.out.println("Inside Rectangle::draw() method.");
    }
}
