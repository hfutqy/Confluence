```
import java.text.SimpleDateFormat;

public class Test {
	public static void main(String[] args) throws Exception {
		String str = "00-00-00 00:00:00";
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟  
		java.util.Date date=format.parse(str);
		System.out.println(date);
	}
}
```
### 输出是Sun Nov 30 00:00:00 CST 2
### 是公元2年11月30日！！！
### exome???
