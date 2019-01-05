## Springboot整合redis

### 一、 SpringBoot项目引入redis依赖jar
<!--SpringBoot接入redis依赖 + jedis支持的redis客户端-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
<dependency>
    <groupId>redis.clients</groupId>
    <artifactId>jedis</artifactId>
    <version>2.9.0</version>
</dependency>
ps:若使用spring-boot-starter-redis需要引入版本号



### 二、在application.properties中设置redis连接属性
SpringBoot支持直接设置redis配置类的属性org.springframework.boot.autoconfigure.data.redis.RedisProperties

通用的连接属性在application.properties中设置：
```
# REDIS (RedisProperties)-redis通用配置
# Redis数据库索引（默认为0）
spring.redis.database=0
# Redis服务器地址-在各环境配置文件中设置
#spring.redis.host=localhost
# Redis服务器连接端口
#spring.redis.port=6379
# Redis服务器连接密码（默认为空）
spring.redis.password=
# 连接池最大连接数（使用负值表示没有限制）
spring.redis.jedis.pool.max-active=500
# 连接池最大阻塞等待时间（使用负值表示没有限制）
spring.redis.jedis.pool.max-wait=6000ms
# 连接池中的最大空闲连接
spring.redis.jedis.pool.max-idle=1000
# 连接池中的最小空闲连接
spring.redis.jedis.pool.min-idle=4
# 连接超时时间（毫秒）
spring.redis.timeout=5000
host和port根据各环境分别配置在dev、staging、prd的properties中

# REDIS  ip+port配置
spring.redis.host=127.0.0.1
spring.redis.port=19000
```
### 三、BaseRedisClient实现类
从网上找了比较全的RedisService，并整理了一下，用的RedisTemplate从配置文件自动读取连接属性。
```java
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis client
 *
 * @author qiyu
 * @date 2018-12-13 20:09
 */

@Service
@SuppressWarnings("unchecked")
public class BaseRedisClient {
    private static final Logger logger = LoggerFactory.getLogger(BaseRedisClient.class);

    @Autowired
    private RedisTemplate redisTemplate;

    private Gson gson = new Gso'n();

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value) {
        logger.info("set redis key:{}, value:{}", key, gson.toJson(value));

        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存设置时效时间
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value, Long expireTime) {
        logger.info("set redis key:{}, value:{}, expireTime:{}", key, gson.toJson(value), expireTime);

        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 批量删除对应的value
     *
     * @param keys
     */
    public void remove(final String... keys) {
        logger.info("remove redis keys-values:{}", gson.toJson(keys));

        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 批量删除key
     *
     * @param pattern
     */
    public void removePattern(final String pattern) {
        logger.info("remove redis keys:{}", pattern);

        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0) {
            redisTemplate.delete(keys);
        }
    }

    /**
     * 删除对应的value
     *
     * @param key
     */
    public void remove(final String key) {
        logger.info("remove redis key-value:{}", key);

        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    public Object get(final String key) {
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        Object value = operations.get(key);
        logger.info("get redis key:{}, value:{}", key, gson.toJson(value));
        return value;
    }

    /**
     * 哈希 添加
     *
     * @param key
     * @param hashKey
     * @param value
     */
    public void hmSet(String key, Object hashKey, Object value) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        hash.put(key, hashKey, value);
    }

    /**
     * 哈希获取数据
     *
     * @param key
     * @param hashKey
     * @return
     */
    public Object hmGet(String key, Object hashKey) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        return hash.get(key, hashKey);
    }

    /**
     * 列表添加
     *
     * @param k
     * @param v
     */
    public void lPush(String k, Object v) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        list.rightPush(k, v);
    }

    /**
     * 列表获取
     *
     * @param k
     * @param l
     * @param l1
     * @return
     */
    public List<Object> lRange(String k, long l, long l1) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        return list.range(k, l, l1);
    }

    /**
     * 集合添加
     *
     * @param key
     * @param value
     */
    public void add(String key, Object value) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        set.add(key, value);
    }

    /**
     * 集合获取
     *
     * @param key
     * @return
     */
    public Set<Object> setMembers(String key) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        return set.members(key);
    }

    /**
     * 有序集合添加
     *
     * @param key
     * @param value
     * @param scoure
     */
    public void zAdd(String key, Object value, double scoure) {
        ZSetOperations<String, Object> zSet = redisTemplate.opsForZSet();
        zSet.add(key, value, scoure);
    }

    /**
     * 有序集合获取
     *
     * @param key
     * @param score
     * @param score1
     * @return
     */
    public Set<Object> rangeByScore(String key, double score, double score1) {
        ZSetOperations<String, Object> zSet = redisTemplate.opsForZSet();
        return zSet.rangeByScore(key, score, score1);
    }
}
```

