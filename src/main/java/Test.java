import learn.schema.User;

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

    public static void main(String[] args) throws Exception{
        System.out.println("com/page+".replaceAll("\\+",""));
        System.out.println("com.scl".replaceAll("\\.",Matcher.quoteReplacement(File.separator)));
    }
}
