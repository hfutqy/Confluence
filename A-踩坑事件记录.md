3### 2018/5/28
#### for里面不可以使用remove(i)的方法
迭代器从底层集合中删除刚返回的元素（可选操作）。该方法只能在每次调用next后调用一次。 <br>
如果底层集合已被修改而迭代器正在进程中（除了调用本方法），则迭代器的行为不能确定。 <br>
所以要想在循环体内使用remove就得用在next后，使用Itertor<br>
```
Iterator<MyObject> iterator = myList.iterator();
while(iterator.hasNext()){
    MyObject myobj = iterator.next();
    if (myobj == null) {
        //使用iterator.remove删掉该list的一个元素
        myobj.remove();
        continue;
    }
    //do someting
}
```
### 2018/6/7
#### ConcurrentHashmap、Hashtable不支持key或者value为null
在很多java资料中，都有提到 ConcurrentHashmap HashMap和Hashtable都是key-value存储结构，但他们有一个不同点是 ConcurrentHashmap、Hashtable不支持key或者value为null，而HashMap是支持的。为什么会有这个区别？在设计上的目的是什么？

在网上找到了这样的解答:The main reason that nulls aren’t allowed in ConcurrentMaps (ConcurrentHashMaps, ConcurrentSkipListMaps) is that ambiguities that may be just barely tolerable in non-concurrent maps can’t be accommodated. The main one is that if map.get(key) returns null, you can’t detect whether the key explicitly maps to null vs the key isn’t mapped. In a non-concurrent map, you can check this via map.contains(key), but in a concurrent one, the map might have changed between calls.

理解如下：ConcurrentHashmap和Hashtable都是支持并发的，这样会有一个问题，当你通过get(k)获取对应的value时，如果获取到的是null时，你无法判断，它是put（k,v）的时候value为null，还是这个key从来没有做过映射。HashMap是非并发的，可以通过contains(key)来做这个判断。而支持并发的Map在调用m.contains（key）和m.get(key),m可能已经不同了。

### 2018/7/11
#### swoole_process must run at php_cli environment.
踩坑注意，这个方法只能在cli(命令行模式)下使用，不支持fpm（web请求模式）
贴上1.10版本源码（好像1.7及以前是运行的，没有cli校验）：
```
static PHP_METHOD(swoole_process, __construct)
{
    zend_bool redirect_stdin_and_stdout = 0;
    long pipe_type = 2;
    zval *callback;

    //only cli env
    if (!SWOOLE_G(cli))
    {
        swoole_php_fatal_error(E_ERROR, "swoole_process only can be used in PHP CLI mode.");
        RETURN_FALSE;
    }
......
```
换新的思路，使用curl_multi 来实现并发的http请求（考虑到时间损耗在于http请求响应上，这样并发使用http请求有效解决耗时）
