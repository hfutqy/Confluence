### AMQ
#### 一、生产者
关注这个类：org.springframework.jms.core.JmsTemplate
这是AMQ消息队列消息模板类
即所有的消息都是要设置这个模板里的属性
四大属性：
A. 优先级
赋值：0-9， 由低到高，一般使用0-4就可以了
因为是阻塞的，所以在多线程...

B. 消息推送方式
设置是否持久化要发送的消息：1-非持久化；2-持久化

C. 消息存活时间
设置消息的存活时间，毫秒单位

D. 服务质量开关
public boolean isExplicitQosEnabled()
如果是true，deliveryMode, priority, 和 timeToLive的值将被使用；否则使用默认的值。

E
接收等待时间
