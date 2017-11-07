### 介绍一下，这是list初始化的几种方式
```
package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ListTest {
	public static void main(String[] args) {
		/***初始化list***/
		//常规思路
		ArrayList<String> list1 = new ArrayList<String>();
		list1.add("A");
		list1.add("B");
		list1.add("C");
		System.out.println(list1);
		
		//一步到位-Arrays.asList//指定列表且长度不可变
		List<String> list2 = Arrays.asList("Buenos Aires", "Córdoba", "La Plata");
		System.out.println(list2);
		if(list2.contains("Córdoba")) {
			System.out.println("XXXXX");
		}
		
		//一步到位-Collections.singletonList//返回一个只包含指定对象的不可变列表
		List<String> list3 = Collections.singletonList("Buenos Aires");
		System.out.println(list3);
		
		//匿名内部类
		ArrayList<String> list4 = new ArrayList<String>() {{
		    add("A");
		    add("B");
		    add("C");
		}};
		System.out.println(list4);
		
		//一步到位之长度可变
		List<String> list5 = new ArrayList<>(Arrays.asList("Buenos Aires", "Córdoba", "La Plata"));
		System.out.println(list5);
	}
}
```
