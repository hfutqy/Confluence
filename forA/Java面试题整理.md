一、数据结构相关
1. HashMap的结构，如何解决Hash冲突，如何扩容
2. ConcurrentHashMap如何做到并发安全的
3. HashTable、HashSet、TreeSet



二、 多线程
1. 多线程的几种实现方式，Runnable接口，Thread类，Callable，Furture
2. 线程池的实现方式，executor接口，executors实现类
newFixedThreadPool(),newSingleThreadExecutor(),newCachedThreadPool()等
3. ThreadLocal对象，既是全局入库可见，又是线程安全的，对每个线程存储的数据都是各自独立的。可能有内存泄漏，key会消失，value既是处于游离态。


三、设计模式
1. 单例，注意构造函数要私有，对象要静态的，有懒汉、饿汉、volatile+双重检查锁，静态内部类多种实现方式。（除了饿汉都是延迟加载）
2. 门面模式
3. 观察者模式
4. 代理模式

四、mysql
1. 索引，B+树，联合索引，索引的设计
2. 事物隔离级别，Read-uncommitted,Read-committed,repeatable-read,serializable。后三者解决了脏读、不可重复读、幻读的问题。


五、redis





六、拓展
1. 实现全局流水唯一id（mysql乐观锁，redis队列）
