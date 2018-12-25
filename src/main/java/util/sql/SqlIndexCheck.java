package util.sql;

/**
 * 检测是否使用索引
 * 1、使用explain执行SQL看看是否有索引返回；
 * 2、mybatis 、habernate 、jdbcTemplate的特性分析
 * 3、
 * Created by YScredit on 2018/12/22.
 */
public class SqlIndexCheck {

    public static void main(String[] args) {

    }

    /**
     * JdbcTemplate可以自己封装共通方法，实现拦截
     */
    private void checkJdbcTemplate(String sql){

    }

    /**
     * Habernate需实现StatementInspector
     */
    private void checkHabernate(){

    }

    /**
     * Mybatis提供了MybatisSqlInterceptor拦截器，可以对所有SQL进行统一处理
     */
    private void checkMybatis(){

    }


}
