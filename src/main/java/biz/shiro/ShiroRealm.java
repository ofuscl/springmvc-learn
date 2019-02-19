package biz.shiro;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 *  参照ccb-cloud的做法
 */
public class ShiroRealm extends AuthorizingRealm {

    Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 账户类服务层注入
     */
    @Autowired
    private IAccountService accountService;

    /*
     * 认证 ---登录信息和用户验证信息验证(non-Javadoc)
     * 备注 String userName = (String)authcToken.getPrincipal(); //用户名
     * 备注 String password = new String((char[])token.getCredentials()); //密码
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        String username = token.getUsername();//用户名
        // 先验证用户是否存在有效
        ManageAccountBo a = accountService.findFormatByLoginName(username);
        if (a ==null || 1 != a.getIsValid()){ //用户不存在，或者无效用户
            throw new UnknownAccountException();
        }
        // session缓存部分数据
        this.setSession(Constants.SESSION_USER, a);
        // 返回认证信息 --主：内容来自通过用户名查到的
        return new SimpleAuthenticationInfo(a.getAccount() , a.getPassword(), ByteSource.Util.bytes(a.getAccount()+a.getSalt()), getName());

    }

    /*
     * 授权 ---授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用,负责在应用程序中决定用户的访问控制的方法(non-Javadoc)
     * @see org.apache.shiro.realm.AuthorizingRealm#doGetAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {
        // 因为非正常退出，即没有显式调用 SecurityUtils.getSubject().logout()
        // (可能是关闭浏览器，或超时)，但此时缓存依旧存在(principals)，所以会自己跑到授权方法里。
        if (!SecurityUtils.getSubject().isAuthenticated()) {
            doClearCache(pc);
            SecurityUtils.getSubject().logout();
            return null;
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        return info;
    }

    /**
     * 将一些数据放到ShiroSession中,以便于其它地方使用
     *
     * @see
     */
    private void setSession(Object key, Object value) {
        Subject currentUser = SecurityUtils.getSubject();
        if (null != currentUser) {
            Session session = currentUser.getSession();
            if (null != session) {
                session.setAttribute(key, value);
            }
        }
    }

}
