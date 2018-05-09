select A.id,group_concat(B.tag) from tmc_task_incomplete A  left join tmc_order_task_tag B on A.id=B.order_task_id where 1=1 group by A.id limit 10 ;
### group_concat使用
面对查询列表需要分页，遇到一对多的表结构，使用left join会有问题<br>
所以使用group_concat(B.xxx)用于聚合A中一个id对应B中多个xxx的现象<br>
查询效果如下<br>
```
+----+---------------------+
| id | group_concat(B.tag) |
+----+---------------------+
|  2 | 10,11,12            |
|  3 | 13                  |
|  4 | 10,11,14            |
|  5 | 10                  |
|  6 | 10                  |
|  8 | NULL                |
|  9 | NULL                |
| 11 | NULL                |
| 12 | NULL                |
| 13 | NULL                |
+----+---------------------+
```
