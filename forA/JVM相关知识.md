### JVM相关知识
http://youzhixueyuan.com/jvm-classic-interview-questions-and-answers.html
jdk小工具：
https://blog.csdn.net/liuxinghao/article/details/70805900
jvm调优
https://blog.csdn.net/sun1021873926/article/details/78002118
jvm知识点
http://www.importnew.com/23792.html
深入理解java虚拟机知识点
http://www.cnblogs.com/prayers/p/5515245.html
#### 堆
A. 存放对象实例；基本上对象实例都从这里分配内存。
  - 堆内存大小，最低-Xms(默认内存1/64)，最高-Xmx(默认内存1/4)
  - 默认空余堆内存<40%时会自动增大堆内存分配，直至-Xmx设定的大小。这个比例是可设置的，用-XX:MinHeapFreeRatio(最小堆内存空余比)
  - 默认空余堆内存>70%时会自动减少堆内存分配，直至-Xms设定的大小。这个比例也是可设置的，用-XX:MaxHeapFreeRatio
B. 堆里的对象是线程不安全的，他说公开的内存空间。

#### 栈（虚拟机栈）每个线程独有一个栈空间
A. 存放对象的引用 和 基本数据类型的变量
B. 栈是线程安全的。
C. 虚拟机栈描述的是java方法执行的内存模型，每个java方法被执行都会同时创建 栈帧（Stack Frame）用于存储局部变量表、动态链接、方法出口等信息。本地方法栈则为虚拟机使用到的native方法服务。

#### 方法区
A. 存放运行时的常量池。
B. 存储已被虚拟机加载的类的信息、常量、静态变量、及时编译器编译后的代码等数据。

#### 本地方法栈
和虚拟机栈很相似，但是是处理native方法的一块内存区域


#### 程序计数器
当前线程所执行的字节码行号 指示器（只会记录java方法的，native方法不会记录）

#### 直接内存
放缓冲区的信息，IO数据啊这些

### 总结
栈：线程私有，使用一段连续的内存空间；存放局部变量表、操作栈、动态链接、方法出口；使用-XSs配置；异常信息有StackOverflow、OutOfMemoryError

堆：线程共享，什么周期与虚拟机相同；保存对象的实例；使用-Xmx、-Xms、-Xmn配置；异常信息有OutOfMemoryError

程序计数器： 线程私有，占用内存小；用来存放字节码行号；

方法区：线程共享；存储类加载信息、常量、静态变量等；使用-XX:PermSize、-XX:MaxPermSize；异常信息有OutOfMemoryError

### JVM垃圾回收算法
1. 标记-清除: 这是垃圾收集算法中最基础的，根据名字就可以知道，它的思想就是标记哪些要被回收的对象，然后统一回收。这种方法很简单，但是会有两个主要问题：1.效率不高，标记和清除的效率都很低；2.会产生大量不连续的内存碎片，导致以后程序在分配较大的对象时，由于没有充足的连续内存而提前触发一次GC动作。


2. 复制算法: 为了解决效率问题，复制算法将可用内存按容量划分为相等的两部分，然后每次只使用其中的一块，当一块内存用完时，就将还存活的对象复制到第二块内存上，然后一次性清楚完第一块内存，再将第二块上的对象复制到第一块。但是这种方式，内存的代价太高，每次基本上都要浪费一般的内存。 于是将该算法进行了改进，内存区域不再是按照1：1去划分，而是将内存划分为8:1:1三部分，较大那份内存叫Eden区，其余是两块较小的内存区叫Survior区。每次都会优先使用Eden区，若Eden区满，就将对象复制到第二块内存区上，然后清除Eden区，如果此时存活的对象太多，以至于Survivor不够时，会将这些对象通过分配担保机制复制到老年代中。(java堆又分为新生代和老年代)

3. 标记-整理 该算法主要是为了解决标记-清除，产生大量内存碎片的问题；当对象存活率较高时，也解决了复制算法的效率问题。它的不同之处就是在清除对象的时候现将可回收对象移动到一端，然后清除掉端边界以外的对象，这样就不会产生内存碎片了。

4. 分代收集 现在的虚拟机垃圾收集大多采用这种方式，它根据对象的生存周期，将堆分为新生代和老年代。在新生代中，由于对象生存期短，每次回收都会有大量对象死去，那么这时就采用复制算法。老年代里的对象存活率较高，没有额外的空间进行分配担保，所以可以使用标记-整理 或者 标记-清除。

minor gc清理年轻的 eden+from+to
major gc清理老年代---这东西是由minor gc触发的
full gc清理整个堆内存空间


#### 备注常用jvm调优参数
-Xss 为jvm启动的每个线程（栈空间）分配的内存大小
-Xms 为jvm启动时分配的内存，即最小内存
-Xmx 为jvm运行过程中分配的最大内存
-Xmn 2g ：设置年轻代大小为2G。整个堆大小=年轻代大小 + 年老代大小 + 持久代大小 。持久代一般固定大小为64m，所以增大年轻代后，将会减小年老代大小。此值对系统性能影响较大，Sun官方推荐配置为整个堆的3/8。
-XX:MinHeapFreeRatio 最小堆内存占比，剩余可用堆内存低于min会自动增大堆内存分配
-XX:MaxHeapFreeRatio 最大对内存占比，剩余可用堆内存高于max会自动减少堆内存分配
-XX:PermSize 非堆初始化内存分配，即permanent size（持久化内存）
-XX:MaxPermSize 最大非堆内存分配

### 垃圾收集器的类型
1. 串行收集器 -XX:+UseSerialGC
2. 并行收集器 -XX:+UseParallelGC（默认收集器）
3. CMS收集器 concurrent-mark-sweep -XX:+UseCMSGC
并发地扫描标记清除（消耗cpu）
年轻的和老年代gc时候会出现promotion failure
就是会有老年代腾出空间的速度赶不上年轻代promotion的速度，需要stw
还有内存空间碎片化；需要full gc进行整理
4. G1收集器 garbage first（内存消耗，堆内存>4G）－XX:+UseG1GC
堆分区1-32MB，多线程扫描这些分区
它会在扫描处理的时候就整理压缩（CMS只有fullGc的时候才会压缩）


### JVM学习
Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
使用-xms设置最新-xmx设置最大
使用-XX:+HeapDumpOutOfMemoryError 堆存储快照打印出来


JDK小工具
工具	用途
jps	列出已装载的JVM
jstack	打印线程堆栈信息
jstat	JVM监控统计信息
jmap	打印JVM堆内对象情况
jinfo	输出JVM配置信息
jconsole	GUI监控工具
jvisualvm	GUI监控工具
jhat	堆离线分析工具
jdb	java进程调试工具
jstatd	远程JVM监控统计信息
