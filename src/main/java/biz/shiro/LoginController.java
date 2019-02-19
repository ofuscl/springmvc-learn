package biz.shiro;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


@Controller
public class LoginController {

    /**
     * 访问登录页
     *
     * @return
     */
    @RequestMapping(value = "/loginIndex")
    public ModelAndView toLogin() throws Exception {
        ModelAndView mv = new ModelAndView();
        Subject currentUser = SecurityUtils.getSubject();
        ManageAccountBo currentAccount = AccountShiroUtil.getCurrentUser();
        if (currentUser.isAuthenticated()) {
            mv.addObject("currentAccount", currentAccount);
            mv.setViewName("system/index");// 跳转jsp的地址
        } else {
            mv.setViewName("system/login/login");// 跳转jsp的地址
        }

        return mv;
    }

    /**
     * 请求登录，验证用户
     */
    @RequestMapping(value = "/system_login", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> login() throws Exception {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 前端对密码做了加密操作，同时将用户名和密码与特殊字符串拼接传输
        // 前端代码示例：var code = loginname+",SYS,"+$.md5(password)+",SYS,"+verifyCode;  ---厉害了
        String[] userData = request.getParameter("KEYDATA").split(",SYS,");
        String username = userData[0];
        String password = userData[1];

        String errInfo = "";
        //shiro管理的session
        Subject currentUser = SecurityUtils.getSubject();

        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            map.put("result", "nullup");//缺少用户名或密码
            return map;
        }
        // shiro加入身份验证
        UsernamePasswordToken token = new UsernamePasswordToken(username, password.toUpperCase());
        token.setRememberMe(true);
        try {
            if (!currentUser.isAuthenticated()) {
                currentUser.login(token);
            }
            //记录登录日志 TODO
        } catch (UnknownAccountException uae) {
            uae.printStackTrace();
            errInfo = "usererror";// 用户名或密码有误
        } catch (IncorrectCredentialsException ice) {
            ice.printStackTrace();
            errInfo = "usererror"; // 密码错误
        } catch (LockedAccountException lae) {
            lae.printStackTrace();
            errInfo = "inactive";// 未激活
        } catch (ExcessiveAttemptsException eae) {
            eae.printStackTrace();
            errInfo = "attemptserror";// 错误次数过多
        } catch (AuthenticationException ae) {
            ae.printStackTrace();
            errInfo = "codeerror";// 验证未通过
        }
        // 验证是否登录成功
        if (!currentUser.isAuthenticated()) {
            token.clear();
        }
        if (StringUtils.isBlank(errInfo)) {
            errInfo = "success"; // 验证成功
            currentUser.getSession().removeAttribute(Constants.SESSION_SECURITY_CODE);//移除SESSION验证码的验证
        }
        map.put("result", errInfo);
        return map;
    }

    /**
     * 帐号注销
     *
     * @return
     */
    @RequestMapping("/system_logout")
    public String logout(HttpServletRequest request) {
        Subject currentUser = SecurityUtils.getSubject();
        HttpSession session = request.getSession();
        session.removeAttribute(Constants.SESSION_USER);
        currentUser.logout();
        return "redirect:loginIndex";
    }


}
