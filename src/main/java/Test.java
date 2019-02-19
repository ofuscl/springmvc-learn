import learn.schema.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.JsonUtil;

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
        Map<String,String> xmap = new HashMap<>();
        xmap.put("a","1");
        xmap.put("b","2");
        xmap.put("c","3");
        Map<String,String> ymap = xmap;
        ymap.remove("a");

        System.out.println(JsonUtil.toJsonFromObject(xmap));
        System.out.println(JsonUtil.toJsonFromObject(ymap));
    }
}
