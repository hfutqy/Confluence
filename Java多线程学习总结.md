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




