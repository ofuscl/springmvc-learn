package biz.shiro;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class ManageAccountBo  implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer accType;

    private String account;

    private String password;

    private String salt;

    private String cid;

    private String name;

    private String phone;

    private Integer isValid;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private Integer isDelete;

    /** 角色id */
    private Integer roleId;
    /** 角色名称 */
    private String roleName;

    private String keyWord;

    /** 一级分行 */
    private String oneBranchCode;
    /** 二级分行 */
    private String twoBranchCode;
    /** 一级支行 */
    private String oneSubbranchCode;
    /** 二级支行 */
    private String twoSubbranchCode;

    private Integer allRoleLength;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAccType() {
        return accType;
    }

    public void setAccType(Integer accType) {
        this.accType = accType;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid == null ? null : cid.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getOneBranchCode() {
        return oneBranchCode;
    }

    public void setOneBranchCode(String oneBranchCode) {
        this.oneBranchCode = oneBranchCode;
    }

    public String getTwoBranchCode() {
        return twoBranchCode;
    }

    public void setTwoBranchCode(String twoBranchCode) {
        this.twoBranchCode = twoBranchCode;
    }

    public String getOneSubbranchCode() {
        return oneSubbranchCode;
    }

    public void setOneSubbranchCode(String oneSubbranchCode) {
        this.oneSubbranchCode = oneSubbranchCode;
    }

    public String getTwoSubbranchCode() {
        return twoSubbranchCode;
    }

    public void setTwoSubbranchCode(String twoSubbranchCode) {
        this.twoSubbranchCode = twoSubbranchCode;
    }

    public Integer getAllRoleLength() {
        return allRoleLength;
    }

    public void setAllRoleLength(Integer allRoleLength) {
        this.allRoleLength = allRoleLength;
    }
}