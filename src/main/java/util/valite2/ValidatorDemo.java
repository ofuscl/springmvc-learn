package util.valite2;

import demo.util.comm.StringUtil;
import org.apache.commons.lang.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 验证器
 * @author yunfan
 */
public class ValidatorDemo {

    public static void main(String[] args) throws Exception{

        TestVo vo = new TestVo();
        vo.setAge(17);
        vo.setPhone("123456");
        vo.setName("张三");
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

        if(args == null){
            return null;
        }

        ItemValidator itemValidator = args.getClass().getAnnotation(ItemValidator.class);
        if(!itemValidator.required()){
            return null;
        }

        Field[] fields = args.getClass().getDeclaredFields();

        String msg = null;
        for (Field field :fields) {

            Annotation[] annotations = field.getAnnotations();
            if (annotations == null || annotations.length ==0){
                continue;
            }

            field.setAccessible(true);// 获取私有变量
            String fieldName = field.getName();
            String fieldValue = StringUtil.toStringNotNull(field.get(args));

            for (Annotation an : annotations){

                String className = an.annotationType().getSimpleName();
                if (className == null){
                    continue;
                }

                String methodName = "validate_"+ StringUtils.lowerCase(className);
                Method target = ValidatorDemo.class.getMethod(methodName,Field.class,String.class);

                msg = (String)target.invoke(ValidatorDemo.class,field,fieldValue);
                if (msg != null){
                    return msg;
                }
            }
        }

        return msg;
    }


    /**
     * 验证数字范围
     * @param field
     * @param fieldValue
     * @return
     */
    public static String validate_range(Field field,String fieldValue){
        Range rang = field.getAnnotation(Range.class);
        if (Integer.parseInt(fieldValue) < rang.min() || Integer.parseInt(fieldValue) > rang.max()){
            return rang.message();
        }

        return null;
    }

    /**
     * 验证手机号
     * @param field
     * @param fieldValue
     * @return
     */
    public static String validate_phone(Field field,String fieldValue){
        Phone phone = field.getAnnotation(Phone.class);
        if (!fieldValue.matches("^(([0-9]{3})|([0-9]{4}))[-]\\d{6,8}$")){
            return phone.message();
        }

        return null;
    }
}
