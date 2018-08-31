package learn.reflect;

import org.apache.poi.ss.formula.functions.T;

import java.lang.reflect.Field;

/**
 * Created by yunfan on 2018/5/14.
 */
public class Test {

    public static void main(String[] args) throws Exception{


        TacticsComm x = new TacticsComm();
        x.setFields("100");

        Field fields = x.getClass().getDeclaredField("fields");
        Field fields2 = x.getClass().getDeclaredField("value");
        System.out.println(fields2.isAnnotationPresent(Tactics.class));
        Tactics xx = fields.getAnnotation(Tactics.class);
        if(xx.type().equals("[]")){
            System.out.println(fields.get(x));
        }
        System.out.println(xx.type());
    }

    private void xx(T investItem) {

        Field[] fields = investItem.getClass().getDeclaredFields();
        for(Field field : fields){
            if(!field.isAnnotationPresent(Tactics.class)){
                continue;
            }
            Tactics tactics = field.getAnnotation(Tactics.class);
            if("greater".equals(tactics.type())){//大于某个值

            }
            if("less".equals(tactics.type())){//小于某个值

            }
            if("contains".equals(tactics.type())){//包含于

            }
            if("equals".equals(tactics.type())){//等于

            }
        }
    }
}
