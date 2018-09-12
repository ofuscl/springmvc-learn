/**  
*****************************************************************************
* Copyright(C)  MORETRUST Co., Ltd. 2012                                        
*****************************************************************************
* 产品名称：MT弱联盟商业平台  
* 版本信息：1.0  
* 日期：2012-5-30  
*/
package util.valite;

/**
 * 正则验证器
 * @author yunfan
 */
public abstract class RegexValidator extends Validator {

	@Override
	public String validate(String fieldName, String fieldValue,String fieldDescription, String... args) {
		
		// 非空时方进行验证
		if(fieldValue != null && fieldValue.length() > 0) {
			if(!fieldValue.matches(getRegEx())) {
				return fieldDescription + getMessage();
			}
		}
		return null;
	}
	
	/**
	 * 返回验证消息.
	 * 子类需实现.
	 * @return 验证结果消息, 为空时表示没有错误
	 */
	public abstract String getMessage();
	
	/**
	 * 获取验证规则.
	 * 子类需实现.
	 * @return 正则表达式
	 */
	public abstract String getRegEx();

}