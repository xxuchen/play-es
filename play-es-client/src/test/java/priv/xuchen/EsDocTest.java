package priv.xuchen;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.MaxAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.ParsedValueCount;
import org.elasticsearch.search.aggregations.metrics.ValueCountAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import priv.xuchen.entity.User;

import java.io.IOException;
import java.util.List;

/**
 * ES文档操作示例
 * 
 * @version 1.0
 * @author: xuchen
 * @date: 2021/11/27 20:38
 */
public class EsDocTest extends EsBaseTest {

    Logger logger = LogManager.getLogger(EsDocTest.class);

    /**
     * 创建文档
     */
    @Test
    public void test_createDoc() throws IOException {
        IndexRequest request = new IndexRequest();
        User user = new User("1001", "zhangsan", "男", 35);
        request.index(INDEX_NAME).id(user.getId());
        request.source(JSONObject.toJSONString(user), XContentType.JSON);

        IndexResponse response = esClient.index(request, RequestOptions.DEFAULT);
        logger.info("数据插入结果===" + response.getResult());
    }

    /**
     * 修改文档
     */
    @Test
    public void test_updateDoc() throws IOException {
        UpdateRequest request = new UpdateRequest();
        request.index(INDEX_NAME).id("1001");
        request.doc(XContentType.JSON, "sex", "女");

        UpdateResponse response = esClient.update(request, RequestOptions.DEFAULT);
        logger.info("数据修改结果===" + response.getResult());
    }

    /**
     * 查询文档
     */
    @Test
    public void test_getDoc() throws IOException {
        GetRequest request = new GetRequest();
        request.index(INDEX_NAME).id("1001");
        
        GetResponse response = esClient.get(request, RequestOptions.DEFAULT);
        logger.info("获取数据结果===" + response.getSourceAsString());
        
        //将获取的数据直接转换成对象
        User user = JSON.parseObject(response.getSourceAsString(), User.class);
        logger.info("对象转换结果===" + user.toString());
    }

    /**
     * 删除文档
     */
    @Test
    public void test_deleteDoc() throws IOException {
        DeleteRequest request = new DeleteRequest();
        request.index(INDEX_NAME).id("1001");
        
        DeleteResponse response = esClient.delete(request, RequestOptions.DEFAULT);
        logger.info("删除数据结果===" + response.getResult());
    }

    /**
     * 批量插入数据
     */
    @Test
    public void test_batchCreateDoc() throws IOException {
        BulkRequest request = new BulkRequest();
        for (int i = 0; i < 10; i++) {
            User user = new User("100" + i, "zhangsan" + i,  i%2==0 ? "男" : "女", 32 + i);
            //这个地方要注意写法
            request.add(new IndexRequest(INDEX_NAME).id(user.getId()).source(JSONObject.toJSONString (user),XContentType.JSON));
        }
        
        BulkResponse responses = esClient.bulk(request, RequestOptions.DEFAULT);
        logger.info("批量插入时间：" + responses.getTook());
        logger.info("数据插入项：" + responses.getItems());
    }

    /**
     * 批量删除数据
     */
    @Test
    public void test_batchDeleteDoc() throws IOException {
        BulkRequest request = new BulkRequest();
        for (int i = 0; i < 10; i++) {
            request.add(new DeleteRequest(INDEX_NAME).id("100" + i));
        }
        
        BulkResponse responses = esClient.bulk(request, RequestOptions.DEFAULT);
        logger.info("批量删除时间：" + responses.getTook());
    }

    /**
     * 全量查询
     */
    @Test
    public void test_queryAll() throws IOException {
        SearchRequest request = new SearchRequest(INDEX_NAME);
        request.source(new SearchSourceBuilder().query(QueryBuilders.matchAllQuery()));
        
        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
        
        //获取搜索命中数据
        SearchHits hits = response.getHits();
        logger.info("全量查询时间：" + response.getTook());
        logger.info("命中条数：" + hits.getTotalHits());
        
        for (SearchHit hit : hits) {
            logger.info("命中数据：" + hit.getSourceAsString());
        }
    }

