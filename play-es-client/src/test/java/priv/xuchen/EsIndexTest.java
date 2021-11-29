package priv.xuchen;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.junit.Test;

import java.io.IOException;

/**
 * ES索引操作示例
 *
 * @version 1.0
 * @author: xuchen
 * @date: 2021/11/27 20:37
 */
public class EsIndexTest extends EsBaseTest {

    Logger logger = LogManager.getLogger(EsIndexTest.class);
    
    /**
     * 创建索引
     */
    @Test
    public void test_createIndex() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest(INDEX_NAME);
        CreateIndexResponse response = esClient.indices().create(request, RequestOptions.DEFAULT);
        logger.info(response.isAcknowledged());
    }

    /**
     * 查询索引
     */
    @Test
    public void test_getIndex() throws IOException {
        GetIndexRequest request = new GetIndexRequest(INDEX_NAME);
        GetIndexResponse response = esClient.indices().get(request, RequestOptions.DEFAULT);
        logger.info("别名==" + response.getAliases());
        logger.info("映射==" + response.getMappings());
        logger.info("设置==" + response.getSettings());
    }

    /**
     * 删除索引
     */
    @Test
    public void test_deleteIndex() throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest(INDEX_NAME);
        AcknowledgedResponse response = esClient.indices().delete(request, RequestOptions.DEFAULT);
        logger.info(response.isAcknowledged());
    }
    
    
}
