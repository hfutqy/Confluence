### 事务Transaction
事务Transaction的四大特性asic：
原子性-Atomicity
一致性-consistency
隔离性-isolation
持久性-durability

#### 原子性-atomicity
即事务的所有操作，要么都完成要么都不完成。中间出问题会回滚(rollback)。
举例：sql的insert、update、alter动作

#### 一致性-consistency
即事务始终保持系统处于一致的状态，不管是否有并发事务的情况，系统一致性都会保持。
举例：n个账户总额不受n账户之间转账所影响。


#### 隔离性-isolation
即事务之间的相互影响和隔离程度，不同的隔离级别，事务之间的并发程度也不相同。隔离性越强大，越趋向于串行执行事务（串行化serializable）。

#### 持久性-durability
即事务执行完成后，数据将会被持久地存储于存储系统中，不可逆。
