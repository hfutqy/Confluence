### 直接上代码举例：

#### 这是不走FutureTask，但是要等待前置条件执行完，所以基本就是串行
```
public class TestJ {
    public static void main(String[] args) throws InterruptedException{
        long startTime = System.currentTimeMillis();
        //第一步--网购厨具
        OnlineShopping chujuThread = new OnlineShopping();
        chujuThread.start();
        chujuThread.join();//串行保证chujuThread先执行完毕，join()=join(0)即等待无限长直到chujuThread执行结束
        //第二步--去超市购买食材
        Thread.sleep(2000);
        Shicai shicai = new Shicai();
        System.out.println("第二步--食材到位");
        //第三--用厨具烹饪食材
        System.out.println("第三部--开始用厨具烹饪食材");
        cook(chujuThread.chuju, shicai);

        System.out.println("总共用时" + (System.currentTimeMillis() - startTime) + "ms");

    }

    static class OnlineShopping extends Thread{
        private Chuju chuju;

        @Override
        public void run(){
            System.out.println("第一步--下单");
            System.out.println("第一步--等待送货");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("第一步--快递送到");
            chuju = new Chuju();
        }
    }

    //烹饪食材
    static void cook(Chuju chuju, Shicai shicai) {
    }

    static class Chuju{};
    static class Shicai{};


}
```

#### 这让前置条件feature对象task自己跑，不影响继续往下走，即同时还能做别的事，最后通过task.get()获取task的执行结果
```
public class TestJ{

    public static void main(String[] args) throws Exception{
        long startTime = System.currentTimeMillis();
        //第一步网购厨具
        Callable<Chuju> onlineShopping = new Callable<Chuju>() {
            @Override
            public Chuju call() throws Exception {
                System.out.println("第一步--下单");
                System.out.println("第一步--等待送货");
                Thread.sleep(5000);
                System.out.println("第一步--快递送到");
                return new Chuju();
            }
        };

        FutureTask<Chuju> task = new FutureTask<Chuju>(onlineShopping);
        new Thread(task).start();
        // 第二步 去超市购买食材
        Thread.sleep(2000);
        Shicai shicai = new Shicai();
        System.out.println("第二步--食材到位");
        //第三步 用厨具烹饪食材
        if (!task.isDone()) {
            System.out.println("获取厨具task还没走完，等待");
        }
        //获取task的return值，如果task没走完会一直卡在这等待task走完拿到返回值
        Chuju chuju = task.get();
        System.out.println("第三步=-厨具到位，开始展现厨艺");
        cook(chuju, shicai);
        System.out.println("总共用时" + (System.currentTimeMillis() - startTime) + "ms");

    }

    //  用厨具烹饪食材
    static void cook(Chuju chuju, Shicai shicai) {}

    // 厨具类
    static class Chuju {}

    // 食材类
    static class Shicai {}

}
```

还是FutureTask好用，异步处理任务，让任务自己跑，并且结果是可以感知的。
