package demo.util.http;

/**
 * HTTP相关定数
 */
public class HttpConst {

    /** 响应时长 */
    public static final int TIME_OUT_RESPONSE = 20000;
    /** 连接时长 */
    public static final int TIME_OUT_CONNECTION = 3000;
    /** 编码格式-UTF-8 */
    public static final String CHARSET_UTF_8 = "UTF-8";

    /**
     * 连接重试次数
     */
    public static final int RETRY_COUNT = 3;

    /**
     * 总共最大连接数
     */
    public static final int DEFAULT_MAX_CONNECTIONS = 1000;



}
