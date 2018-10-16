package util.beancopy;

import org.springframework.beans.BeanUtils;
import util.StringUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

public class BeanUtilTest {

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
        getSet(bo,vo);
        System.out.println(System.currentTimeMillis()-s + " | " + vo.toString());
//        testMethodInvoke(bo,vo);
//        testBeanCopy(bo,vo);
//        testFieldSet(bo,vo);
    }

    public static void getSet (UserApiBo bo,UserApiVo vo) throws Exception {
        vo.setId(bo.getId());
        vo.setUid(bo.getUid());
        vo.setApi("");
        vo.setRemainPoint(0);
        vo.setInvokedPoint(0);
        vo.setStatus("");
        vo.setLastUseTime(new Date());
        vo.setPrice(new BigDecimal("0"));
        vo.setHasSpePrice((byte)0);
        vo.setApplyStatus(0);
        vo.setApplyTime("");
        vo.setMaxOccurs(0);
        vo.setFavoriteStatus(0);
        vo.setDiscount(new BigDecimal("0"));
        vo.setMaxCountDay(0);
        vo.setBasicMap(bo.getBasicMap());
        vo.setUserList(null);
    }

    public static void testMethodInvoke (Object bo,Object vo) throws Exception {
        Long s = System.currentTimeMillis();
        Class vzz = vo.getClass();
        Class bzz = bo.getClass();
        Field[] fields = vzz.getDeclaredFields();
        for (Field field : fields){
            if ("serialVersionUID".equals(field.getName())){
                continue;
            }
            Field bfield = bzz.getDeclaredField(field.getName());
            if (bfield == null){
                continue;
            }
            if(!field.getName().equals(bfield.getName()) || !field.getType().equals(bfield.getType())){
                continue;
            }
            field.setAccessible(true);
            bfield.setAccessible(true);
//            System.out.println(field.getType());
            Method method = vzz.getMethod("set"+StringUtil.upperFirstCase(field.getName()), field.getType());
            method.invoke(vo, bfield.get(bo));
        }
        System.out.println(System.currentTimeMillis()-s + " | " + vo.toString());
    }

    private static void testBeanCopy (Object bo,Object vo){
        Long s = System.currentTimeMillis();
        BeanUtils.copyProperties(bo,vo);
        System.out.println(System.currentTimeMillis()-s + " | " + vo.toString());
    }

    private static void testFieldSet (Object bo,Object vo) throws Exception {
        Long s = System.currentTimeMillis();
        Field[] fields = vo.getClass().getDeclaredFields();
        Class bzz = bo.getClass();
        for (Field field : fields){
            if ("serialVersionUID".equals(field.getName())){
                continue;
            }
            Field bfield = bzz.getDeclaredField(field.getName());
            if (bfield == null){
                continue;
            }
            if(!field.getName().equals(bfield.getName()) || !field.getType().equals(bfield.getType())){
                continue;
            }
            field.setAccessible(true);
            bfield.setAccessible(true);
            field.set(vo,bfield.get(bo));
        }
        System.out.println(System.currentTimeMillis()-s + " | " + vo.toString());
    }
}
