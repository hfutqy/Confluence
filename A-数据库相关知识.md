### 删除表数据

- 正确的用法：
- 当你不再需要该表时， 用 drop；当你仍要保留该表，但要删除所有记录时， 用 truncate；当你要删除部分记录时（always with a WHERE clause), 用 delete其中使用truncate，例如：truncate table user;
##### truncate使用注意事项
TRUNCATE TABLE 在功能上与不带 WHERE 子句的 DELETE 语句相同：二者均删除表中的全部行。但 TRUNCATE TABLE 比 DELETE 速度快，且使用的系统和事务日志资源少。<br>
如果有ROLLBACK语句，DELETE操作将被撤销，但TRUNCATE不会撤销。<br>
效率上：drop > truncate > delete


### MySql分解关联查询优点
1. 可以复用应用缓存、mysql查询缓存
  应用缓存：程序实现的缓存，粒度细了便于复用，减少sql插叙数
  mysql查询缓存：表若发生变化则无法再用查询缓存，拆分后某些表很少变就可以用上这些表的查询缓存。
2. 查询分解后，可以减少单个查询锁表的情况。减少锁竞争。
3. 应用层做关联，解耦，提高单个service/mapper的复用。高可扩展。
4. 分解查询的做法，相当于在应用中实现了哈希关联，而不是mysql嵌套循环关联，某些场景哈希关联效率要高很多。【哈希关联？set?减少冗余查询】


### 大数据表新增字段思路
问题：大数据表新增字段时会出现锁表的操作，导致该表一直无法被访问，很有可能导致服务器崩溃等情况<br>
解决方案：
那么，给 MySQL 大表加字段的思路如下：

① 创建一个临时的新表，首先复制旧表的结构（包含索引）

```
create table new_table like old_table;
```
② 给新表加上新增的字段
```
ALTER TABLE new_table ADD title(255) DEFAULT '' COMMENT '标题' AFTER id;
```
③ 把旧表的数据复制过来
```
insert into new_table(filed1,filed2…) select filed1,filed2,… from old_table
```
④ 删除旧表，重命名新表的名字为旧表的名字

不过这里需要注意，执行第三步的时候，可能这个过程也需要时间，这个时候有新的数据进来，所以原来的表如果有字段记录了数据的写入时间就最好了，可以找到执行这一步操作之后的数据，并重复导入到新表，直到数据差异很小。不过还是会可能损失极少量的数据。

所以，如果表的数据特别大，同时又要保证数据完整，最好停机操作。

另外的方法：

1.在从库进行加字段操作，然后主从切换

2.使用第三方在线改字段的工具

一般情况下，十几万的数据量，可以直接进行加字段操作。
### 建立主从库的优势
主从数据库的建立一般基于以下三个方面考虑：<br>
1、容灾：**备库在异地**，主库不存在了，备库可以立即接管，无须恢复时间<br>
2、**负载均衡**：**主库做增删改，备库做查询**，这样很多查询业务不占用主库资源<br>
3、**数据集中和分发**：此种模式主要用于数据从分公司集中到总公司，或从总公司分发到分公司，前提是公司需要同步的数据很少，另外各公司间业务系统不是同一家公司开发的<br>
同步功能主要通过数据库同步软件实现的，象ORACLE的DATAGUARD、QUEST的SHAREPLEX、沃信科技的PAC、ORACLE的GOLDEN GATE、迪思杰的REALSYNC


### ON DUPLICATE KEY UPDATE
mysql骚操作之insert重复主键or唯一索引，走更新而不是update
比如表：
id   name
1    aaa
2    bbb
3    ccc
此时的需求场景是我要insert一堆数据，但是insert的数据中有id字段，且id字段有可能和即存的id重复。
sql如下
```sql
insert into mytb (id, name)
values (1, 'xxx1'),(2, 'xxx2'),(3, 'xxx3')
ON DUPLICATE KEY UPDATE name=values(name);
```
主要是这个ON DUPLICATE KEY UPDATE
可以在主键or唯一索引重复的时候走更新动作。