### 四、验证功能
暂时是通过controller接口验证该功能，后续接入JUnit
```java
@Autowired
private BaseRedisClient baseRedisClient;

private Gson gson = new Gson();
/**
 * 测试BaseRedisClient
 */
@RequestMapping("/redisTest")
@ResponseBody
public String redisTest() {
    baseRedisClient.set("qiyu-redis-test", "qiyu-redis-test-value");
    String value = gson.toJson(baseRedisClient.get("qiyu-redis-test"));
    System.out.println(value);
    return value;
}
```
### 五、问题
ProductServer使用多个redis实例，如config、files、sform等，需要支撑多实例配置。

处理redis多实例的问题：
思路：如果要实现多个实例，那么每个实例的redis连接池必须单独配置一份，单个实例使用RedisTemplate，多个实例就要使用多分不同配置的RedisTemplate。

由于是SpringBoot架构，单个实例boot省去了很多配置，配置文件中配置的连接池属性能够自动注入RedisProperties类，现若用多个不同配置的RedisTemplate，需要单独定制RedisTemplate

那么：新增RedisConfig类，为每个实例配置“RedisConnectionFactory ”，并生成对应的RedisTemplate。
```java
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author qiyu
 * @date 2018-12-14 14:44
 */
@Configuration
public class RedisConfig {

    @Value("${spring.redis.database}")
    private int database;

    @Value("${spring.redis.jedis.pool.max-idle}")
    private int maxIdl;
    @Value("${spring.redis.jedis.pool.min-idle}")
    private int minIdl;
    @Value("${spring.redis.timeout}")
    private int timeout;
    @Value("${spring.redis.jedis.pool.max-active}")
    private int maxActive;

    /**
     * 多实例host&port在此处配置
     */
    @Value("${spring.redis.host.config}")
    private String hostOfConfig;
    @Value("${spring.redis.port.config}")
    private int portOfConfig;
    @Value("${spring.redis.host.sform}")
    private String hostOfSform;
    @Value("${spring.redis.port.sform}")
    private int portOfSform;

    @Value("${spring.redis.password}")
    private String passWord;

    /**
     * 需要借助@Primary来指定默认的连接工厂，否则会报错:
     * 'RedisAutoConfiguration required a single bean, but 2 were found'
     */
    @Bean
    @Primary
    public RedisConnectionFactory redisConnectionFactoryOfConfig() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(maxIdl);
        poolConfig.setMinIdle(minIdl);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);
        poolConfig.setNumTestsPerEvictionRun(10);
        poolConfig.setTimeBetweenEvictionRunsMillis(60000);
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(poolConfig);
        jedisConnectionFactory.setHostName(hostOfConfig);
        if (!passWord.isEmpty()) {
            jedisConnectionFactory.setPassword(passWord);
        }
        jedisConnectionFactory.setPort(portOfConfig);
        jedisConnectionFactory.setDatabase(database);
        return jedisConnectionFactory;
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactoryOfSform() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(maxIdl);
        poolConfig.setMinIdle(minIdl);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);
        poolConfig.setNumTestsPerEvictionRun(10);
        poolConfig.setTimeBetweenEvictionRunsMillis(60000);
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(poolConfig);
        jedisConnectionFactory.setHostName(hostOfSform);
        if (!passWord.isEmpty()) {
            jedisConnectionFactory.setPassword(passWord);
        }
        jedisConnectionFactory.setPort(portOfSform);
        jedisConnectionFactory.setDatabase(database);
        return jedisConnectionFactory;
    }

    @Bean(name = "redisTemplateOfConfig")
    public RedisTemplate<String, Object> redisTemplateOfConfig() {
        RedisTemplate<String, Object> redisTemplateObject = new RedisTemplate<String, Object>();
        redisTemplateObject.setConnectionFactory(redisConnectionFactoryOfConfig());
        setSerializer(redisTemplateObject);
        redisTemplateObject.afterPropertiesSet();
        return redisTemplateObject;
    }


    @Bean(name = "redisTemplateOfSform")
    public RedisTemplate<String, Object> redisTemplateOfSform() {
        RedisTemplate<String, Object> redisTemplateObject = new RedisTemplate<String, Object>();
        redisTemplateObject.setConnectionFactory(redisConnectionFactoryOfSform());
        setSerializer(redisTemplateObject);
        redisTemplateObject.afterPropertiesSet();
        return redisTemplateObject;
    }


    private void setSerializer(RedisTemplate<String, Object> template) {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(
                Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setKeySerializer(template.getStringSerializer());
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        //在使用String的数据结构的时候使用这个来更改序列化方式
        /*RedisSerializer<String> stringSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringSerializer );
        template.setValueSerializer(stringSerializer );
        template.setHashKeySerializer(stringSerializer );
        template.setHashValueSerializer(stringSerializer );*/
    }
}
```
更改BaseRedisClient中的方法，client类里的redis操作方法添加RedisTemplate入参，即根据对应redis实例选择使用对应的redis操作，如set方法：
```java
/**
 * 写入缓存
 *
 * @param redisTemplate create by RedisConfig
 * @see RedisConfig
 */
public boolean set(final String key, Object value, RedisTemplate redisTemplate) {
    logger.info("set redis key:{}, value:{}", key, gson.toJson(value));

    boolean result = false;
    try {
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        operations.set(key, value);
        result = true;
    } catch (Exception e) {
        e.printStackTrace();
    }
    return result;
}
```
功能验证：
debug可查看各redisTemplateOfConfig中的Connection属性分别为不同的实例host&port
```java
@Autowired
@Resource(name = "redisTemplateOfConfig")
private RedisTemplate<String,Object> redisTemplateOfConfig;

@Autowired
@Resource(name = "redisTemplateOfSform")
private RedisTemplate<String,Object> redisTemplateOfSform;
private Gson gson = new Gson();
/**
 * 测试BaseRedisClient
 */
@RequestMapping("/redisTest")
@ResponseBody
public String redisTest() {
    baseRedisClient.set("qiyu-redis-test", "qiyu-redis-test-value", redisTemplateOfConfig);
    String value1 = gson.toJson(baseRedisClient.get("qiyu-redis-test", redisTemplateOfConfig));
    System.out.println(value1);
    baseRedisClient.set("qiyu-redis-test", "qiyu-redis-test-value", redisTemplateOfSform);
    String value2 = gson.toJson(baseRedisClient.get("qiyu-redis-test", redisTemplateOfSform));
    System.out.println(value2);
    return value1;
}
```


