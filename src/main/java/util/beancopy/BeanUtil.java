package util.beancopy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.DateUtil;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

public class BeanUtil {
    
    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(BeanUtil.class);
    static final Map<String,Object> defaultMap = new HashMap<>();
    static {
        defaultMap.put("int",0);
        defaultMap.put("Integer",0);
        defaultMap.put("String","");
        defaultMap.put("Date",new Date());
        defaultMap.put("BigDecimal",BigDecimal.ZERO);
        defaultMap.put("byte",0);
        defaultMap.put("long",0L);
        defaultMap.put("Long",0L);
        defaultMap.put("Map",new HashMap<>());
        defaultMap.put("List",new ArrayList());
    }
    public static void main(String[] args) throws Exception{

        UserApiBo bo = new UserApiBo();
        bo.setId(10);
        bo.setApi(1001);
        bo.setUid("test");
        bo.setApplyTime(new Date());
        bo.setDiscount(new BigDecimal(100.24324));
        Map<String,Object> map = new HashMap<>();
        map.put("xx","11");
        map.put("xx1","11");
        map.put("xx2","11");
        map.put("xx3","11");
        map.put("xx4","11");
        map.put("xx5","11");
        bo.setBasicMap(map);
        List<User> userList = new ArrayList<>();
        User u = new User();
        u.setAge(10);
        u.setName("张三");
        userList.add(u);
        bo.setUserList(userList);
        UserApiVo vo = new UserApiVo();

        Long s = System.currentTimeMillis();
        copy(vo,bo);
        System.out.println(System.currentTimeMillis()-s + " | " + vo.toString());

    }

    public static void copy (Object vo,Object bo) {

        try {
            Field[] fields = vo.getClass().getDeclaredFields();
            Class bzz = bo.getClass();
            for (Field field : fields){
                if ("serialVersionUID".equals(field.getName())){
                    continue;
                }
                Field bfield = bzz.getDeclaredField(field.getName());
                field.setAccessible(true);
                bfield.setAccessible(true);

                String voTypeName = field.getType().getSimpleName();
                if (bfield == null){
                    field.set(vo,defaultMap.get(voTypeName));
                    continue;
                }
                if(!voTypeName.equals(bfield.getType().getSimpleName())){
                    field.set(vo,defaultMap.get(voTypeName));
                    continue;
                }

                Object o = bfield.get(bo);
                if (o == null){
                    field.set(vo,defaultMap.get(voTypeName));
                } else { // 日期默认类型设置 YYYY-MM-DD
                    if ("String".equals(voTypeName) && "Date".equals(bfield.getType().getSimpleName())){
                        String format = DateUtil.YYYY_MM_DD;
                        if(field.isAnnotationPresent(DateFormat.class)){
                            format = field.getAnnotation(DateFormat.class).value();
                        }
                        field.set(vo,DateUtil.toStrFromObject(o,format));// 默认
                    } else if("BigDecimal".equals(bfield.getType().getSimpleName())){// 浮点自动设置两位小数
                        int scale = 2;
                        if(field.isAnnotationPresent(BigDecimalFormat.class)){
                            scale = field.getAnnotation(BigDecimalFormat.class).scale();
                        }
                        field.set(vo,new BigDecimal(o.toString()).setScale(scale,BigDecimal.ROUND_UP));
                    }
                }
            }
        } catch(Exception e){
            logger.error("",e);
        }
    }
}
