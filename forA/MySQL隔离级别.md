1）read uncommitted : 读取尚未提交的数据 ：哪个问题都不能解决
2）read committed：读取已经提交的数据 ：可以解决脏读 ---- oracle默认的
3）repeatable read：重读读取：可以解决脏读 和 不可重复读 ---mysql默认的
4）serializable：串行化：可以解决 脏读 不可重复读 和 虚读---相当于锁表
