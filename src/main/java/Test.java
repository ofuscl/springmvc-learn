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

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(Test.class);

    public static void main(String[] args) throws Exception{

        System.out.println(test(9));
    }

    private static int test(int k){
        int i =0;
        if (k == 10){
            final int x = i;
            return x;
        }
        return k;
    }
}
