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

双亲委派模式的工作原理的是;如果一个类加载器收到了类加载请求，它并不会自己先去加载，而是把这个请求委托给父类的加载器去执行，如果父类加载器还存在其父类加载器，则进一步向上委托，依次递归，请求最终将到达顶层的启动类加载器，如果父类加载器可以完成类加载任务，就成功返回，倘若父类加载器无法完成此加载任务，子加载器才会尝试自己去加载，这就是双亲委派模式，即每个儿子都不愿意干活，每次有活就丢给父亲去干，直到父亲说这件事我也干不了时，儿子自己想办法去完成，这不就是传说中的双亲委派模式.那么这种模式有什么作用呢?

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
3.implements Callable 能拿到结果，返回自己定义的；有Exception
4.Future future.get(Time)
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

----
小米面试
很难、很多、很长时间
设计模式：手写单例（别忘了私有构造函数）/什么是门面模式
hashmap的原理
多线程，了解cocurrent包吗？了解executor接口吗
线程死锁了解吗？怎么处理
JVM垃圾回收
mysql的事务隔离，分哪几层
说一下AOP的实现原理吧，AspectJ包下实现@Aspect注解和xml配置
IOC和DI实际是两个过程；IOC是实力化对象，在启动的时候就会触发；但是DI是在getBean的时候才用到；
Redis的持久化，默认使用哪种回滚
AMQ---点对点还是发布订阅；接收的服务器有几个，1个是点对点n个是发布订阅

多线程死锁的产生原因--
A线程持有x资源想继续持有y，等待y资源空闲
B线程持有y资源想继续持有x，等待x资源空闲
这样就是互相等待，互相不放手自己的资源，就死锁了。
解决方式：设置资源等待时长object.wait(long time)，还有杀掉某线程，

小米一面
自旋锁和互斥锁
都是对资源占用不释放的锁
自旋是B线程等待时候自己for循环，不断请求
互斥是B线程睡眠，等待释放资源后唤醒。

算法题：不定长数组，数据不重复，有且仅有两个元素的和为target，求这两个元素的下标
时间复杂为On;;;;;我只实现了N*logN
很多：
数据结构：链表、HashMap
io  nio和bio，tomcat用的哪个
mysql 索引，结构，事务隔离机制，详细描述；为什么幻读读不到新增的数据？
说下锁知识，怎么解决死锁，你还了解哪些锁，自选可重入互斥锁等
聊聊AOP。聊聊IOC和DI的关系
Redis源码看过没？怎么持久化，有哪些方式；我们默认用哪个方式
AMQ？还知道别的mq吗？消息队列有哪些方式（点对点、发布订阅）
多线程的实现方式，区别？你所知道的线程池？
concurret包看过没，知道里面哪些实现类；atomic包知道吗

问了很多底层原理的实现。
二面：
基本聊聊项目
springBoot，详细说说你了解的
垃圾回收
linux 指令查看java程序，查看个数
AOP说一说
最后手写啊
A.链表查询是否有环  B.快排
最后期望薪资啊、现在薪资

三面：
说下完全二叉树
我要获取这个完全二叉树最后一个叶子节点，给出遍历代码（手写+敲代码）
说下带static的内部类 和 不带static的内部类的区别
实例创建outClass.new InnerClass()/ new InnerClass()
说一下锁知识
Class A{
  synchronized a(){...};
  synchronized b(){...};
}
两个线程AB分别访问ab方法会互斥吗？如果是，怎么能不互斥
java四个作用域关键字，详细描述default
说下hashmap
说下jvm内存gc过程
说下计数器的作用
jvm的线程和栈是什么关系，一个线程能持有多个栈空间吗
线程创建的方式，优缺点
你读过jdk和spring之外的源码吗
你读过哪些技术相关书，印象深刻的是？
有抗压能力吗？
为什么离职？

四面：
TCP/UDP的区别
TCP------传输控制协议
面向连接   
可靠的    
传输大量数据
慢      
UDP------用户数据报协议
非面向连接（多对多）
非可靠的
传输少量数据
快
https加密协议，怎么握手的呢？
一：https握手大致过程
建立服务器443端口连接
SSL握手：随机数，证书，密钥，加密算法
发送加密请求
发送加密响应
关闭SSL
关闭TCP
排查网页慢加载
线上接口慢如何排查（不重启）
mysql的读锁
innodb的间隙锁
索引，最左原则
事务隔离
jvm内存模型
gc算法


抽象类和接口的区别
抽象类：
抽象方法，只有行为的概念，没有具体的行为实现。使用abstract关键字修饰，没有方法体。子类必须重写这些抽象方法。
包含抽象方法的类，一定是抽象类。
抽象类只能被继承，一个类只能继承一个抽象类。
接口：
全部的方法都是抽象方法，属型都是常量。
不能实例化，可以定义变量。
接口变量可以引用具体实现类的实例
接口只能被实现，一个具体类实现接口，必须实现全部的抽象方法
接口之间可以多实现，一个具体类可以实现多个接口，实现多继承现象


垃圾回收可以被当做root的：
被启动类（bootstrap 加载器）加载的类和创建的对象
JavaStack 中的引用的对象 (栈内存中引用的对象)；
方法区中静态引用指向的对象；
方法区中常量引用指向的对象；
Native 方法中 JNI 引用的对象。

【虚拟机栈中引用的对象、方法区类静态属性引用的对象、方法区常量池引用的对象、本地方法栈JNI引用的对象】
