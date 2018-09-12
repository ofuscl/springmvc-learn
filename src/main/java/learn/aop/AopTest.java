package learn.aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AopTest {

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml"});

        UserService userService = (UserService) ctx.getBean("userService");
        userService.add();
    }
}
