# 工程目的
使用RestHighLevelClient与SpringData集成操作ES  

# 工程结构

# 注意
RestHighLevelClient通过配置文件完成注入的时候，则于使用spring-boot-starter-data-elasticsearch，所以ElasticsearchRestTemplate也同时完成的注入。  

### Spring Boot 与 ES版本对应关系



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

