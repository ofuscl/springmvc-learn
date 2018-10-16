package learn.file.excel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.StringUtil;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.*;

/**
 * 报表导出工具类
 *
 * @author yunfan
 */
public class ExcelExportUtil {

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(ExcelExportUtil.class);

    private static final String BASE_PARH = "F:/test/";
    private static final String TEMPLATE_PATH = ExcelExportUtil.class.getResource("/").getPath() + "template/excel/";

    public static void main(String[] args) throws Exception {

        String destPath = "demo_alone.xlsx";
        String templatePath = "demo_alone.xlsx";

        List<Staff> staffs = new LinkedList<>();
        staffs.add(new Staff("2018-06-11","张三", "6000", "3000","男"));
        staffs.add(new Staff("2018-07-11","李四", "5000", "2000","男"));
        staffs.add(new Staff("2018-08-01","王五", "4000", "1000","女"));
        staffs.add(new Staff("2018-08-21","王五", "3000", "500","女"));
        staffs.add(new Staff("2018-08-01","吴六", "4000", "1000","女"));
        staffs.add(new Staff("2018-08-21","吴六", "3000", "1500","女"));
        for (int i =0; i < 1000; i++){
            staffs.add(new Staff("2018-08-21","吴六", "2000", "4500","女"));
        }

        Map<String, Object> params = new HashMap<>();
        params.put("staffs", staffs);
        User user = new User("张三","2018-08-24","男");
        params.put("user", user);


//        alone(params,templatePath,destPath);
//        list(params,templatePath,destPath);
//        merge(params,calculateMergeMap(params),templatePath,destPath);


        Map<String,String> fieldMap = new LinkedHashMap<>();
        fieldMap.put("birthDay", "生日");
        fieldMap.put("name", "姓名");
        fieldMap.put("payment", "薪资");
        fieldMap.put("bonus", "年终奖");
        fieldMap.put("sex", "性别");
        List<Item> items = new ArrayList<Item>(){{add(new Item("staffs","台账信息",fieldMap,staffs));}};
        long s = System.currentTimeMillis();
        dynamicSxssf(items,"demo_11111.xlsx");
        System.out.println(String.valueOf(System.currentTimeMillis()-s));

//        long s1 = System.currentTimeMillis();
//        list(params,"demo_list.xml","demo_list.xlsx");
//        System.out.println(String.valueOf(System.currentTimeMillis()-s1));
    }

    /**
     * 单独
     */
    public static void alone (Map<String,Object> params,String templateName,String fileName) throws Exception{

        // XLST模板转化
        XLSTransformer former = new XLSTransformer();
        former.transformXLS(TEMPLATE_PATH+templateName, params, BASE_PARH+fileName);
        logger.info("excel生成成功!!!");
    }

    /**
     * 列表
     */
    public static void list(Map<String,Object> params,String templateName,String fileName)  throws Exception{

        // XLST模板转化
        XLSTransformer former = new XLSTransformer();
        former.transformXLS(TEMPLATE_PATH+templateName, params, BASE_PARH+fileName);
        logger.info("excel生成成功!!!");
    }

    /**
     * 合并单元格
     * @param mergeMap 合并的列表
     */
    public static void merge(Map<String,Object> params,Map<String,CellRangeAddress> mergeMap,String templateName,String fileName)  throws Exception{

        try (InputStream is = new FileInputStream(TEMPLATE_PATH+templateName);OutputStream os = new FileOutputStream(BASE_PARH+fileName)){
            // XLST模板转化
            XLSTransformer former = new XLSTransformer();
            XSSFWorkbook workbook = (XSSFWorkbook)former.transformXLS(is, params);
            XSSFSheet sheet = workbook.getSheetAt(0);

            for (CellRangeAddress cell : mergeMap.values()){
                sheet.addMergedRegion(cell);
            }
            workbook.write(os);
        } catch(Exception e){
            logger.error("",e);
        }

        logger.info("excel生成成功!!!");
    }

