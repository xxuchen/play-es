package priv.xuchen;

import org.apache.http.HttpHost;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.After;
import org.junit.Before;

/**
 * TODO
 *
 * @version 1.0
 * @author: xuchen
 * @date: 2021/11/27 20:38
 */
public class EsBaseTest {

    Logger logger = LogManager.getLogger(EsBaseTest.class);

    RestHighLevelClient esClient;
    String INDEX_NAME = "user";
    
    @Before
    public void init() {
        try {
            RestClientBuilder restClientBuilder = RestClient.builder(
                    new HttpHost("10.4.122.106", 9200),
                    new HttpHost("10.4.122.107", 9200),
                    new HttpHost("10.4.122.108", 9200)
            );
            esClient = new RestHighLevelClient(restClientBuilder);
            logger.info("初始化ES客户端成功----");
        } catch (Exception e) {
            logger.error("初始化ES客户端异常----", e.getMessage());
        }
        
    }
    
    @After
    public void destroy() {
        try {
            if (null != esClient) {
                esClient.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("销毁ES客户端成功----");
    }
}
