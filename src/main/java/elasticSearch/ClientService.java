package elasticSearch;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Created by yanli on 2018/6/5.
 */
public class ClientService {
    private Logger logger = LoggerFactory.getLogger(getClass());

//    @Value(value = "${aliyun.es.username}")
    private final String username = "";
//    @Value(value = "${aliyun.es.password}")
    private final String password ="";
//    @Value(value = "${aliyun.es.host}")
    private final String aliyunHost ="";
//    @Value(value = "${aliyun.es.port}")
    private final String aliyunPort ="";

    private static RestClientConnectionPool pool;


    public RestClient getRestClient() throws Exception{

        if(pool == null){
            pool = new RestClientConnectionPool(10,aliyunHost,aliyunPort,username,password);
        }

        return pool.fetchRestClient(2000l);
    }

    private void releaseRestClient(RestClient restClient){
        pool.releaseRestClient(restClient);
    }

    public RestClient getSingleRestClient(RestClient restClient) {
        pool.removeRestClient(restClient);
        restClient = RestClientConnectionPool.createRestClient(aliyunHost,aliyunPort,username,password);
        pool.releaseRestClient(restClient);
        return restClient;
    }

    public String searchResultStr(String searchString, String endPoint) {
        RestClient restClient = null;
        try {
            HttpEntity entity = new NStringEntity(searchString, ContentType.APPLICATION_JSON);
            restClient = this.getRestClient();
            Response response = restClient.
                    performRequest("GET", endPoint + "_search", Collections.EMPTY_MAP, entity);
            return EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            if(e.getMessage().contains("I/O reactor status: STOPPED")){
                try {
                    HttpEntity entity = new NStringEntity(searchString, ContentType.APPLICATION_JSON);
                    Response response = this.getSingleRestClient(restClient).
                            performRequest("GET", endPoint + "_search", Collections.EMPTY_MAP, entity);
                    return EntityUtils.toString(response.getEntity());
                } catch (Exception ex) {
                    logger.error("es查询失败: endPoint=" + endPoint + "searchString=" + searchString, ex);
                }
            }
            logger.error("es查询失败: endPoint=" + endPoint + "searchString=" + searchString, e);
            return "";
        }
    }

    public String searchResultDetailStr(String id, String endPoint) {
        RestClient restClient = null;
        try {
            HttpEntity entity = new NStringEntity("", ContentType.APPLICATION_JSON);
            restClient = this.getRestClient();
            Response response = restClient.
                    performRequest("GET", endPoint + id, Collections.EMPTY_MAP, entity);
            return EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            if(e.getMessage().contains("I/O reactor status: STOPPED")){
                try {
                    HttpEntity entity = new NStringEntity("", ContentType.APPLICATION_JSON);
                    Response response = this.getSingleRestClient(restClient).
                            performRequest("GET", endPoint + id, Collections.EMPTY_MAP, entity);
                    return EntityUtils.toString(response.getEntity());
                } catch (Exception ex) {
                    logger.error("es查询失败: endPoint=" + endPoint, ex);
                }
            }
//            logger.error("es查询id:" + id + "失败: endPoint=" + endPoint, e);
            return "";
        }
    }
}
