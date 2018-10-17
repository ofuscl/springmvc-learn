package java8.fuction;

import java.util.function.Supplier;

/**
 * Supplier接口表示不带参数的操作并返回结果R
 * Created by YScredit on 2018/10/17.
 */
public class SupplierTest {

    public static void main(String[] args) {
        Supplier<String> supplierStr = () -> {
            return "Hello world.";
        };

        Supplier<Number> supplierNum = () -> {
            return 40;
        };

        System.out.println(supplierStr.get());
        System.out.println(supplierNum.get());
    }
}
