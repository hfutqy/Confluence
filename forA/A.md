A.Spring相关
1.Spring事务、 Bean的生命周期，Bean的初始化、销毁过程
《Spring in Action》
2.Spring IOC 注入，怎么注入的？注入方式-a.XML里面写、b.@value c.@Service 和@Resource
3.Spring AOP 切面；如何@aspect 切点aspect point、after、before
原理：动态代理--反射 （jdk动态代理、cglib 一个是基于接口的一个是基于类的☆）

B. 数据库
数据库事务  begin; commit;  acid事务的四大特性
原子性、一致性、隔离性、持久性
TransAction
索引：单一索引(唯一索引、主键索引)、组合索引(最左原则，where里面有组合索引从左到右的索引)、


C.http / tcp/ip
1xx  (临时响应)
Status 2xx  (成功)
4xx (请求错误)
5xx （服务器错误）
405 method error\
400 请求无效 (Bad request
504 url不对
http请求是什么:是指从客户端到服务器端的请求消息,request请求、response响应的
request和response里的属性
特点：无状态的

D.java 多线程、线程池
a:线程池
参数：1.最大线程数m 2.核心线程数n n是永久激活的，n《m的，当核心线程数不够了会n+1 and m-1；  3.非核心的线程的alive time  4.
超出最大线程数会xxx
拒绝策略： 哪几种？RejectedExecutionHandler
1.线程调用运行该任务的 execute 本身
2.处理程序遭到拒绝将抛出运行时RejectedExecutionException
3.不能执行的任务将被删除
4.如果执行程序尚未关闭，则位于工作队列头部的任务将被删除，然后重试执行程序（如果再次失败，则重复此过程）
几种常见的线程池；用Excutors.newXXX()
1.newFixedThreadPool 指定数目
2.newSingleThreadExecutor 单线程持久化
3.newScheduledThreadPool  定期执行周期执行
4.newCachedThreadPool  可根据需要创建新线程的线程池



b.多线程
1.implements runnable   ---run()
2.extends Thread --- new Thread(runnable).start;
3.implements Callable 能拿到结果，返回自己定义的
4.Feature feature.get(Time)


E. 数据结构
HashMap--LinkedHashMap--ConcurrentHashMap
HashTable
HashSet
List-ArrayList-LinkedList
Iterator
排序、快排、堆排、合并排
树--二叉树 B树 B- 、B+树 文件索引、mysql索引


F: JVM内存模型
堆的分区--年轻代、老年代
年轻的 8:1:1   monitor.gc    
老年代--      full.gc   超大对象会直接进入老年代




G: 类加载
JDK 默认提供了如下几种ClassLoader
1. BootStrp ClassLoader 是C++写的;它是在Java虚拟机启动后初始化的
负责加载%JAVA_HOME%/jre/lib,-Xbootclasspath参数指定的路径以及%JAVA_HOME%/jre/classes中的类
2. ExtClassLoader
Bootstrp loader加载ExtClassLoader,并且将ExtClassLoader的父加载器设置为Bootstrp loader
加载%JAVA_HOME%/jre/lib/ext，此路径下的所有classes目录以及java.ext.dirs系统变量指定的路径中类库。
3. AppClassLoader
Bootstrp loader加载完ExtClassLoader后，就会加载AppClassLoader,并且将AppClassLoader的父加载器指定为 ExtClassLoader。


面试经历：
【线程池】
参数：1.最大线程数m 2.核心线程数n n是永久激活的；
3queueCapacity：任务队列容量（阻塞队列）  核心线程数达到最大时，新任务会放在队列中排队等待执行
4.非核心的线程的alive time；线程空闲时间；达到keepAliveTime时，线程会退出直到活跃线程数=corePoolSize  
超出最大线程数会
【拒绝策略】哪几种？RejectedExecutionHandler；怎么拒绝的or拒绝处理方式
1.线程调用运行该任务的 execute 本身
2.处理程序遭到拒绝将抛出运行时RejectedExecutionException
3.不能执行的任务将被删除
4.如果执行程序尚未关闭，则位于工作队列头部的任务将被删除，然后重试执行程序（如果再次失败，则重复此过程）
【常见的线程池】；用Excutors.newXXX()
1.newFixedThreadPool 指定数目
2.newSingleThreadExecutor 单线程持久化
3.newScheduledThreadPool  定期执行周期执行
4.newCachedThreadPool  可根据需要创建新线程的线程池
【多线程】
1.implements runnable   ---run()
2.extends Thread --- new Thread(runnable).start;
3.implements Callable 能拿到结果，返回自己定义的
4.Feature feature.get(Time)
【JVm】堆栈
【数据库】事务ACID 原子性、一致性、隔离性、持久性
索引：单一索引(唯一索引、主键索引)、组合索引(最左原则，where里面有组合索引从左到右的索引)、
【Spring】
Spring IOC 注入，怎么注入的？注入方式-a.XML里面写、b.@value c.@Service 和@Resource
implements InitializingBean
在XML里面定义\<bean id=.. class=.. init-method="init">
Spring AOP 切面；如何@aspect 切点aspect point、after、before
原理：动态代理--反射 （jdk动态代理、cglib 一个是基于接口的一个是基于类的☆）
【Redis】

【AMQ】
生产者：spring支持的JmsTemplate；设置优先级0-9、推送方式、消息存活时间、
消费者：实现MessageListener接口，有onMessage方法
配置：JmsTemplate

----
2018/10/31 youzan
印象深刻的项目经验，设计的不错的点

jvm   new对象进入老年代 超大的对象会直接进入老年代
分为eden survivor(From/To) Old
先进入eden区，经历minor GC后，仍然存活 会被挪到survivor区;
survivor 区每熬过一次minor GC后，加一岁 达到一定程度会挪到Old区
年轻代基本都是朝生夕死(80%以上),所以用复制算法，这很快
gc开始的时候所有对象只会存在eden和survivor的form区，survivor的to是空的; GC后，eden的所有存活对象会复制到survivor的to; from区则根据年龄判断是到Old区还是to区。 然后eden和from就空了；

组合索引
explain 组合索引是什么样
aop
事务    事务级别
多线程、线程池  属性，队列作用，拒绝策略
hashmap..concunrrentHashmap  hashmap扩容过程

redis 持久化过程
堆 垃圾回收

----
蚂蚁面试-11.5
1.项目经历-合同项目-按位存储、process处理器、乐观锁获取合同编号
2.事务的隔离性-脏读读未提交-不可重复读反复读不一致，读已提交--幻读--局部和整体--串行
3.设计模式--模板方法、观察者模式？
4.HashMap实现、线程安全吗
5.volatile和synchronized
6.集群--多实例可以访问一个应用吗
7.乐观锁、悲观锁
8.mysql存储引擎myisam和innodb区别，索引的数据结构、为什么用b+树
myisam是不完全回滚 默认锁表  不支持外键 低并发、查询快
innodb 是完全回滚   默认锁行  支持外键   高并发推荐innodb，效率高
9.abstract类和接口的区别
抽象类是用来捕捉子类的通用特性的 。它不能被实例化，只能被用作子类的超类。抽象类是被用来创建继承层级里子类的模板。以JDK中的GenericServlet为例：[public abstract class GenericServlet implements Servlet]

10.redis支持的数据类型、持久化的方式
11.谈谈我没问到但是你有的优点
