package priv.xuchen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * 商品实体类
 *
 * @version 1.0
 * @author: xuchen
 * @date: 2021/11/29 21:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(indexName = "product", shards = 3, replicas = 1)
public class Product {

    @Id
    private Long id;
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String title;
    @Field(type = FieldType.Keyword)
    private String category;
    @Field(type = FieldType.Double)
    private String price;
    //该字段不分词（FieldType.Keyword），不作为索引关联（index = false）
    @Field(type = FieldType.Keyword, index = false)
    private String images;
    
}
