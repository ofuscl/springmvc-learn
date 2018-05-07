package learn.schema;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
        ApplicationContext context= new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml"});
        User user = (User)context.getBean("user");
        System.out.println(user.getAge()+":"+user.getName());

    }
}
