package biz.shiro;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * 封装shiro用对象获取
 */
public class AccountShiroUtil {

    /**
     * 获取当前对象的拷贝
     *
     * @return ManageAccountBo
     */
    public static ManageAccountBo getCurrentUser() {
        ManageAccountBo customer = null;
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        if (null != session) {
            Object obj = session.getAttribute(Constants.SESSION_USER);
            if (null != obj && obj instanceof ManageAccountBo) {
                try {
                    // 复制一份对象，防止被错误操作
                    customer = (ManageAccountBo) BeanUtils.cloneBean(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return customer;
    }

    /**
     * 获取当前真实的对象，可以进行操作实体
     *
     * @return ManageAccountBo
     */
    public static ManageAccountBo getRealCurrentUser() {
        ManageAccountBo customer = null;
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        if (null != session) {
            Object obj = session.getAttribute(Constants.SESSION_USER);
            if (null != obj && obj instanceof ManageAccountBo) {
                try {
                    // 不复制一份对象，防止被错误操作
                    customer = (ManageAccountBo) obj;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return customer;
    }
}
