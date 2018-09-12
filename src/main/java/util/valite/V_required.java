package util.valite;

/**
 * 是否必填
 * @author yunfan
 */
public class V_required extends Validator{

	@Override
	public String validate(String fieldName, String fieldValue,String fieldDescription, String... args) {

		if(fieldValue == null || fieldValue.length() == 0 || fieldValue.trim().length() == 0) {
			return fieldDescription + "不能为空！";
		}
		return null;
	}
}