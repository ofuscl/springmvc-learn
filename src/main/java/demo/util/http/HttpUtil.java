package demo.util.http;

import demo.util.comm.JsonUtil;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * HTTP请求。
 * 技术说明：
 * 1、GET限制2083字节、效率高、安全性差；POST无数量限制、效率稍差、安全性更高
 * 2、HttpClient就是一个增强版的HttpURLConnection；HttpClient是个很不错的开源框架，封装了访问http的请求头，参数，内容体，响应等等；HttpURLConnection是java的标准类；
 * 3、采用HttpClient进行请求；涉及请求时长、响应时长、重试次数、请求响应编码等内容；
 *
 * ------------------------------------------------------------------------------------------------------------------------------------------------
 * GET请求(推荐)  | String doGet(String url)
 * GET请求(推荐)  | String doGet(String url, int resTimeout)
 * POST请求(推荐) | String doPost(String url, String param)
 * POST请求(推荐) | String doPost(String url, String param, int resTimeout)
 * POST请求(推荐) | String doPost(String url, Map<String, String> params)
 * POST请求(推荐) | String doPost(String url, Map<String, String> params, int resTimeout)
 * POST请求(推荐) | String doPost(String url, Map<String, String> params, int resTimeout, Map<String, String> headerMap)
 *
 */
public class HttpUtil {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * GET请求(推荐).
     *
     * @param url URL
     */
    public String doGet(String url) {
        return doGet(url, HttpConst.TIME_OUT_CONNECTION, HttpConst.TIME_OUT_RESPONSE, HttpConst.RETRY_COUNT,HttpConst.CHARSET_UTF_8);
    }

    /**
     * GET请求(推荐).
     *
     * @param url        URL
     * @param resTimeout 响应时长
     */
    public String doGet(String url, int resTimeout) {
        return doGet(url, HttpConst.TIME_OUT_CONNECTION, resTimeout, HttpConst.RETRY_COUNT,HttpConst.CHARSET_UTF_8);
    }

    /**
     * GET请求.
     *
     * @param url         URL
     * @param connTimeout 连接时长
     * @param resTimeout  响应时长
     * @param retryCount  重试次数
     * @param charset  编码格式
     */
    public String doGet(String url, int connTimeout, int resTimeout, int retryCount,String charset) {
        HttpClient client = new HttpClient();
        client.getHttpConnectionManager().getParams().setConnectionTimeout(connTimeout);
        client.getHttpConnectionManager().getParams().setSoTimeout(resTimeout);
        String res = null;
        GetMethod method = null;// Create a method instance.
        try {
            method = new GetMethod(url);
            method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(retryCount, false));
            method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, charset);
            int statusCode = client.executeMethod(method);// Execute the method.
            if (statusCode == HttpStatus.SC_OK) {
                InputStreamReader readerInput;
                if (charset == null) {
                    readerInput = new InputStreamReader(method.getResponseBodyAsStream());
                } else {
                    readerInput = new InputStreamReader(method.getResponseBodyAsStream(), charset);
                }
                BufferedReader reader = new BufferedReader(readerInput);
                StringBuffer stringBuffer = new StringBuffer();
                String str = "";
                while ((str = reader.readLine()) != null) {
                    stringBuffer.append(str);
                }
                res = stringBuffer.toString();
            } else {
                logger.info("Response Code: " + statusCode + ", url=" + url);
            }
        } catch (Exception e) {
            logger.error("url=" + url + "\r\n", e);
        } finally {
            if (method != null) {
                method.releaseConnection();
            }
        }
        return res;
    }

    /**
     * POST请求(推荐).
     *
     * @param url   url
     * @param param 请求参数
     */
    public String doPost(String url, String param) {
        return doPost(url, param, HttpConst.TIME_OUT_CONNECTION, HttpConst.TIME_OUT_RESPONSE,HttpConst.CHARSET_UTF_8,null);
    }

    /**
     * POST请求(推荐).
     *
     * @param url         url
     * @param param       请求参数
     * @param resTimeout  超时时间
     */
    public String doPost(String url, String param, int resTimeout) {
        return doPost(url, param, HttpConst.TIME_OUT_CONNECTION, resTimeout,HttpConst.CHARSET_UTF_8,null);
    }

    /**
     * POST请求(推荐).
     *
     * @param url    URL
     * @param params 请求参数
     */
    public String doPost(String url, Map<String, String> params) {
        return doPost(url, JsonUtil.toJsonString(params));
    }

    /**
     * POST请求(推荐).
     *
     * @param url     URL
     * @param params  请求参数
     * @param resTimeout 响应时长
     */
    public String doPost(String url, Map<String, String> params,int resTimeout) {
        return doPost(url, JsonUtil.toJsonString(params), resTimeout);
    }

    /**
     * POST请求(推荐).
     *
     * @param url        url
     * @param params     请求参数
     * @param resTimeout 超时时间
     * @param headerMap     报文头
     */
    public String doPost(String url, Map<String, Object> params,int resTimeout, Map<String, String> headerMap) {
        return doPost(url, JsonUtil.toJsonString(params), HttpConst.TIME_OUT_CONNECTION,resTimeout, HttpConst.CHARSET_UTF_8,headerMap);
    }

    /**
     * POST请求.
     *
     * @param url        url
     * @param param     请求参数
     * @param charset    编码格式
     * @param resTimeout 超时时间
     * @param headerMap  报文头
     */
    public String doPost(String url, String param, int connTimeout,int resTimeout, String charset, Map<String, String> headerMap) {
        HttpClient httpClient = new HttpClient();
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(connTimeout);
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(resTimeout);
        PostMethod post = new PostMethod(url);
        try {
            post.setRequestEntity(new StringRequestEntity(param, "application/json", charset));
            if (null != headerMap && !headerMap.isEmpty()) {
                for (Map.Entry<String, String> headerEntry : headerMap.entrySet()) {
                    post.setRequestHeader(headerEntry.getKey(), headerEntry.getValue());
                }
            }
            post.setRequestHeader("Content-Type", "application/json; charset=UTF-8");
            httpClient.executeMethod(post);
            // 响应结果
            BufferedReader reader = new BufferedReader(new InputStreamReader(post.getResponseBodyAsStream(), charset));
            StringBuffer stringBuffer = new StringBuffer();
            String str = "";
            while ((str = reader.readLine()) != null) {
                stringBuffer.append(str);
            }
            return stringBuffer.toString();
        } catch (Exception e) {
            logger.error("url=" + url + "\r\n", e);
            return null;
        } finally {
            post.releaseConnection();
        }
    }
}
