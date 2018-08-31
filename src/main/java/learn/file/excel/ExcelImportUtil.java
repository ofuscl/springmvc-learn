package learn.file.excel;


import demo.util.comm.JsonUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 报表导出工具类
 *
 * @author yunfan
 */
public class ExcelImportUtil {

    static Logger logger = LoggerFactory.getLogger(ExcelImportUtil.class);

    /**
     * @param args
     */
    public static void main(String[] args) {

        try {
            List<ExcelSheet> sheetList = new ArrayList<>();
            ExcelSheet sheet1 = new ExcelSheet("Sheet1","Sheet1",2,new Staff().getClass(),Staff.ITEM_MAP);
            ExcelSheet sheet2 = new ExcelSheet("Sheet2","Sheet2",1,new Staff().getClass(),Staff2.ITEM_MAP);
            sheetList.add(sheet1);
            sheetList.add(sheet2);
            File file = new File("F:\\test\\dest.xlsx");
            Map<String,List<Staff>> sheetMap =  readFile(file,sheetList);

            for (Map.Entry<String,List<Staff>> sheetEntry : sheetMap.entrySet()){

                System.out.println(sheetEntry.getKey() + "  start -----------------");
                if (sheetEntry.getValue() == null){
                    logger.info("读取数据为空");
                    return;
                }
                sheetEntry.getValue().forEach(s->{
                    System.out.println(JsonUtil.toJsonFromObject(s));
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 附件上传
     * @param file 上传文件
     */
    public static <T> Map<String,List<T>> readFile(MultipartFile file,List<ExcelSheet> sheetList) throws Exception {

        if(file == null){
            System.out.println("找不到指定的文件");
            return null;
        }
        return readExcel(file.getInputStream(),file.getName(),sheetList);
    }

    /**
     * 文件读取
     * @param file 上传文件
     */
    public static <T> Map<String,List<T>> readFile(File file,List<ExcelSheet> sheetList) throws Exception {

        if (!file.isFile() || !file.exists()) {   //判断文件是否存在
            System.out.println("找不到指定的文件");
            return null;
        }

        FileInputStream fis = new FileInputStream(file);   //文件流对象

        return readExcel(fis,file.getName(),sheetList);
    }

    /**
     * 读取Excel具体字段。
     * @param fis 上传文件流
     * @param fileName 文件名
     * @param sheetList sheet列
     */
    public static <T> Map<String,List<T>> readExcel(InputStream fis, String fileName, List<ExcelSheet> sheetList) throws Exception {

        String[] split = fileName.split("\\.");  //.是特殊字符，需要转义！！！！！
        if(split.length != 2 || !"xls".equals(split[1]) && !"xlsx".equals(split[1])){
            System.out.println("文件类型错误!");
            return null;
        }

        Workbook wb = "xlsx".equals(split[1])?new XSSFWorkbook(fis):new HSSFWorkbook(fis);

        //开始解析
        Map<String,List<T>> sheetMap = new HashMap<>();
        for (ExcelSheet excelSheet : sheetList){
            Sheet sheet = wb.getSheet(excelSheet.getSheetName()); //根据sheet名读取
            if(sheet == null){
                System.out.println("sheet："+excelSheet.getSheetName()+" 不存在");
                continue;
            }
            List<T> list = readRowandCell(sheet,excelSheet);
            sheetMap.put(excelSheet.getSheetCode(),list);
        }
        //第一行是列名，所以不读
        return sheetMap;
    }

    private static <T> List<T> readRowandCell(Sheet sheet,ExcelSheet<T> excelSheet) throws Exception{
        List<T> itemList = new ArrayList<>();
        int firstRow = sheet.getFirstRowNum()+excelSheet.getFirstRow();
        for (int r = firstRow; r <= sheet.getLastRowNum(); r++) {   //遍历行
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }
            T cls = excelSheet.getClazz().newInstance();
            Field[] fields = cls.getClass().getDeclaredFields();
            for (Field field : fields) {
                int mod = field.getModifiers();
                if(Modifier.isStatic(mod) || Modifier.isFinal(mod)){
                    continue;
                }

                field.setAccessible(true);
                int cindex = excelSheet.getIndexMap().get(field.getName());
                field.set(cls, row.getCell(cindex) == null ? "":row.getCell(cindex).toString());
            }

            if(cls != null){
                itemList.add(cls);
            }
        }

        return itemList;
    }
}





