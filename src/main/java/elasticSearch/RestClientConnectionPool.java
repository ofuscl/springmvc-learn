package elasticSearch;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;

import java.util.LinkedList;

public class RestClientConnectionPool {

    private LinkedList<RestClient> pool = new LinkedList<>();

    /**
     * 初始化连接池的大小
     * @param initialSize
     */
    public RestClientConnectionPool(int initialSize,String aliyunHost,String aliyunPort,String username,String password) {
        if (initialSize > 0) {
            for (int i = 0; i < initialSize; i++) {
                pool.addLast(createRestClient(aliyunHost,aliyunPort,username,password));
            }
        }
    }

    /**
     * 释放连接，放回到连接池
     * @param restClient
     */
    public void releaseRestClient(RestClient restClient){
        if(restClient != null){
            synchronized (pool) {
                // 连接释放后需要进行通知，这样其他消费者能够感知到连接池中已经归还了一个连接
                pool.addLast(restClient);
                pool.notifyAll();
            }
        }
    }

    /**
     * 释放连接，放回到连接池
     * @param restClient
     */
    public void removeRestClient(RestClient restClient){
        if(restClient != null){
            pool.remove(restClient);
        }
    }

    /**
     * 在mills内无法获取到连接，将会返回null
     * @param mills
     * @return
     * @throws InterruptedException
     */
    public RestClient fetchRestClient(long mills) throws InterruptedException{
        synchronized (pool) {
            // 无限制等待
            if (mills <= 0) {
                while (pool.isEmpty()) {
                    pool.wait();
                }
                return pool.removeFirst();
            }else{
                long future = System.currentTimeMillis() + mills;
                long remaining = mills;
                while (pool.isEmpty() && remaining > 0) {
                    // 等待超时
                    pool.wait(remaining);
                    remaining = future - System.currentTimeMillis();
                }
                RestClient result = null;
                if (!pool.isEmpty()) {
                    result = pool.removeFirst();
                    releaseRestClient(result);
                }
                return result;
            }
        }
    }

    /**
     * 创建一个Connection的代理，在commit时休眠100毫秒
     * @return
     */
    public static final RestClient createRestClient(String aliyunHost,String aliyunPort,String username,String password){

        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(username, password));

        long nowTime = System.currentTimeMillis();
        return RestClient.builder(new HttpHost(aliyunHost,Integer.parseInt(aliyunPort)))
                .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                    @Override
                    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
//                        httpClientBuilder.setMaxConnPerRoute(50);
//                        httpClientBuilder.setMaxConnTotal(150);
                        return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                    }
                })
                .setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
                    @Override
                    public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder builder) {
                        builder.setConnectTimeout(3000);
                        builder.setSocketTimeout(30000);
                        builder.setConnectionRequestTimeout(2000);
                        return builder;
                    }
                }).build();
    }
}
