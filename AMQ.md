### 简述
消息服务是以消息为载体，在各应用系统之间进行传递共享的异步数据系统。

### 消息结构：
消息头，消息属性，消息体



### 消息头信息
JMSDestination
JMSDeliveryMode
JMSMessageId
JMSTimestamp
JMSExpiration
JMSRedelivered
JmsPriority
JMSReplyTo
JmsCorrelationId

JMSType

### 消息属性信息
自定义：

String、boolean、byte、double、int、long、float

JMS定义：
JMSXGroupID

JMSXGroupSeq等

### 消息体类型
TextMessage
StreamMessage
MapMessage
ObjectMessage

BytesMessage



### 为什么使用消息服务？
1.异构集成
2.缓解系统瓶颈
3.提高可伸缩性
4.提高最终用户生产率
5.体系结构灵活敏捷

6.ip活跃

### 消息传送模型
点对点模型
发布订阅模型

