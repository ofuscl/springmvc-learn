package learn.schema;

import org.apache.commons.beanutils.Converter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.beans.PropertyEditor;

public class SchemaTest {

    public static void main(String[] args) {

        // BeanDefinition :bean定义
        // BeanDefinitionParser : bean解析器

        // 1、定义 gupao.xsd  --定义元素；在xsd中定义targetNameSpace=//http://gupoao.com/schema/gupaoo
        // 2、配置Schema META-INF/spring-schemas : schema的绝对路径和schema相对路径的映射
        //    http\://www.gupao.com/schema/gupao.xsd=xsd/gupao.xsd
        // 3、添加gupao.xsd到applicationContext.xml里面
        //    创建缩写gupao标签 -- xmlns:gupao="http://www.gupao.com/schema/gupaoo"
        //    设置schema的位置  -- xsi:schemaLocation="http://www.gupao.com/schema/gupaoo http://www.gupao.com/schema/gupao.xsd"
        // 4、配置Schema META-INF/spring-handlers : 建立schema与Handler的映射
        //    http\://www.gupao.com/schema/gupaoo=learn.schema.GupaoNamespaceHandler
        // 5、实现NameSpaceHandlerSupplor  --------GupaoNamespaceHandler
        // 6、实现BeanDefinitionParser     --------UserBeanDefinitionParser
        // PropertyEditor是基础

        // SAX:时间处理性能良好   --DOM ：属性结构，性能最差 XmlStream  Jaxb :

        ClassPathXmlApplicationContext classPathXml= new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml"});
        User userClass = (User)classPathXml.getBean("user");
        System.out.println("class : " + userClass.getAge()+":"+userClass.getName()+":"+userClass.getBname());
        classPathXml.close();
//        ApplicationContext fileSystemXml = new FileSystemXmlApplicationContext(new String[]{"applicationContext.xml"});
//        User userFile = (User)fileSystemXml.getBean("user");
//        System.out.println("file : " + userFile.getAge()+":"+userFile.getName());

//        ApplicationContext xmlWeb = new XmlWebApplicationContext(new String[]{"applicationContext.xml"});
//        User userXmlWeb = (User)xmlWeb.getBean("user");
//        System.out.println("xml : " + userXmlWeb.getAge()+":"+userXmlWeb.getName());
    }
}
