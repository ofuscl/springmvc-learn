package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

/**
 *  生成签名-工具类
 */
public class SignUtil {
	
    private static final Logger logger = LoggerFactory.getLogger(SignUtil.class);

    public static boolean validSign(String sign, Object... args) {
        String genSign = md5Lower("", args);
//        logger.info("客户签名：" + sign + "，本地签名：" + genSign);
        return sign != null && sign.equalsIgnoreCase(genSign);
    }

    public static String md5Upper(String split, Object... args) {
        String encryptStr = md5Lower(split, args);
        return encryptStr.toUpperCase();
    }


    public static String md5Lower(String split, Object... args) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < args.length; i++) {
            if (i < args.length - 1) {
                sb.append(args[i] == null ? "" : args[i] + split);
            } else {
                sb.append(args[i] == null ? "" : args[i]);
            }
        }
        
        byte[] bytes = null;
        try {
			bytes = sb.toString().getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			return null;
		}
        return md5Lower(bytes);
    }

    /**
     * 加密(MD5)
     *
     * @return 返回加密字串（大写）
     */
    public static String md5Upper(String str) {
        String encryptStr = md5Lower(str);
        return encryptStr.toUpperCase();
    }

    /**
     * 加密(MD5)
     *
     * @return 返回加密字串（小写）
     */
    public static String md5Lower(String s) {
    	
        if (null == s || "".equals(s))
            return "";

        return md5Lower(s.getBytes());
    }
    
    public static String md5Lower(byte[] bytes) {
    	char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

        try {
            MessageDigest mdtemp = MessageDigest.getInstance("MD5");
            mdtemp.update(bytes);
            byte[] md = mdtemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {

    }
    

}

