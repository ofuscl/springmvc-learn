import learn.schema.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServlet;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Matcher;

/**
 * Created by yunfan on 2018/7/16.
 */
public class Test {

    public static void main(String[] args){

        Map<String, Integer> myMap = new HashMap<>();
        String keyA = "A";
        String keyB = "B";
        String keyC = "C";
        String keyD = "D";
        String keyE = "E";
        String keyF = "F";
        String keyG = "G";
        String keyH = "H";
        myMap.put(keyA, 5);
//        myMap.put(keyB, "str01B");
//        myMap.put(keyC, "str01C");

//        myMap.compute(keyA,(k,v) -> (v == null)? 1:(v + 1));

        String transaction = "10000万元";
        String  t = transaction.replaceAll("[^\\d.]","");
        System.out.println(Double.parseDouble(t));
        System.out.println(transaction);
    }
}
