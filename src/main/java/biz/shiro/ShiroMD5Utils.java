package biz.shiro;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/*
* @Description 散列算法 生成数据的摘要信息，是一种不可逆的算法，一般适合存储密码之类的数据，
* @Author x
* @Date 2018/3/24 13:28
*/
public class ShiroMD5Utils {

    //干扰数据 盐 防破解  -默认
    private static final String SALT = "mar%#$@";
    //散列算法类型为MD5
    private static final String ALGORITH_NAME = "MD5";
    //hash的次数
    private static final int HASH_ITERATIONS = 2;

    /**
     * 密码加密
     * @param pswd ---密码
     * @return
     */
    public static String encrypt(String pswd) {
        String newPassword = new SimpleHash(ALGORITH_NAME, pswd, ByteSource.Util.bytes(SALT), HASH_ITERATIONS).toHex();
        return newPassword;
    }

    /**
     * 密码加密
     * @param pswd ---密码
     * @param salt ---动态盐
     * @return
     */
    public static String encrypt(String pswd,String salt) {
        String newPassword = new SimpleHash(ALGORITH_NAME, pswd, salt, HASH_ITERATIONS).toHex();
        return newPassword;
    }

    public static void main(String[] args) {
        System.out.println("加密后String" + encrypt("123"));
    }
}
