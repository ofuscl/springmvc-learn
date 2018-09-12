package learn.file.excel;

import learn.file.word.WordTest;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * 报表导出工具类
 *
 * @author yunfan
 */
public class ExcelExportUtil {

    private static final String templateFolder = WordTest.class.getResource("/").getPath() + "template/excel/";

    public static void main(String[] args) throws Exception {

        alone();

        list();

        merge();
    }

    /**
     * 单独
     */
    private static void alone () throws Exception{

        Map<String, Object> params = new HashMap<>();
        User user = new User("张三","2018-08-24","男");
        params.put("user", user);

        System.out.println(templateFolder);
        // XLST模板转化
        XLSTransformer former = new XLSTransformer();
        former.transformXLS(templateFolder+"demo_alone.xlsx", params, "F:/test/demo_alone.xlsx");

        System.out.println("the end !!!");
    }

    /**
     * 列表
     */
    private static void list()  throws Exception{

        List<Staff> staffs = new ArrayList<Staff>();
        staffs.add(new Staff("2018-06-11","张三", "6000", "3000","男"));
        staffs.add(new Staff("2018-07-11","李四", "5000", "2000","男"));
        staffs.add(new Staff("2018-08-01","王五", "4000", "1000","女"));
        staffs.add(new Staff("2018-08-21","王五", "3000", "500","女"));

        Map<String, Object> params = new HashMap<>();
        params.put("staffs", staffs);

        System.out.println(templateFolder);
        // XLST模板转化
        XLSTransformer former = new XLSTransformer();
        former.transformXLS(templateFolder+"demo_list.xlsx", params, "F:/test/demo_list.xlsx");

        System.out.println("the end !!!");
    }

    /**
     * 合并单元格
     */
    private static void merge()  throws Exception{

        List<Staff> staffs = new LinkedList<>();
        staffs.add(new Staff("2018-06-11","张三", "6000", "3000","男"));
        staffs.add(new Staff("2018-07-11","李四", "5000", "2000","男"));
        staffs.add(new Staff("2018-08-01","王五", "4000", "1000","女"));
        staffs.add(new Staff("2018-08-21","王五", "3000", "500","女"));
        staffs.add(new Staff("2018-08-01","吴六", "4000", "1000","女"));
        staffs.add(new Staff("2018-08-21","吴六", "3000", "1500","女"));
        staffs.add(new Staff("2018-08-21","吴六", "2000", "4500","女"));

        Map<String, Object> params = new HashMap<>();
        params.put("staffs", staffs);
        User user = new User("张三","2018-08-24","男");
        params.put("user", user);

        System.out.println(templateFolder);
        // XLST模板转化
        XLSTransformer former = new XLSTransformer();
        InputStream is = new FileInputStream(templateFolder+"demo_merge.xlsx");

        XSSFWorkbook workbook = (XSSFWorkbook)former.transformXLS(is, params);
        XSSFSheet sheet = workbook.getSheetAt(0);

        // 第一步：需要对数据进行排序，把需要合并的数据集中起来
        // 第二步：固定列，计算合并的起止行数
        int firstCol = 0; // 开始列
        int lastCol = 0; // 终止列
        int pre = 2; // 起始行
        Map<String,CellRangeAddress> mergeMap = new LinkedHashMap<>();
        for (int i = 0; i < staffs.size(); i++){
            if (mergeMap.containsKey(staffs.get(i).getName())){
                CellRangeAddress cell = mergeMap.get(staffs.get(i).getName());
                cell.setLastRow(i+pre);
            } else {
                CellRangeAddress cell = new CellRangeAddress(i+pre,i+pre,firstCol,lastCol);
                mergeMap.put(staffs.get(i).getName(),cell);
            }
        }
        //参数1：起始行号 参数2：终止行号 参数3：起始列号 参数4：终止列号 下标从0开始
        for (CellRangeAddress cell : mergeMap.values()){
            sheet.addMergedRegion(cell);
        }

        OutputStream os = new FileOutputStream("F:/test/demo_merge.xlsx");
        workbook.write(os);
        is.close();
        os.flush();
        os.close();

        System.out.println("the end !!!");
    }
}




