### SpringBean生命周期
1. Bean的建立

2.属性注入

3.
如果实现了 BeanNameAware 接口，则执行setBeanName()
如果实现了BeanFactoryAware接口，则执行setBeanFactory()
如果实现了BeanPostProcessor接口，则执行processBeforeInitialization()
如果实现了InitializingBean接口，则执行afterPropertiesSet()
bean定义文件中定义了init-method
如果实现了BeanPostProcessor接口，则执行processAfterInitialization()

4. 此时，Bean已经可以被应用系统使用，并且将保留在BeanFactory中直到它不在被使用

5.
执行DisposableBean的destroy()方法，结束这个Bean的生命
或者
xml中定义了destroy-method的命名空间