    public static Map<String,CellRangeAddress> calculateMergeMap(Map<String,Object> params){
        // 第一步：需要对数据进行排序，把需要合并的数据集中起来
        // 第二步：固定列，计算合并的起止行数
        int firstCol = 0; // 开始列
        int lastCol = 0; // 终止列
        int pre = 2; // 起始行
        //参数1：起始行号 参数2：终止行号 参数3：起始列号 参数4：终止列号 下标从0开始
        List<Staff> staffs = (List<Staff>)params.get("staffs");
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

        return mergeMap;
    }
    /**
     * 动态输出字段。（未设置任何样式）
     * @param items 输出字段模板
     * @param fileName 输出文件名
     */
    public static void dynamicXssf(List<Item> items, String fileName) {

        try (OutputStream os = new FileOutputStream(BASE_PARH+fileName)){
            XSSFWorkbook wb = new XSSFWorkbook();
            for(int i = 0; i < items.size(); i++) {
                Item item = items.get(i);
                // 设置Sheet
                XSSFSheet sheet = wb.createSheet(item.getItemTitle());
                // 设置标题栏
                XSSFRow rowTitle = sheet.createRow(0);
                int k = 0;
                for (String value : item.getFieldMap().values()) {
                    rowTitle.createCell(k).setCellValue(value);
                    k++;
                }
                // 设置填充数据
                List<?> datas = item.dataList;
                for (int j = 0; j < datas.size(); j++) {// 行填充
                    XSSFRow rowData = sheet.createRow(j+1);
                    Object data = datas.get(j);
                    Class<?> clzz = data.getClass();
                    int m = 0;
                    for (String fieldName : item.getFieldMap().keySet()) {// 列填充
                        Field field = clzz.getDeclaredField(fieldName);
                        field.setAccessible(true);
                        String value = StringUtil.toStringNotNull(field.get(data));
                        rowData.createCell(m).setCellValue(value);
                        m++;
                    }
                }
            }
            // 文件写入
            wb.write(os);
            logger.info("excel生成成功!!!");
        } catch(Exception e){
            logger.error("",e);
        }

    }

    /**
     * 动态输出字段。（未设置任何样式）
     * @param items 输出字段模板
     * @param fileName 输出文件名
     */
    public static void dynamicSxssf(List<Item> items,String fileName) {

        try (OutputStream os = new FileOutputStream(BASE_PARH+fileName)){
            SXSSFWorkbook sxwb = new SXSSFWorkbook(-1);
            for(int i = 0; i < items.size(); i++) {
                Item item = items.get(i);
                // 设置Sheet
                Sheet sheet = sxwb.createSheet(item.getItemTitle());
                // 设置标题栏
                Row rowTitle = sheet.createRow(0);
                int k = 0;
                for (String value : item.getFieldMap().values()) {
                    rowTitle.createCell(k).setCellValue(value);
                    k++;
                }
                // 设置填充数据
                List<?> datas = item.dataList;
                for (int j = 0; j < datas.size(); j++) {// 行填充
                    Row rowData = sheet.createRow(j+1);
                    Object data = datas.get(j);
                    Class<?> clzz = data.getClass();
                    int m = 0;
                    for (String fieldName : item.getFieldMap().keySet()) {// 列填充
                        Field field = clzz.getDeclaredField(fieldName);
                        field.setAccessible(true);
                        String value = StringUtil.toStringNotNull(field.get(data));
                        rowData.createCell(m).setCellValue(value);
                        m++;
                    }
                }
            }
            // 文件写入
            sxwb.write(os);
            logger.info("excel生成成功!!!");
        } catch(Exception e){
            logger.error("",e);
        }

    }

    @Getter
    @Setter
    @AllArgsConstructor
    private static class Item {
        private String itemName;
        private String itemTitle;
        private Map<String,String> fieldMap;
        private List<?> dataList;
    }
}




