package learn.schema;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.context.support.XmlWebApplicationContext;
import sun.misc.Resource;

public class XmlReader {

    // <1>、利用ClassPathXmlApplicationContext,可以从classpath中读取XML文件
    //读取一个文件
    ApplicationContext context= new ClassPathXmlApplicationContext("applicationContext.xml");
    User user= (User)context.getBean("user");
    //读取多个文件
    ClassPathXmlApplicationContext resource= new ClassPathXmlApplicationContext("applicationContextconfig.xml","applicationContext.xml","applicationContext-data.xml");
    BeanFactory factory= resource;
//    User user= (User)factory.getBean("user");

//    //<2>、利用ClassPathResource,可以从classpath中读取XML文件
//    Resource resource= new ClassPathResource("applicationContext.xml");
//    BeanFactory bf=new XmlBeanFactory(resource);
//    UserDAO userDAO= (UserDAO )bf.getBean("userDAO ");
//
//    //<3>、利用XmlWebApplicationContext读取
//    XmlWebApplicationContext xmlctx = new XmlWebApplicationContext();
//    xmlctx.setConfigLocations(new String[] {"/WEB-INF/ applicationContext.xml");
//        xmlctx.setServletContext(pageContext.getServletContext());
//        xmlctx.refresh();
//        UserDAO userDAO= (UserDAO ) xmlctx.getBean("userDAO ");
//
//<4>、利用FileSystemResource读取
//        Resource resource= new FileSystemResource("E:/Java/spring/WebRoot/WEB-INF/classes/ applicationContext.xml");
//        BeanFactory factory= new XmlBeanFactory(resource );
//        UserDAO userDAO= (UserDAO )factory.getBean("userDAO ");
//        注意:利用FileSystemResource，则配置文件必须放在project直接目录下，或者写明绝对路径，否则就会抛出找不到文件的异常
//
//                <5>、利用FileSystemXmlApplicationContext读取,可以指定XML定义文件的相对路径或者绝对路径来读取定义文件。
//        1.String[] path={"WebRoot/WEB-INF/applicationContext.xml","WebRoot/WEB-INF/applicationContext_config.xml"};
//        ApplicationContext context = new FileSystemXmlApplicationContext(path);
//        2. String path="WebRoot/WEB-INF/applicationContext*.xml";
//        ApplicationContext context = new FileSystemXmlApplicationContext(path);
//        3.ApplicationContext ctx = new FileSystemXmlApplicationContext("classpath:地址");
    }
