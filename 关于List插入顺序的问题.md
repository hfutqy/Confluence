list插入顺序：
```
import java.util.ArrayList;
import java.util.List;

public class ListTest {
	public static void main(String[] args) {
		List<Integer> alist = new ArrayList<Integer>();
		alist.add(1);
		alist.add(2);
		alist.add(3);
		alist.add(4);
		alist.add(0, 10);
		alist.add(0, 9);
		System.out.println(alist);
		
	}
}
```
那么输出的结果是：
[9, 10, 1, 2, 3, 4]
add(x,element);就是在x的位置挤进去一个元素，后面的往后排。
