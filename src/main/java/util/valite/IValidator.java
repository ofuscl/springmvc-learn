/************************* 版权声明 **********************************
 * 版权所有：Copyright (c) MYBABYUP 2015 
 *
 * 工程名称： retail-service
 * 创建者： wandou  
 * 创建日期： 2014年9月29日
 * 创建记录： 创建类结构。
 *
 * ************************* 变更记录 ********************************
 * 修改者： 
 * 修改日期：
 * 修改记录：
 *
 **/
package util.valite;

/**
 * 验证器
 * @author yunfan
 */
public interface IValidator {
    
	/**
	 * 实现验证方法.
	 */
    String validate(String fieldName, String fieldValue, String fieldDescription, String... args) ;
	
	/**
	 * 获取验证器本身的参数列表.
	 * @return
	 */
    String[] getArgs();
	
	/**
	 * 设置验证器本身的参数列表
	 * @param args - 一个或者多个参数, 允许为 null
	 */
    void setArgs(String[] args);
	
	/**
	 * 获取原始表达式.
	 * @return
	 */
    String getExpression();
	
		/**
	 * 设置原始表达式, 用于日期, 正则等验证方式, 避免和 - 号冲突.
	 * @param
	 */
        void setExpression(String expression);
}
