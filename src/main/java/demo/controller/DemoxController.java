package demo.controller;

import demo.annotation.DemoController;
import demo.annotation.DemoAutowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * spring作用在类上的注解有@Component、@Responsity、@Service以及@Controller；
 * 而@Autowired和@Resource是用来修饰字段、构造函数或者设置方法，并做注入的;
 *
 */

@DemoController
public class DemoxController {


    /**
     * 所有调用接口的统一入口
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "request", method = RequestMethod.GET)
    @ResponseBody
    public Object request() {
        return "";
    }
}
