package util.valite;

/**
 * 验证器异常类
 * @author yunfan
 */
public class ValidatorException extends Exception{

    public ValidatorException(String msg) {
        super("验证器异常：" + msg);
    }
}
