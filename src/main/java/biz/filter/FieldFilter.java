package biz.filter;


import util.JsonUtil;

/**
 * 字段过滤
 * 方案一：每次都新建对象；优点：对象独立，使用清晰，开发快；缺点：新建过多重复对象，且不能做到相互比较；
 * 方案二：配置化过滤处置；优点：灵活，减少程序开发；缺点：过于灵活，程序看起来反而不清晰，且配置维护工作量大
 * 方案三：利用继承的方法,子类继承时覆盖并且忽略父类字段；优点：减少对象字段冗余，可以知道和基类的关联；缺点：基类新增，子类都要处理；
 *
 * Created by yunfan on 2018/8/3.
 */
public class FieldFilter {

    public static void main(String[] args) {

        TesteeVo ee = new TesteeVo();
        ee.setName("haha");
        ee.setAge(25);
        ee.setDesc("desc");

        System.out.println(JsonUtil.toJsonFromObject(ee));
    }
}
