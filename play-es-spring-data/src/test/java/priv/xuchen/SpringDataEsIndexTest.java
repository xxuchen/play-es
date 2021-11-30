package priv.xuchen;

import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * Spring Data Elasticsearch操作索引示例
 *
 * @version 1.0
 * @author: xuchen
 * @date: 2021/11/29 22:26
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringDataEsIndexTest {
    

    @Autowired
    private RestHighLevelClient elasticsearchClient;
    
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;
    
    /**
     * 创建索引
     */
    @Test
    public void createIndex() throws IOException {
        System.out.println("创建索引");
    }
}
