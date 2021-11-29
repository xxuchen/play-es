package priv.xuchen;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

/**
 * TODO
 *
 * @version 1.0
 * @author: xuchen
 * @date: 2021/11/27 19:55
 */
public class PlayEsClientApplication {
    public static void main(String[] args) throws IOException {
        RestClientBuilder restClientBuilder = RestClient.builder(
                new HttpHost("10.4.122.106", 9200),
                new HttpHost("10.4.122.107", 9200),
                new HttpHost("10.4.122.108", 9200)
        );

        RestHighLevelClient esClient = new RestHighLevelClient(restClientBuilder);
        
        esClient.close();
    }

}
