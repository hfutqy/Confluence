一、数据结构相关
1. HashMap的结构，如何解决Hash冲突，如何扩容
2. ConcurrentHashMap如何做到并发安全的
3. HashTable、HashSet、TreeSet
4. 一棵二叉查找树如果满足下面的红黑性质，则为一棵红黑树：
1) 每个结点是或是红的，或是黑的。
2) 根结点是黑的。
3) 每个叶结点（nil[T]）是黑的。
4) 如果一个结点是红的，那么它的两个儿子是黑的。
5) 对每个结点，从该结点到其子孙结点的所有路径上包含相同数目的黑结点。


二、 多线程
1. 多线程的几种实现方式，Runnable接口，Thread类，Callable接口，Furture
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
2. 事物隔离级别，Read-uncommitted,Read-committed,repeatable-read,
serializable。后三者依次解决了脏读、不可重复读、幻读的问题。如何理解幻读？
3. ACID，原子性（Atomicity，要么执行要么不执行，原子操作）、一致性（Consistency，写ok后读到写ok的值，保持一致）、隔离性（Isolation,这就是隔离级别了）、持久性（Durability，操作会落地到磁盘，不会丢失）

五、redis

六、mongo

七、kafka、zookeeper





X、拓展
1. 实现全局流水唯一id（mysql乐观锁，redis队列）
