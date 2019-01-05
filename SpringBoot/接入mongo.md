
### 一、pom文件引入依赖
和邮件、redis很相似，SpringBoot 项目对接入mongoDB也集成好了一套架子，可以直接接入使用

<!-- mongo -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-mongodb</artifactId>
</dependency>


### 二、 修改配置文件
接入mongoDB需要指定mongo服务器的host:port + username:password +dbname

在application-development.properties（各环境独立配置ip:port）文件中加上：
```
#mongoDB uri mongodb://[username:password@]host1[:port1][,host2[:port2],...[,hostN[:portN]]][/[database][?options]]
spring.data.mongodb.uri=mongodb://xxx:xxx@host:port,xxx
spring.data.mongodb.database=label
spring.data.mongodb.authentication-database=admin
```
其中，spring.data.mongodb.database需要独立设置。只在uri中设置db，会如下报错：

com.mongodb.MongoSecurityException: Exception authenticating MongoCredential{mechanism=null, userName='sys_admin', source='label', password=<hidden>, mechanismProperties={}}
网上查了下，参考了这批讨论的接口，发现不会出现以上问题：https://segmentfault.com/q/1010000012110119



### 三、 使用MongoTemplate实现mongo操作client
新建BaseMongoClient.java 自定义各种mongo操作方法
```java
import com.mongodb.client.model.geojson.Polygon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * mongodb client
 *
 * @author qiyu
 * @date 2018-12-24 18:57
 */
@Service
public class BaseMongoClient {

    @Autowired
    private MongoTemplate mongoTemplate;


    public <T> List<T> find(Query query, Class<T> entityClass) {
        return mongoTemplate.find(query, entityClass);
    }

    public <T> T findById(Class<T> entityClass, String id) {
        return mongoTemplate.findById(id, entityClass);
    }

    public <T> T findOne(Query query, Class<T> entityClass) {
        return mongoTemplate.findOne(query, entityClass);
    }

    public <T> List<T> findAll(Class<T> entityClass) {
        return mongoTemplate.findAll(entityClass);
    }


    public void save(Polygon polygon) {
        mongoTemplate.save(polygon);
    }


    public <T> void saveOrUpdate(T entity) {
        mongoTemplate.save(entity);
    }

    public <T> void remove(T entity) {
        mongoTemplate.remove(entity);
    }

    public <T> void add(T entity) {
        mongoTemplate.insert(entity);
    }

    public <T> void addAll(List<T> entity) {
        mongoTemplate.insertAll(entity);
    }

}

```


### 四、 使用BaseMongoClient
mongodb的存储对象需要在java中创建映射的实例，label集合映射的java bean如下：

其中的注解：
```
@Document(collection="label_relation")指定了mongo中label数据库下的“label_relation”集合

@Id 为mongo文档的唯一标识，在mongodb中为ObjectId

@Field(value = "item_id") 指向mongodb中存储的数据结构中的字段名，不指定默认为当前bean中的字段（javabean中用驼峰，mongo中使用下划线，所以还是需要指定下）
```

```
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author qiyu
 * @date 2018-12-24 19:33
 */
@Document(collection="label_relation")
public class LabelRelationEntity {

    @Id
    private String id;

    @Field(value = "item_id")
    private Long itemId;

    @Field(value = "item_type")
    private Integer itemType;

    @Field(value = "labels")
    private Long[] labels;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    public Long[] getLabels() {
        return labels;
    }

    public void setLabels(Long[] labels) {
        this.labels = labels;
    }
}
```
验证一下：

在TestController中实现了一个查询的方法。

mongoDB由于是NoSQL，所以只能根据已有的索引进行匹配查询，参考productServer的逻辑，使用item_id和item_type的索引，构建Query对象设置查询条件。
```java
@Autowired
private BaseMongoClient baseMongoClient;


/**
 * mongodb
 */
@RequestMapping("/mongodbTest")
@ResponseBody
public String mongodbTest() {
    Query query = new Query(Criteria.where("item_id").is(100L).and("item_type").is(1));
    return gson.toJson(baseMongoClient.find(query, LabelRelation.class));
}
```
使用postman测试，能够正常获取mongodb中的数据：
```
[{"id":"5bb06ec36df38d96f9f87aef","itemId":100,"itemType":1,"labels":[235,180,99,104,105,106,238,239]}]
```
