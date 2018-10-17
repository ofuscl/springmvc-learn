package biz.file.excel;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import util.JsonUtil;
import util.StringUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * 报表导入工具类
 *
 * @author yunfan
 */
public class ExcelImportUtil {

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(ExcelImportUtil.class);

    public static final String R = "row";
    public static final String C = "column";

    /**
     * @param args
     */
    public static void main(String[] args) {

        try {
            List<ExcelSheet> sheetList = new ArrayList<>();
            //ExcelImportUtil.ExcelSheet sheet1 = new ExcelImportUtil.ExcelSheet("Sheet1","Sheet1",2,1,R,new Staff().getClass(),Staff.ITEM_MAP);
            Map<String,Integer> indexMap = new HashMap<>();
            indexMap.put("name",0);
            indexMap.put("age",1);
            indexMap.put("sex",2);
            indexMap.put("love",3);
            ExcelImportUtil.ExcelSheet sheet1 = new ExcelImportUtil.ExcelSheet("Sheet1","Sheet1",0,0,C,null,indexMap);
            sheetList.add(sheet1);
//            sheetList.add(sheet2);
            File file = new File("F:\\test\\dest.xlsx");
            Map<String,Object> sheetMap =  readFile(file,sheetList);
            for (Map.Entry<String,Object> sheetEntry : sheetMap.entrySet()){
                if (sheetEntry.getValue() == null){
                    logger.info("读取数据为空");
                    return;
                }

                System.out.println(sheetEntry.getKey() + "  读取内容直接输出 -----------------");
                String[][] fieldArray2 = (String[][])sheetEntry.getValue();
                for (int r = 0; r < fieldArray2.length; r++){
                    System.out.println(JsonUtil.toJsonFromObject(fieldArray2[r]));
                }

                System.out.println(sheetEntry.getKey() + "  行列转换后输出---------------------");

                String[][] fieldArray = (String[][])sheetEntry.getValue();
                String[][] xx = new String[fieldArray[0].length][fieldArray.length];
                for (int r = 0; r < fieldArray.length; r++){
                    String[] carray = fieldArray[r];
                    for (int c = 0; c < carray.length; c++){
                        xx[c][r]=fieldArray[r][c];
                    }
                }

                for(int r = 0; r < xx.length; r++){
                    System.out.println(JsonUtil.toJsonFromObject(xx[r]));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 附件上传
     * @param file 上传文件
     */
    public static Map<String,Object> readFile(MultipartFile file,List<ExcelSheet> sheetList) throws Exception {

        if(file == null){
            System.out.println("找不到指定的文件");
            return null;
        }
        try (InputStream fis = file.getInputStream()){
            return readExcel(fis,file.getName(),sheetList);
        } catch(Exception e){
            logger.error("文件生成异常：",e);
        }
        return null;
    }

    /**
     * 文件读取
     * @param file 上传文件
     */
    public static Map<String,Object> readFile(File file,List<ExcelSheet> sheetList) {

        try (FileInputStream fis = new FileInputStream(file)){
            if (!file.isFile() || !file.exists()) {   //判断文件是否存在
                System.out.println("找不到指定的文件");
                return null;
            }
            return readExcel(fis,file.getName(),sheetList);
        } catch(Exception e){
            logger.error("文件生成异常：",e);
        }
        return null;
    }

    /**
     * 读取Excel具体字段。
     * @param fis 上传文件流
     * @param fileName 文件名
     * @param sheetList sheet列
     */
    public static Map<String,Object> readExcel(InputStream fis, String fileName, List<ExcelSheet> sheetList) throws Exception {

        String[] split = fileName.split("\\.");  //.是特殊字符，需要转义！！！！！
        if(split.length != 2 || !"xls".equals(split[1]) && !"xlsx".equals(split[1])){
            System.out.println("文件类型错误!");
            return null;
        }

        Workbook wb = "xlsx".equals(split[1])?new XSSFWorkbook(fis):new HSSFWorkbook(fis);

        //开始解析
        Map<String,Object> sheetMap = new HashMap<>();
        for (ExcelSheet excelSheet : sheetList){
            Sheet sheet = wb.getSheet(excelSheet.getSheetName()); //根据sheet名读取
            if(sheet == null){
                System.out.println("sheet："+excelSheet.getSheetName()+" 不存在");
                continue;
            }
            Object list = null;
            if (excelSheet.getClazz() !=null){ // 按照行读
                list = readByRow(sheet,excelSheet);
            } else {
                list = readByColumns(sheet,excelSheet);// 按照列读
            }

            sheetMap.put(excelSheet.getSheetCode(),list);
        }
        //第一行是列名，所以不读
        return sheetMap;
    }

    /**
     * 按照列读
     * @param sheet
     * @param excelSheet
     * @return
     * @throws Exception
     */
    private static String[][] readByColumns(Sheet sheet,ExcelSheet<T> excelSheet) throws Exception {

        String[][] itemArray = new String[0][0];
        if (R.equals(excelSheet.getType())){
            itemArray = new String[sheet.getLastRowNum()+1][excelSheet.indexMap.size()];
        } else if (C.equals(excelSheet.getType())){
            itemArray = new String[excelSheet.indexMap.size()][sheet.getLastRowNum()];
        }
        int firstRow = sheet.getFirstRowNum()+excelSheet.getFirstRow();
        int firstColumn = excelSheet.getFirstColumn();
        for (int r = 0; r < sheet.getLastRowNum()-firstRow+1; r++) {   //遍历行
            Row row = sheet.getRow(r+firstRow);
            if (row == null) {
                continue;
            }

            for (int c = 0; c < row.getLastCellNum() - firstColumn; c++) {   //遍历行
                itemArray[r][c] = StringUtil.toStringNotNull(row.getCell(c+firstColumn));
            }
        }

        return itemArray;
    }

    /**
     * 按照行读
     * @param sheet
     * @param excelSheet
     * @param <T>
     * @return
     * @throws Exception
     */
    private static <T> List<T> readByRow(Sheet sheet,ExcelSheet<T> excelSheet) throws Exception {
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
                field.set(cls, StringUtil.toStringNotNull(row.getCell(cindex)));
            }

            if(cls != null){
                itemList.add(cls);
            }
        }

        return itemList;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class ExcelSheet<T> {
        private String sheetName;
        private String sheetCode;
        private int firstRow;
        private int firstColumn;
        /** 类型：按列or按行 */
        private String type;
        private Class<T> clazz;
        private Map<String,Integer> indexMap;
    }
}





