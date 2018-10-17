package biz.file.word;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.*;
import java.util.*;

/**
 * 生成word
 * 说明：关键点是需要修改word另存为XML文件(*.xml)，然后修改.xml为.ftl即可
 * Created by yunfan on 2018/4/28.
 */
public class WordTest {

    private static final String filePath = "F://word/test.doc";
    private static final String templateFolder = WordTest.class.getResource("/").getPath() + "template/";

    private Configuration configuration = null;

    public WordTest(){
        configuration = new Configuration();
        configuration.setDefaultEncoding("UTF-8");
    }

    public static void main(String[] args) {
        WordTest test = new WordTest();
        test.createWord(test.getData());
    }

    public void createWord(Map<String,Object> dataMap){
        try (Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(filePath))))){
            configuration.setDirectoryForTemplateLoading(new File(templateFolder));//模板文件所在路径
            Template t = configuration.getTemplate("javaToWord.ftl"); //获取模板文件
            t.process(dataMap, out); //将填充数据填入模板文件并输出到目标文件
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    private Map<String,Object> getData() {

        Map<String,Object> dataMap=new HashMap<String,Object>();
        List<Student> stuList = new ArrayList<>();
        Student stu1 = new Student(1,"张已","男","15");
        Student stu2 = new Student(2,"张尔","女","16");
        Student stu3 = new Student(3,"张三","男","17");
        Student stu4 = new Student(4,"张斯","女","18");
        Student stu5 = new Student(5,"张武","男","19");
        stuList.add(stu1);
        stuList.add(stu2);
        stuList.add(stu3);
        stuList.add(stu4);
        stuList.add(stu5);
//        dataMap.put("xxxx", "2018-02-13");
        dataMap.put("xxxx", "Solin");
        dataMap.put("topic", "基于Java模板技术动态生成Word文档");
        dataMap.put("stuList", stuList);
        dataMap.put("applyNo", "201213141242353");

        return dataMap;
    }
}
