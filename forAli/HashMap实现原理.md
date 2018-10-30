### HashMap

#### 首先要知道常用的集合：
- ArrayList 本质是数组，查改快，增删慢
- LinkedList 本质是链表，查改慢，增删快

#### 初步解析HashMap
为了实现查改快，增删也快，就要用上上面两个数据结构体
所有基本的结构体是：
1. 所有entry单元组合成一个数组array
2. 每个entry是链表的存储方式（保留了指向下个节点的引用）

那么怎么实现的呢：
1. 构造entry的结构体，要能够存储key、value、指向下个节点的引用
```java
class Node<K, V>{
  private K key;
  private V value;
  private Node<K, V> next;
  //get/set
}
```

2. 构造这个数组，这个数组每个元素都是个Node
```java
 Node<K, V>[];
```

3. 数组就要设置大小；大小有初始化大小和最大值。
数组大小要是2的n次幂，这样减去1，其他所有位都是1
```java
//DefaultSize = ...;为什么走位运算，因为快；要求2的n次幂，因为n-1后按位都是1
static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16
//MaximumSize = ...;
static final int MAXIMUM_CAPACITY = 1 << 30;
```

4. 数组大小设置，以及扩容方案
假如初始化长度为16，满了之后再往map里put值就要扩容了。
```java
final  Node<K, V>[] resize(){
  //...
}
```
扩容要定义标准，每次等用满了再扩容这很不友好。
那么在 used/total > X 的时候扩容，这个X定义为扩容因子
```java
static final float DefaultLoadFactor=0.75f;
```
那么还需要记录一个当前map长度的数值
```java
transient int size;
```
5. 链表的长度也应该有个限制
链表长度达到某个值后也应该需要某些动作，长度太长就影响查询效率
JDK1.8的时候，如果链表长度大于某个值，就将其结构改为红黑树。
```java
//阈值=容量*负载因子
static final int TREEIFY_THRESHOLD = 8;
```
6. 新来的node节点存在哪？
我们用object的native方法 hashcode()
```java
public native int hashCode();
```
例如"1".hashCode()=49,但是这并不是我们想要的hashcode
我们通过一个hash函数，得到一个理想值 hash(key);

#### 深度源码解析HashMap
##### PUT
1. 开始根据put方法走
2. putVal()---->hash(key)
```java
public V put(K key, V value) {                         
    return putVal(hash(key), key, value, false, true);
}                                                      
```
```java
static final int hash(Object key) {
      int h;
      return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
      //h右移16位后再和h异或，充分利用了int32位的所有值
      //即h的高16位和低16位做异或运算
}
```
resize()是初始化 和 扩容的动作
```java
final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
               boolean evict) {
    //tab是当前的map；Node是当前的k,v；n是数组长度
    Node<K,V>[] tab; Node<K,V> p; int n, i;
    if ((tab = table) == null || (n = tab.length) == 0)
        n = (tab = resize()).length;
    //这里的i=(n-1)&hash 表示i肯定是小于n-1，妙啊，解决了前面获取hash值会越界的问题
    if ((p = tab[i = (n - 1) & hash]) == null)
        tab[i] = newNode(hash, key, value, null);
    else {
        Node<K,V> e; K k;
        if (p.hash == hash &&
            ((k = p.key) == key || (key != null && key.equals(k))))
            e = p;
        else if (p instanceof TreeNode)
            e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
        else {
            for (int binCount = 0; ; ++binCount) {
                if ((e = p.next) == null) {
                    p.next = newNode(hash, key, value, null);
                    if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                        treeifyBin(tab, hash);
                    break;
                }
                if (e.hash == hash &&
                    ((k = e.key) == key || (key != null && key.equals(k))))
                    break;
                p = e;
            }
        }
        if (e != null) { // existing mapping for key
            V oldValue = e.value;
            if (!onlyIfAbsent || oldValue == null)
                e.value = value;
            afterNodeAccess(e);
            return oldValue;
        }
    }
    ++modCount;
    if (++size > threshold)
        resize();
    afterNodeInsertion(evict);
    return null;
}
```








































··