    /**
     * 按条件查询
     */
    @Test
    public void test_queryByCondition() throws IOException {
        SearchRequest request = new SearchRequest(INDEX_NAME);
        request.source(new SearchSourceBuilder().query(QueryBuilders.termQuery("sex", "女")));
        
        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
        //获取命中的数据
        SearchHits hits = response.getHits();
        logger.info("按条件查询时间：" + response.getTook());
        logger.info("命中条数：" + hits.getTotalHits());

        for (SearchHit hit : hits) {
            logger.info("命中数据：" + hit.getSourceAsString());
        }
    }

    /**
     * 按条件查询
     */
    @Test
    public void test_queryForPaged() throws IOException {
        SearchRequest request = new SearchRequest(INDEX_NAME);

        SearchSourceBuilder query = new SearchSourceBuilder().query(QueryBuilders.termQuery("sex", "女"));
        query.from(0);
        query.size(2);
        request.source(query);

        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
        //获取命中的数据
        SearchHits hits = response.getHits();
        logger.info("按条件查询时间：" + response.getTook());
        logger.info("命中条数：" + hits.getTotalHits());

        for (SearchHit hit : hits) {
            logger.info("命中数据：" + hit.getSourceAsString());
        }
    }

    /**
     * 查询结果排序
     */
    @Test
    public void test_queryOrderBy() throws IOException {
        SearchRequest request = new SearchRequest(INDEX_NAME);

        SearchSourceBuilder query = new SearchSourceBuilder().query(QueryBuilders.termQuery("sex", "女"));
        //分页
        query.from(0);
        query.size(10);
        //查询结果排序
        query.sort("age", SortOrder.DESC);
        request.source(query);

        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
        //获取命中的数据
        SearchHits hits = response.getHits();
        logger.info("按条件查询时间：" + response.getTook());
        logger.info("命中条数：" + hits.getTotalHits());

        for (SearchHit hit : hits) {
            logger.info("命中数据：" + hit.getSourceAsString());
        }
    }

    /**
     * 查询结果指定显示字段
     */
    @Test
    public void test_queryFieldFilter() throws IOException {
        SearchRequest request = new SearchRequest(INDEX_NAME);

        SearchSourceBuilder query = new SearchSourceBuilder().query(QueryBuilders.termQuery("sex", "女"));
        //包含的字段
        String[] includes = {};
        //排除的字段
        String[] excludes = {"sex"};
        query.fetchSource(includes, excludes);
        request.source(query);

        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
        //获取命中的数据
        SearchHits hits = response.getHits();
        logger.info("按条件查询时间：" + response.getTook());
        logger.info("命中条数：" + hits.getTotalHits());

        for (SearchHit hit : hits) {
            logger.info("命中数据：" + hit.getSourceAsString());
        }
    }

    /**
     * 条件“与”查询
     */
    @Test
    public void test_ConditionAndQuery() throws IOException {
        SearchRequest request = new SearchRequest(INDEX_NAME);

        SearchSourceBuilder query = new SearchSourceBuilder();
        //条件构造
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.termQuery("sex", "男"));
        boolQueryBuilder.must(QueryBuilders.termQuery("age", 36));
        query.query(boolQueryBuilder);
        
        request.source(query);

        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
        //获取命中的数据
        SearchHits hits = response.getHits();
        logger.info("按条件查询时间：" + response.getTook());
        logger.info("命中条数：" + hits.getTotalHits());

