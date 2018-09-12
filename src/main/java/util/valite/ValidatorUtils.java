package util.valite;

import demo.util.comm.StringUtil;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 验证器
 * @author yunfan
 */
public class ValidatorUtils {

    public static void main(String[] args) throws Exception{

        TestVo vo = new TestVo();
        vo.setAge(100);
        vo.setPhone("123456");
//        vo.setName("张三");
        System.out.println(validate(vo));
    }

    /**
     * validate(数据验证)
     *
     * @param args 验证数据对象
     * @return boolean 数据验证结果
     * @throws Exception 异常
     */
    public static String validate(Object args) throws Exception {

        String msg = null;

        if(args == null){
            return null;
        }

        ItemValidator itemValidator = args.getClass().getAnnotation(ItemValidator.class);
        if(!itemValidator.required()){
            return null;
        }

        Field[] fields = args.getClass().getDeclaredFields();

        for (Field field :fields) {
            ParamValidator pvalidator = field.getAnnotation(ParamValidator.class);
            if(pvalidator == null){
                continue;
            }

            field.setAccessible(true);// 获取私有变量
            String fieldName = field.getName();
            String fieldValue = StringUtil.toStringNotNull(field.get(args));

            // 验证条件
            String[] validations = pvalidator.value().split(";");
            for (String pvalue : validations) {
                String[] pt = pvalue.split(",");
                if(pt.length != 3){
                    throw new ValidatorException("验证条件[ "+pvalue+" ]不符合要求");
                }
                List<Validator> validators = FieldParser.getValidations(fieldName,fieldValue,pt[1],pt[2]);
                for (Validator validator : validators) {
                    msg = validator.validate(validator.getFieldName(), validator.getFieldValue(),validator.getFieldDescription(), validator.getArgs());
                    if (msg != null) {
                        return msg;
                    }
                }
            }
            if(msg != null){
                break;
            }
        }

        return msg;
    }
}