### 遇到的问题：
在读取用户权限配置的redis（sform），拿到的value在反序列化过程出现问题：
```
org.springframework.data.redis.serializer.SerializationException: Could not read JSON: Unexpected token (START_OBJECT), expected START_ARRAY: need JSON Array to contain As.WRAPPER_ARRAY type information for class java.lang.Object
 at [Source: (byte[])"{"uids":"1110013495,1110013495,178059419,3410726,1270333625,1110013495","instanceId":"1522324378499"}"; line: 1, column: 1]; nested exception is com.fasterxml.jackson.databind.exc.MismatchedInputException: Unexpected token (START_OBJECT), expected START_ARRAY: need JSON Array to contain As.WRAPPER_ARRAY type information for class java.lang.Object
 at [Source: (byte[])"{"uids":"1110013495,1110013495,178059419,3410726,1270333625,1110013495","instanceId":"1522324378499"}"; line: 1, column: 1]
 ```
查阅一堆文档后，定位到是RedisConfig.java里面选取的序列化工具：Jackson2JsonRedisSerializer 的问题，对value为json的反序列化处理有问题。

验证后使用StringRedisSerializer 后不会有该问题。但是StringRedisSerializer只支持key value都为string对象。

目前是先换成了StringRedisSerializer 后续继续排查Jackson2JsonRedisSerializer 的坑。

看到一篇博客：https://www.cnblogs.com/niceboat/p/7284099.html

大概讲的就是objectMapper.enableDefaultTyping();这个的配置是有问题的。

去掉Jackson2JsonRedisSerializer 中配置的enableDefaultTyping()，发现问题解决了。
