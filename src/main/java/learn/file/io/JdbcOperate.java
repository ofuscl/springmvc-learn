package learn.file.io;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.formula.functions.T;
import util.JsonUtil;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JdbcOperate {

    /** 数据库连接 */
    private static final ConnDB ysConnDB = new ConnDB("com.mysql.jdbc.Driver", "jdbc:mysql://10.1.1.3:3306", "yscredit", "root", "root");

    public static void main(String[] args) {

        queryTable(ysConnDB,"select * from api");
    }
    public static List<T> queryTable(ConnDB connDB,String sql){
        Connection conn = null;
        try {
            Class.forName(connDB.getDriver()).newInstance();
            conn = DriverManager.getConnection(connDB.getUrl()+"/"+connDB.getSchema(), connDB.getName(),connDB.getPassWord());
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                StringBuffer s = new StringBuffer();
                for (int i=0; i< 8;i++){
                    s.append(rs.getString(i+1)).append(" | ");
                }
                System.out.println(s);
            }
            stmt.close();
            return null;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        } finally {

            if (conn!=null) {
                try {
                    conn.close();
                } catch (Exception e) {

                }
            }
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class ConnDB {

        //驱动
        private String driver;
        //数据库地址
        private String url;
        //密码
        private String schema;
        //用户名
        private String name;
        //密码
        private String passWord;
    }
}
