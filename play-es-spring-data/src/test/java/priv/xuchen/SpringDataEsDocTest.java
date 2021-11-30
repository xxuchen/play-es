package priv.xuchen;

import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import priv.xuchen.base.TestAppBase;
import priv.xuchen.dao.ProductDao;
import priv.xuchen.entity.Product;

import java.io.IOException;

/**
 * TODO
 *
 * @version 1.0
 * @author: xuchen
 * @date: 2021/11/30 23:30
 */
public class SpringDataEsDocTest extends TestAppBase {

    @Autowired
    ProductDao productDao;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    /**
     * 按条件搜索
     * term查询
     * 老版本的使用方式
     */
    @Test
    public void test_termQuery() throws IOException {
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("category", "图文");
        Iterable<Product> productList = productDao.search(termQueryBuilder);
        for (Product p : productList) {
            System.out.println(p.toString());
        }
    }
}
