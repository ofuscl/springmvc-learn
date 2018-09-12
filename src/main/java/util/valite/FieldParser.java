/**  
*****************************************************************************
* Copyright(C)  MORETRUST Co., Ltd. 2012                                        
*****************************************************************************
* 产品名称：MT弱联盟商业平台  
* 版本信息：1.0  
* 日期：2012-5-31  
*/
package util.valite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 字段解析器
 * @author yunfan
 */
public class FieldParser {

	static final String VALIDATOR_PACKAGE = "util.valite.";// 验证规则类所在包
	static Map<String, Validator> valudators = new HashMap<>(); // 添加缓存

	/**
	 * 分析验证器
	 * 
	 * @return
	 */
	public static List<Validator> getValidations(String fieldName, String fieldValue,String fieldDescription,String validExpression) throws Exception{
		List<Validator> lists = new ArrayList<>();

		String[] parts = validExpression.split("&");
		for (String part : parts) {
			String argsExpression = null;// 规则表达式
			String className = null;// 规则类名
			String[] args = new String[10];// 参数列表(可能为空)

			if(part.indexOf("-") == -1){
				className = part;
			} else if(part.indexOf("-") > 0){
				String[] xx =part.split("-");
				className = xx[0];
				for (int i =0; i< xx.length;i++){
					if(xx[i].equals(className)){
						continue;
					}
					args[i-1] = xx[i];
				}
			}
			// 类名有效时, 尝试加载
			if (className == null && className.length() == 0) {
				continue;
			}

			String classPath = VALIDATOR_PACKAGE + className;
			try {
				Validator validator = valudators.get(classPath) == null ? (Validator) Class.forName(classPath).newInstance() : valudators.get(classPath);
				validator.setArgs(args);
				validator.setFieldName(fieldName);
				validator.setFieldValue(fieldValue);
				validator.setFieldDescription(fieldDescription);
				validator.setExpression(argsExpression);// 原始表达式
				lists.add(validator);
			} catch (Exception e) {
				throw new ValidatorException("验证器实现类 "+classPath+" 不存在!");
			}
		}

		return lists;
	}

}