        for (SearchHit hit : hits) {
            logger.info("命中数据：" + hit.getSourceAsString());
        }
    }

    /**
     * 条件“或”查询
     */
    @Test
    public void test_ConditionOrQuery() throws IOException {
        SearchRequest request = new SearchRequest(INDEX_NAME);

        SearchSourceBuilder query = new SearchSourceBuilder();
        //条件构造
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.should(QueryBuilders.termQuery("sex", "男"));
        boolQueryBuilder.should(QueryBuilders.termQuery("age", 36));
        query.query(boolQueryBuilder);

        request.source(query);

        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
        //获取命中的数据
        SearchHits hits = response.getHits();
        logger.info("按条件查询时间：" + response.getTook());
        logger.info("命中条数：" + hits.getTotalHits());

        for (SearchHit hit : hits) {
            logger.info("命中数据：" + hit.getSourceAsString());
        }
    }

    /**
     * 范围查询
     */
    @Test
    public void test_rangeQuery() throws IOException {
        SearchRequest request = new SearchRequest(INDEX_NAME);

        SearchSourceBuilder query = new SearchSourceBuilder();
        //条件构造
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("age");
        // 大于等于
        rangeQueryBuilder.gte(35);
        // 小于等于
        rangeQueryBuilder.lte(40);
        query.query(rangeQueryBuilder);

        request.source(query);

        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
        //获取命中的数据
        SearchHits hits = response.getHits();
        logger.info("按条件查询时间：" + response.getTook());
        logger.info("命中条数：" + hits.getTotalHits());

        for (SearchHit hit : hits) {
            logger.info("命中数据：" + hit.getSourceAsString());
        }
    }

    /**
     * 模糊查询
     */
    @Test
    public void test_fuzzyQuery() throws IOException {
        SearchRequest request = new SearchRequest(INDEX_NAME);

        SearchSourceBuilder query = new SearchSourceBuilder();
        // fuzzy 查询是一个词项级别的查询，所以它不做任何分析。
        // 它通过某个词项以及指定的 fuzziness 查找到词典中所有的词项。 fuzziness 默认设置为 AUTO 。
        // fuzziness模糊查询编辑距离
        query.query(QueryBuilders.fuzzyQuery("name", "zhangsan").fuzziness(Fuzziness.AUTO));

        request.source(query);

        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
        //获取命中的数据
        SearchHits hits = response.getHits();
        logger.info("按条件查询时间：" + response.getTook());
        logger.info("命中条数：" + hits.getTotalHits());

        for (SearchHit hit : hits) {
            logger.info("命中数据：" + hit.getSourceAsString());
        }
    }

    /**
     * 查询高亮展示
     */
    @Test
    public void test_highlightQuery() throws IOException {
        SearchRequest request = new SearchRequest(INDEX_NAME);

        SearchSourceBuilder query = new SearchSourceBuilder();
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", "zhangsan1");
        
        //高亮定义
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<font color='red'>");
        highlightBuilder.postTags("</font>");
        highlightBuilder.field("name");
        query.highlighter(highlightBuilder);
        query.query(termQueryBuilder);

        request.source(query);

        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
        //获取命中的数据
        SearchHits hits = response.getHits();
        logger.info("按条件查询时间：" + response.getTook());
        logger.info("命中条数：" + hits.getTotalHits());

        for (SearchHit hit : hits) {
            logger.info("命中数据：" + hit.getSourceAsString());
        }
    }

    /**
     * 聚合查询
     */
    @Test
    public void test_aggQuery() throws IOException {
        SearchRequest request = new SearchRequest(INDEX_NAME);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //聚合条件
        MaxAggregationBuilder maxAggregationBuilder = AggregationBuilders.max("maxAge").field("age");
        searchSourceBuilder.aggregation(maxAggregationBuilder);
        request.source(searchSourceBuilder);
        
        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
        
        //获取命中的数据
        Aggregations aggregations = response.getAggregations();
        logger.info("按条件查询时间：" + response.getTook());
        logger.info("聚合结果：" + JSONObject.toJSONString(aggregations.getAsMap().get("maxAge")));
    }

    /**
     * 分组查询
     */
    @Test
    public void test_groupingQuery() throws IOException {
        SearchRequest request = new SearchRequest(INDEX_NAME);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //分组条件
        TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders.terms("sexGroup").field("sex");
        ValueCountAggregationBuilder valueCountAggregationBuilder = AggregationBuilders.count("nameCount").field("name");
        termsAggregationBuilder.subAggregation(valueCountAggregationBuilder);
        searchSourceBuilder.aggregation(termsAggregationBuilder);
        
        request.source(searchSourceBuilder);
        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);

        //分组在es中是分桶
        ParsedStringTerms termsName = response.getAggregations().get("sexGroup");
        List<? extends Terms.Bucket> buckets = termsName.getBuckets();
        buckets.forEach(naem -> {
            String key = (String) naem.getKey();
            ParsedValueCount countName = naem.getAggregations().get("nameCount");
            double value = countName.value();
            logger.info("name , count {} {}", key, value);
        });
    }
}
