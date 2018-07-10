```
//以下可以使用php -r '***'直接运行
<?php

$start_time = microtime(TRUE);
$process_arr = array();

$productIds = [
    "100001",
    "100002",
    "100003"
];

foreach ($productIds as $productId) {
    $process = new swoole_process( "my_process", true);

    $process->start();
    $process->write($productId); //通过管道发数据到子进程。管道是单向的：发出的数据必须由另一端读取。不能读取自己发出去的

    $process_arr[] = $process;
}

//先不获取子进程返回值，循环结束后统一返回：(这意味着一次跑十个耗时取决于耗时最长的那个)
foreach ($process_arr as $process){
    echo $rec = $process->read();
}

//子进程创建成功后要执行的函数
function my_process(swoole_process $worker){
    sleep(1);//暂停1s

    $productId = $worker->read();
    echo "****".$productId."****\n";

    // $return = exec($cmd);//exec只会输出命令执行结果的最后一行内容，且需要显式打印输出

    ob_start();
    passthru($productId);//执行外部程序并且显示未经处理的、原始输出，会直接打印输出。
    $return = ob_get_clean();
    if(!$return) $return = 'null';
    $worker->write($return);//写入数据到管道
}

//子进程结束必须要执行wait进行回收，否则子进程会变成僵尸进程
while($ret = swoole_process::wait()){// $ret 是个数组 code是进程退出状态码，
    $pid = $ret['pid'];
    echo PHP_EOL."Worker Exit, PID=" . $pid . PHP_EOL;
}

$end_time = microtime(TRUE);
echo sprintf("use time:%.3f s\n", $end_time - $start_time);
```
