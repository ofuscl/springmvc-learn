package learn.schema;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class SchemaTest {

    public static void main(String[] args) {

        // BeanDefinition :bean定义
        // BeanDefinitionParser : bean解析器

        // 1、定义 gupao.xsd  --定义元素；定义targetNameSpace=//http://gupoao.com/schema/gupao
        // 2、配置Schema META-INF/spring-schemas : schema的绝对路径变schema相对路径
        // 3、添加gupao.xsd到applicationContext.xml里面
        // 4、建立schema与Handler的映射
        // 5、实现NameSpaceHandlerSupplor
        // PropertyEditor是基础

        // SAX:时间处理性能良好   --DOM ：属性结构，性能最差 XmlStream  Jaxb :
//        PropertyEditor;
//        ConversionService
        ApplicationContext classPathXml= new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml"});
        User userClass = (User)classPathXml.getBean("userr");
        System.out.println("class : " + userClass.getAge()+":"+userClass.getName());

//        ApplicationContext fileSystemXml = new FileSystemXmlApplicationContext(new String[]{"applicationContext.xml"});
//        User userFile = (User)fileSystemXml.getBean("user");
//        System.out.println("file : " + userFile.getAge()+":"+userFile.getName());

//        ApplicationContext xmlWeb = new XmlWebApplicationContext(new String[]{"applicationContext.xml"});
//        User userXmlWeb = (User)xmlWeb.getBean("user");
//        System.out.println("xml : " + userXmlWeb.getAge()+":"+userXmlWeb.getName());
    }
}
