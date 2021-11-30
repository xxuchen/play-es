### Spring Boot 与 ES 版本对应关系



|                  Spring Data Release Train                   |                  Spring Data Elasticsearch                   | Elasticsearch |                       Spring Framework                       |                         Spring Boot                          |
| :----------------------------------------------------------: | :----------------------------------------------------------: | :-----------: | :----------------------------------------------------------: | :----------------------------------------------------------: |
| 2021.1 (Q)[[1](https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/#_footnotedef_1)] | 4.3.x[[1](https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/#_footnotedef_1)] |    7.15.2     | 5.3.x[[1](https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/#_footnotedef_1)] | 2.5 .x[[1](https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/#_footnotedef_1)] |
|                       2021.0 (Pascal)                        |                            4.2.x                             |    7.12.0     |                            5.3.x                             |                            2.5.x                             |
|                       2020.0 (Ockham)                        |                            4.1.x                             |     7.9.3     |                            5.3.2                             |                            2.4.x                             |
|                           Neumann                            |                            4.0.x                             |     7.6.2     |                            5.2.12                            |                            2.3.x                             |
|                            Moore                             |                            3.2.x                             |    6.8.12     |                            5.2.12                            |                            2.2.x                             |
| Lovelace[[2](https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/#_footnotedef_2)] | 3.1.x[[2](https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/#_footnotedef_2)] |     6.2.2     |                            5.1.19                            |                            2.1.x                             |
| Kay[[2](https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/#_footnotedef_2)] | 3.0.x[[2](https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/#_footnotedef_2)] |     5.5.0     |                            5.0.13                            |                            2.0.x                             |
| Ingalls[[2](https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/#_footnotedef_2)] | 2.1.x[[2](https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/#_footnotedef_2)] |     2.4.0     |                            4.3.25                            |                            1.5.x                             |

原来spring-data-elasticsearch中的【TransportClient】将在 Elasticsearch 7 中【deprecated】，在 Elasticsearch 8 弃用。

spring-data 官方强烈推荐使用 High Level REST Client 替代 TransportClient 。

本工程将演示使用RestHighLevelClient与SpringData集成操作ES。

当RestHighLevelClient通过配置类完成注入的时候，spring-data-elasticsearch 将自动完成 ElasticsearchRestTemplate 的注入。可以在代码中直接使用：

```java
@Autowired
private RestHighLevelClient elasticsearchClient;
```

```java
@Autowired
private ElasticsearchRestTemplate elasticsearchRestTemplate;
```



  
