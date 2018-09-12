package util.valite;

/**
 * 长度范围
 * @author yunfan
 */
public class V_range extends Validator{

	@Override
	public String validate(String fieldName, String fieldValue,String fieldDescription, String... args) {

		if(fieldValue.length() < Integer.parseInt(args[0]) || fieldValue.length() > Integer.parseInt(args[1])) {
			return fieldDescription + "长度不符合要求！";
		}
		return null;
	}
}