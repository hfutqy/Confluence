此处表示开始java进阶学习
包括 java多线程、分布式、并发操作、锁等，慢慢研究

---
#### 线程和进程

* 进程：每个进程都有独立的代码和数据空间（进程上下文），进程间的切换会有较大的开销，一个进程包含1--n个线程。（进程是资源分配的最小单位）
* 线程：同一类线程共享代码和数据空间，每个线程有独立的运行栈和程序计数器(PC)，线程切换开销小。（线程是cpu调度的最小单位）

线程和进程的五个共同的状态：创建,就绪，运行，阻塞,终止

### 创建线程的两种方式
#### 一、继承java.lang.Thread类
```
package thread;

public class ThreadTest {
	public static void main(String[] args) {
		MyThread t1 = new MyThread("t1的线程");
		MyThread t2 = new MyThread("t2的线程");
		t1.start();
		t2.start();
	}
}

class MyThread extends Thread {
	private String flag;
	
	public MyThread(String flag) {
		this.flag = flag;
	}
	
	@Override
	public void run() {
		super.run();
		for(int i=0; i<5; i++) {
			System.out.println(flag + "在运行中");
		}
	}
}
```
这里同时启动了t1和t2两个线程，他们可以同时运行，就是并发的过程，谁先谁后是随机的，准确的说是cpu调度控制，不能被预测的。
运行结果明显是乱序的，并且每一次都随机顺序：
```
t1的线程在运行中
t1的线程在运行中
t1的线程在运行中
t2的线程在运行中
t1的线程在运行中
t2的线程在运行中
t1的线程在运行中
t2的线程在运行中
t2的线程在运行中
t2的线程在运行中
```
注意：**start()**方法的调用后并不是立即执行多线程代码，而是使得该线程变为可运行态（Runnable），什么时候运行是由操作系统决定的。Thread.sleep()方法调用目的是不让当前线程独自霸占该进程所获取的CPU资源，以留出一定时间给其他线程执行的机会。
还要注意的一点，当t1线程未结束又运行t1（t1.start()）那么程序会报异常：java.lang.IllegalThreadStateException，提示t1还未运行结束，这个注意控制。

#### 二、实现java.lang.Runnable接口
直接上代码，
```
public class ThreadTest {
	public static void main(String[] args) {
		MyThread t1 = new MyThread("t1的线程");
		MyThread t2 = new MyThread("t2的线程");
		new Thread(t1).start();
		new Thread(t2).start();
	}
}

class MyThread implements Runnable{

	private String flag;
	
	public MyThread(String flag) {
		this.flag = flag;
	}
	
	@Override
	public void run() {
		for(int i=0; i<5; i++) {
			System.out.println(flag + "在运行中");
		}
	}
}
```
既然是同一个目的不同的实现方式，这个的运行结果也是和继承Thread类是一个效果。
**run()**方法是多线程程序的一个约定。所有的多线程代码都在run方法里面。Thread类实际上也是实现了Runnable接口的类。
其实能够发现Thread类也是实现了Runnable接口的类，查看Thread类的源码还可以发现：
```
	public Thread(Runnable target) {
        init(null, target, "Thread-" + nextThreadNum(), 0);
    }
```
其构造方法初始化了线程对象；调用t.start()实际就是使得线程t进入随时可运行状态(一旦内存运行被使用，cpu分配资源，即刻运行)。
#### 三、线程状态
查了点资料然后自己用visio画了个图：
![线程各状态之间的关系](http://img.blog.csdn.net/20171101152357458?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvcXk5MjQxMjAzMTY=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

1. 新建(new)：新创建了一个线程对象。
2. 可运行(runnable)：线程对象创建后，其他线程(比如main线程）调用了该对象的start()方法。该状态的线程位于可运行线程池中，等待被线程调度选中，获取cpu 的使用权 。
3. 运行(running)：可运行状态(runnable)的线程获得了cpu 时间片（timeslice） ，执行程序代码。
4. 阻塞(blocked)：阻塞状态是指线程因为某种原因放弃了cpu 使用权，也即让出了cpu timeslice，暂时停止运行。直到线程进入可运行(runnable)状态，才有机会再次获得cpu timeslice 转到运行(running)状态。阻塞的情况分三种： 
(一). 等待阻塞：运行(running)的线程执行o.wait()方法，JVM会把该线程放入等待队列(waitting queue)中。
(二). 同步阻塞：运行(running)的线程在获取对象的同步锁时，若该同步锁被别的线程占用，则JVM会把该线程放入锁池(lock pool)中。
(三). 其他阻塞：运行(running)的线程执行Thread.sleep(long ms)或t.join()方法，或者发出了I/O请求时，JVM会把该线程置为阻塞状态。当sleep()状态超时、join()等待线程终止或者超时、或者I/O处理完毕时，线程重新转入可运行(runnable)状态。（注意,sleep是不会释放持有的锁）
5. 死亡(dead)：线程run()、main() 方法执行结束，或者因异常退出了run()方法，则该线程结束生命周期。死亡的线程不可再次复生。


#### 四、几种创建线程方式的比较
（除了上述介绍还有通过Callable和Future创建线程，暂不细说）
采用实现Runnable、Callable接口的方式创见多线程时，优势是：
线程类只是实现了Runnable接口或Callable接口，还可以继承其他类。
在这种方式下，多个线程可以共享同一个target对象，所以非常适合多个相同线程来处理同一份资源的情况，从而可以将CPU、代码和数据分开，形成清晰的模型，较好地体现了面向对象的思想。
劣势是：
编程稍微复杂，如果要访问当前线程，则必须使用Thread.currentThread()方法。
使用继承Thread类的方式创建多线程时优势是：
编写简单，如果需要访问当前线程，则无需使用Thread.currentThread()方法，直接使用this即可获得当前线程。
劣势是：
线程类已经继承了Thread类，所以不能再继承其他父类。

