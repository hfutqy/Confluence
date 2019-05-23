```java
class Service {
    public void testMethod(Object lock) {
        try {
            synchronized (lock) {
                System.out.println("begin wait()");
                lock.wait();
                System.out.println("end wait()");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("wait状态的线程被interrupt");
        }
    }
}

class ThreadA extends Thread {
    private Object lock;

    public ThreadA(Object lock) {
        super();
        this.lock = lock;
    }

    @Override
    synchronized public void run() {
        try {
            Service ser = new Service();
            ser.testMethod(lock);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

public class Demo {
    public static void main(String[] args) throws InterruptedException {
        Object lock = new Object();
        ThreadA a = new ThreadA(lock);
        a.start();
	System.out.println(System.currentTimeMillis());
        Thread.sleep(1000);
	System.out.println(System.currentTimeMillis());
        a.interrupt();
    }
}
```

输出如下：  
```
begin wait()
java.lang.InterruptedException
	at java.lang.Object.wait(Native Method)
	at java.lang.Object.wait(Object.java:502)
	at com.xiaomi.youpin.hera.controller.Service.testMethod(TestController.java:63)
	at com.xiaomi.youpin.hera.controller.ThreadA.run(TestController.java:85)
wait状态的线程被interrupt！

```
