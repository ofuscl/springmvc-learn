package learn.word;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.*;

/**
 * Created by YScredit on 2018/4/28.
 */
public class WordTest {

    private static final String filePath = "F://word/stu.doc";

    private static final String templateFolder = WordTest.class.getResource("/").getPath() + "template/";

    private Configuration configuration = null;

    public WordTest(){
        configuration = new Configuration();
        configuration.setDefaultEncoding("UTF-8");
    }

    public static void main(String[] args) {
        WordTest test = new WordTest();
        test.createWord();
    }

    public void createWord(){
        Map<String,Object> dataMap=new HashMap<String,Object>();
        getData(dataMap);

        Template t=null;
        try {
            configuration.setDirectoryForTemplateLoading(new File(templateFolder));//模板文件所在路径
            t = configuration.getTemplate("javaToWord.ftl"); //获取模板文件
        } catch (IOException e) {
            e.printStackTrace();
        }
        File outFile = new File(filePath); //导出文件
        Writer out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile)));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }

        try {
            t.process(dataMap, out); //将填充数据填入模板文件并输出到目标文件
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getData(Map<String, Object> dataMap) {


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
        dataMap.put("date", "2018-02-13");
        dataMap.put("teacher", "Solin");
        dataMap.put("topic", "基于Java模板技术动态生成Word文档");
        dataMap.put("stuList", stuList);
    }
}
