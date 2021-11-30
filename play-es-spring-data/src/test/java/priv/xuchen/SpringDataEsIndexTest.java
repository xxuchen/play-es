package priv.xuchen;

import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import priv.xuchen.base.TestAppBase;
import priv.xuchen.entity.Product;

import java.io.IOException;

/**
 * Spring Data Elasticsearch操作索引示例
 *
 * @version 1.0
 * @author: xuchen
 * @date: 2021/11/29 22:26
 */
public class SpringDataEsIndexTest extends TestAppBase {
    

    @Autowired
    private RestHighLevelClient elasticsearchClient;
    
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;
    
    /**
     * 创建索引
     */
    @Test
    public void test_createIndex() throws IOException {
        // 这里通过spring data会自动创建索引
        System.out.println("创建索引");
    }

    /**
     * 删除索引
     * 老版本索引删除方式
     */
    @Test
    public void test_deleteIndex() throws IOException {
        boolean delete = elasticsearchRestTemplate.deleteIndex(Product.class);
        System.out.println(delete);
    }

    /**
     * 删除索引
     * 新版本索引删除方式
     */
    @Test
    public void test_deleteIndex_new() {
        IndexOperations indexOperations = elasticsearchRestTemplate.indexOps(Product.class);
        boolean delete = indexOperations.delete();
        System.out.println(delete);
    }
}
