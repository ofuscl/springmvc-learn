package learn.designPattern.factory;

/**
 * Created by yunfan on 2018/7/18.
 */
public class Square implements Shape {

    @Override
    public void draw() {
        System.out.println("Inside Square::draw() method.");
    }
}
