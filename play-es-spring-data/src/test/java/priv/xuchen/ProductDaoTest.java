package priv.xuchen;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import priv.xuchen.base.TestAppBase;
import priv.xuchen.dao.ProductDao;
import priv.xuchen.entity.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Spring Data Elasticsearch 方式的 ProductDao 数据操作测试
 *
 * @version 1.0
 * @author: xuchen
 * @date: 2021/11/30 22:07
 */
public class ProductDaoTest extends TestAppBase {

    @Autowired
    ProductDao productDao;

    /**
     * 保存文档
     */
    @Test
    public void test_save() {
        Product product = new Product();
        product.setId(1L);
        product.setTitle("Elasticsearch源码解析与优化实战(博文视点出品)");
        product.setCategory("图文书籍");
        product.setPrice(89D);
        product.setImages("https://img30.360buyimg.com/vc/jfs/t1/9163/39/7631/761898/5c064783Ee19e75f0/85370342d50eb88a.jpg");
        productDao.save(product);
    }

    /**
     * 修改文档
     */
    @Test
    public void test_update() {
        Product product = new Product();
        //相同的ID会直接被更新
        product.setId(1L); 
        product.setTitle("Elasticsearch源码解析与优化实战(博文视点出品)");
        product.setCategory("图文");
        product.setPrice(89D);
        product.setImages("https://img30.360buyimg.com/vc/jfs/t1/9163/39/7631/761898/5c064783Ee19e75f0/85370342d50eb88a.jpg");
        productDao.save(product);
    }

    /**
     * 按ID查询
     */
    @Test
    public void test_findById() {
        Product product = productDao.findById(1L).get();
        System.out.println(product.toString());
    }

    /**
     * 查询所有
     */
    @Test
    public void test_findAll() {
        Iterable<Product> all = productDao.findAll();
        for (Product p : all) {
            System.out.println(p);
        }
    }

    /**
     * 按ID删除
     */
    @Test
    public void test_deleteById() {
        productDao.deleteById(1L);
    }

    /**
     * 批量保存
     */
    @Test
    public void test_saveAll() {
        List<Product> productList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Product product = new Product();
            product.setId(0L + i);
            product.setTitle("Elasticsearch源码解析与优化实战(博文视点出品)" + i);
            product.setCategory("图文");
            product.setPrice(89D);
            product.setImages("https://img30.360buyimg.com/vc/jfs/t1/9163/39/7631/761898/5c064783Ee19e75f0/85370342d50eb88a.jpg");
            productList.add(product);
        }
        productDao.saveAll(productList);
    }

    /**
     * 分页查询
     */
    @Test
    public void test_findByPageable() {
        // 排序方式
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        
        // 设置分页，第1页是从0开始的
        int page = 1; 
        int size = 5;
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        
        Page<Product> productPage = productDao.findAll(pageRequest);
        for (Product product : productPage.getContent()) {
            System.out.println(product.toString());
        }
    }
    
    
    
}
