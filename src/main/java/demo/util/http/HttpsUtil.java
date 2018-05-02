package demo.util.http;


import demo.util.comm.JsonUtil;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * HTTPS请求(SSL)。
 * GET请求(推荐)  | String doGet(String getUrl)
 * GET请求(推荐)  | String doGet(String getUrl, int resTimeout)
 * POST请求(推荐) | String doPost(String postUrl, String param)
 * POST请求(推荐) | String doPost(String postUrl, String param, int resTimeout)
 * POST请求(推荐) | String doPostStr(String postUrl, Map<String, String> params)
 * POST请求(推荐) | String doPostStr(String postUrl, Map<String, String> params, int resTimeout)
 * POST请求(推荐) | String doPost(String postUrl, Map<String, Object> params)
 * POST请求(推荐) | String doPost(String postUrl, Map<String, Object> params, int resTimeout)
 * POST请求(推荐) | String doPostStr(String postUrl, Map<String, String> params, int resTimeout)
 *
 */
public class HttpsUtil {

    private static SslContextUtils sslContextUtils = new SslContextUtils();

    /**
     * POST请求(推荐).
     *
     * @param postUrl URL
     * @param param  请求参数
     */
    public static String doPost(String postUrl, String param) {
        return doPost(postUrl,param,null,HttpConst.TIME_OUT_CONNECTION,HttpConst.TIME_OUT_RESPONSE,HttpConst.CHARSET_UTF_8);
    }

    /**
     * POST请求(推荐).
     *
     * @param postUrl URL
     * @param param  请求参数
     * @param resTimeout  响应时长
     */
    public static String doPost(String postUrl, String param, int resTimeout) {
        return doPost(postUrl,param,null,HttpConst.TIME_OUT_CONNECTION,resTimeout,HttpConst.CHARSET_UTF_8);
    }

    /**
     * POST请求(推荐).
     *
     * @param postUrl URL
     * @param params  请求参数
     */
    public static String doPostStr(String postUrl, Map<String, String> params) {
        return doPost(postUrl,getParams(params));
    }

    /**
     * POST请求(推荐).
     *
     * @param postUrl URL
     * @param params  请求参数
     * @param resTimeout  响应时长
     */
    public static String doPostStr(String postUrl, Map<String, String> params, int resTimeout) {
        return doPost(postUrl,getParams(params),resTimeout);
    }

    /**
     * POST请求(推荐).
     *
     * @param postUrl URL
     * @param params  请求参数
     */
    public static String doPost(String postUrl, Map<String, Object> params) {
        return doPost(postUrl,JsonUtil.toJsonString(params));
    }

    /**
     * POST请求(推荐).
     *
     * @param postUrl URL
     * @param params  请求参数
     * @param resTimeout  响应时长
     */
    public static String doPost(String postUrl, Map<String, Object> params, int resTimeout) {
        return doPost(postUrl, JsonUtil.toJsonString(params),resTimeout);
    }

    /**
     * POST请求(推荐).
     *
     * @param postUrl URL
     * @param param  请求参数
     * @param requestProps  请求参数
     */
    public static String doPost(String postUrl, String param, Map<String, String> requestProps) {
        return doPost(postUrl,JsonUtil.toJsonString(param),requestProps,HttpConst.TIME_OUT_CONNECTION,HttpConst.TIME_OUT_RESPONSE,HttpConst.CHARSET_UTF_8);
    }

    /**
     * POST请求.
     *
     * @param postUrl URL
     * @param param  请求参数
     * @param requestProps  请求参数
     * @param connTimeout 连接时长
     * @param resTimeout  响应时长
     * @param charset  编码格式
     */
    public static String doPost(String postUrl, String param, Map<String, String> requestProps,int connTimeout, int resTimeout,String charset) {
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(postUrl);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();

            if (httpConn instanceof HttpsURLConnection) {
                sslContextUtils.initHttpsConnect((HttpsURLConnection) httpConn);
            }

            httpConn.setRequestMethod("POST");
            httpConn.setDoOutput(true);// 设置是否输出
            httpConn.setDoInput(true);// 设置是否读入
            httpConn.setRequestProperty("Content-type", "application/json;charset=utf-8");// 设置使用标准编码格式编码参数的名-值对
            httpConn.setConnectTimeout(connTimeout);
            httpConn.setReadTimeout(resTimeout);
            if (requestProps != null && !requestProps.isEmpty()) {
                for (Map.Entry<String, String> entry : requestProps.entrySet()) {
                    httpConn.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            // 发送请求
            httpConn.getOutputStream().write(param.getBytes(charset));
            httpConn.getOutputStream().flush();
            httpConn.getOutputStream().close();
            // 获取输入流
            BufferedReader reader = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), charset));
            char[] buf = new char[1024];
            int length = 0;
            while ((length = reader.read(buf)) > 0) {
                sb.append(buf, 0, length);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * GET请求(推荐).
     *
     * @param getUrl URL
     */
    public static String doGet(String getUrl) {
        return doGet(getUrl,HttpConst.TIME_OUT_CONNECTION,HttpConst.TIME_OUT_RESPONSE,HttpConst.CHARSET_UTF_8);
    }

    /**
     * GET请求(推荐).
     *
     * @param getUrl URL
     * @param resTimeout  响应时长
     */
    public static String doGet(String getUrl, int resTimeout) {
        return doGet(getUrl,HttpConst.TIME_OUT_CONNECTION,resTimeout,HttpConst.CHARSET_UTF_8);
    }

    /**
     * GET请求.
     *
     * @param getUrl URL
     * @param connTimeout 连接时长
     * @param resTimeout  响应时长
     * @param charset  编码格式
     */
    public static String doGet(String getUrl, int connTimeout, int resTimeout,String charset) {
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(getUrl);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            if (httpConn instanceof HttpsURLConnection) {
                sslContextUtils.initHttpsConnect((HttpsURLConnection) httpConn);
            }

            httpConn.setRequestMethod("GET");
            httpConn.setConnectTimeout(connTimeout);
            httpConn.setReadTimeout(resTimeout);
            InputStream inStream = httpConn.getInputStream();
            // 获取输入流
            BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, charset));
            char[] buf = new char[1024];
            int length = 0;
            while ((length = reader.read(buf)) > 0) {
                sb.append(buf, 0, length);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static String getParams(Map<String, String> paramValues) {
        String params = "";
        String beginLetter = "";
        StringBuffer sb = new StringBuffer();
        for (String s : paramValues.keySet()) {
            if (params.equals("")) {
                sb.append(beginLetter);
                sb.append(s);
                sb.append("=");
                sb.append(paramValues.get(s));
            } else {
                sb.append("&");
                sb.append(s);
                sb.append("=");
                sb.append(paramValues.get(s));
            }
        }
        return params;
    }
}
