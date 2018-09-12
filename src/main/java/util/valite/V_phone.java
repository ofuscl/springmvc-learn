package util.valite;

/**
 * 固定电话
 * @author yunfan
 */
public class V_phone extends RegexValidator {

	@Override
	public String getMessage() {
		return "只能输入正确的固定电话号码,如:0920-29392929";
	}

	@Override
	public String getRegEx() {
		return "^(([0-9]{3})|([0-9]{4}))[-]\\d{6,8}$";
	}
}