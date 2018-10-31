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





G: 类加载
JDK 默认提供了如下几种ClassLoader
BootStrp ClassLoader 是C++写的;它是在Java虚拟机启动后初始化的
负责加载%JAVA_HOME%/jre/lib,-Xbootclasspath参数指定的路径以及%JAVA_HOME%/jre/classes中的类













你好，我是xx；去年（2017）毕业于合肥工业大学，软件工程专业；
目前就职于 南京途牛科技有限公司，一家互联网旅游企业，工作经验一年多了。
职位是java开发工程师，主要负责公司供应商管理系统、导游管理系统等研发工作。
就职期间，经历过多次大项目上线经历（人天>300），对项目建设、管理流程有一定的经验。
个人在公司表现还不错，也拿过奖励和公示表扬。
平时自己也对技术比较感兴趣，有时候遇到问题也会翻翻源码去弄清楚。
对新技术也有所关注，没事也喜欢翻翻技术博客提升自己。
个人快速学习能力还不错，之前有个php项目需要；快速学习上手。
目前感觉个人技术发展遇到了瓶颈，公司整体技术氛围比较冷清，想到新的环境去更好的成长，能多和大佬交流。
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
Spring AOP 切面；如何@aspect 切点aspect point、after、before
原理：动态代理--反射 （jdk动态代理、cglib 一个是基于接口的一个是基于类的☆）
【Redis】

【AMQ】
生产者：spring支持的JmsTemplate；设置优先级0-9、推送方式、消息存活时间、
消费者：实现MessageListener接口，有onMessage方法
配置：JmsTemplate
