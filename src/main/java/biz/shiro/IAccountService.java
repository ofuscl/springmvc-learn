package biz.shiro;


public interface IAccountService {

    int updateAccount(ManageAccountBo o);

    /**
     * 根据登录帐号查找loginName和accountType，正常只有一条数据
     * and a.isvalid='1' and a.account_type='1'需要该条件
     *
     * @param loginName 用户名
     * @return ManageAccountBo
     */
    ManageAccountBo findFormatByLoginName(String loginName);

    /**
     * 获取个人资料，需要登录状态
     *
     * @return ManageAccountBo
     */
    ManageAccountBo getPerData();

    /**
     * 设置个人资料
     *
     * @param account   用户信息
     * @return int
     */
    int setPerData(ManageAccountBo account);

    /**
     * 新增用户(后台)
     *
     * @param account   用户信息
     * @return int
     */
    int insertAccount(ManageAccountBo account) throws Exception;

    /**
     * 系统密码重置
     *
     * @param o 用户信息
     * @return int
     */
    int sysResetPwd(ManageAccountBo o);
}
