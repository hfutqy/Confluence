## Redis的key

redis的key是SDS对象存储的（simple dynamic string)   
结构：   
free 空闲内存  
len key的长度  
buf key 

## SDS的好处
1. O(1)复杂度的字符串长度计算
2. 因为有len不会存在存储缓冲区溢出的问题
3. 减少修改字符串长度时所需内存重新分配的次数（free的作用，空间换时间）
4. 二进制存储，所以很安全
5. 兼容部分C字符串函数（也是用\0结尾可以用string.h库的方法）

