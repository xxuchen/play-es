package priv.xuchen.dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import priv.xuchen.entity.Product;

/**
 * ProductDao
 *
 * @version 1.0
 * @author: xuchen
 * @date: 2021/11/29 22:15
 */
@Repository
public interface ProductDao extends ElasticsearchRepository<Product, Long> {

    
}
