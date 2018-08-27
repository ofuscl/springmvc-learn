package learn.file.excel;

import learn.file.word.WordTest;
import net.sf.jxls.transformer.XLSTransformer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 报表导出工具类
 *
 * @author yunfan
 */
public class ExcelExportUtil {

    private static final String templateFolder = WordTest.class.getResource("/").getPath() + "template/excel/";

    public static void main(String[] args) throws Exception {

        List<Staff> staffs = new ArrayList<Staff>();
        staffs.add(new Staff("张三", "6000", "3000","",""));
        staffs.add(new Staff("李四", "5000", "2000","",""));
        staffs.add(new Staff("王五", "4000", "1000","",""));

        Map<String, Object> params = new HashMap<>();
        params.put("staffs", staffs);
        User user = new User("张三","2018-08-24","男");
        params.put("user", user);

        // XLST模板转化
        XLSTransformer former = new XLSTransformer();
        former.transformXLS(templateFolder+"demo.xlsx", params, "F:/test/dest.xlsx");

        System.out.println("the end !!!");
    }
}




