package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 *
 * URL编码(推荐)   | String encodeURL(String url)
 * URL编码(推荐)   | String encodeURL(String url, String charset)
 * URL解码(推荐)   | String decodeURL(String url)
 * URL解码(推荐)   | String decodeURL(String url, String charset)
 * 字符串编码转换(推荐)   | String convert(String args)
 * 字符串编码转换(推荐)   | String convert(String args, String srcCharset, String destCharset)
 * 字符串转码（推荐）     | String convertUnCharset(String args)
 */
public class CharsetUtil {

    private static final Logger logger = LoggerFactory.getLogger(CharsetUtil.class);

    /** UTF-8 */
    public static String UTF_8 = "UTF-8";
    /** GBK */
    public static String GBK = "GBK";
    /**ISO-8859-1**/
    public static String ISO_8859_1 = "ISO-8859-1";


    /**
     * 编码URL，默认使用UTF-8编码
     *
     * @param url URL
     * @return 编码后的URL
     */
    public static String encodeURL(String url){
        return encodeURL(url, UTF_8);
    }

    /**
     * 指定编码URL
     *
     * @param url URL
     * @param charset 编码
     * @return 编码后的URL
     */
    public static String encodeURL(String url, String charset){
        try {
            return URLEncoder.encode(url, charset);
        } catch (UnsupportedEncodingException e) {
            logger.error("URL编码错误: " + e.getMessage(), e);
        }
        return url;
    }

    /**
     * 解码URL,默认使用UTF-8解码
     *
     * @param url URL
     * @return 解码后的URL
     */
    public static String decodeURL(String url) {
        return decodeURL(url, UTF_8);
    }

    /**
     * 解码URL
     *
     * @param url URL
     * @param charset 编码
     * @return 解码后的URL
     */
    public static String decodeURL(String url, String charset){
        try {
            if(StringUtil.isEmpty(url)){
                return url;
            }
            return URLDecoder.decode(url, charset);
        } catch (UnsupportedEncodingException e) {
            logger.error("URL编码错误: " + e.getMessage(), e);
        }
        return url;
    }


    /**
     * 字符串编码转换 ISO转换为UTF
     *
     * @param args 字符串
     * @return 转换后的字符集
     */
    public static String convert(String args) {
        return convert(args, null, null);
    }

    /**
     * 转换字符串的字符集编码
     *
     * @param args 字符串
     * @param srcCharset 源字符集，默认ISO-8859-1
     * @param destCharset 目标字符集，默认UTF-8
     * @return 转换后的字符集
     */
    public static String convert(String args, String srcCharset, String destCharset) {
        if(StringUtil.isEmpty(srcCharset)) {
            srcCharset = ISO_8859_1;
        }

        if(StringUtil.isEmpty(destCharset)) {
            destCharset = UTF_8;
        }
        if (StringUtil.isEmpty(args) || srcCharset.equals(destCharset)) {
            return args;
        }

        try {
            args = new String(args.getBytes(srcCharset), destCharset);
        }catch (Exception e){
            logger.error("字符串转码错误：" + e.getMessage(), e);
        }
        return args;
    }

    /**
     * 字符串未知编码的转码
     *
     * @param args 字符串
     * @return 转换后的字符集
     */
    public static String convertUnCharset(String args){
        if (StringUtil.isNotEmpty(args)){
            try {
                if (args.equals(new String(args.getBytes(ISO_8859_1), ISO_8859_1))) {
                    args = new String(args.getBytes(ISO_8859_1),UTF_8);
                }
            } catch (UnsupportedEncodingException e) {
                logger.error("参数解码错误: " + e.getMessage(), e);
            }
        }
        return args;
    }
}