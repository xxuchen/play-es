package priv.xuchen.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Spring Boot集成 RestHighLevelClient 配置
 * @version 1.0
 * @author: xuchen
 * @date: 2021/4/30 3:13
 */
@Configuration
public class EsRestClientConfig extends AbstractElasticsearchConfiguration {

    @Value("#{'${priv.elasticsearch.uris}'.split(',')}")
    private List<String> uris;
    
    @Value("${priv.elasticsearch.username}")
    private String username;

    @Value("${priv.elasticsearch.password}")
    private String password;
    
    @Override
    @Bean
    public RestHighLevelClient elasticsearchClient() {
        List<HttpHost> esNodes = new ArrayList<>();
        for (String uri : uris) {
            esNodes.add(HttpHost.create(uri));
        }
        RestClientBuilder restClientBuilder = RestClient.builder(esNodes.toArray(new HttpHost[]{}));
        if (null != username) {
            final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            /*设置账号密码*/
            credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username,password));
            restClientBuilder.setHttpClientConfigCallback(f -> {
                    f.disableAuthCaching();
                    f.setDefaultCredentialsProvider(credentialsProvider);
                    return f;
            });
        }
        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(restClientBuilder);
        return restHighLevelClient;
    }
}
