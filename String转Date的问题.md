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

---
补充一个日期比较工具
---
```
SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
Date d1 = df.parse("2017-10-24");
Date d2 = new Date();
if(d1.after(d2)){
	logger.info("d1 after d2");
}else if(d1.equals(d2)){
	logger.info("d1 equal d2");
}else {
	logger.info("d1 before d2");
}
```

