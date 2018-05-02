package java8;

/**
 * java8新增 ----default 修饰符.
 */
public interface DefaultClass {

    // default修饰的接口可以拥有方法体；实现在实现类中的操作；
    default String getCode(){
        return "";
    }
}
