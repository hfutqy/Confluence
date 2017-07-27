import java.util.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Main {
	public static void main(String[] args) {
		ArrayList<User> comingBackList =  new ArrayList<User>();
		User user1 = new User();
		user1.setAge(10);user1.setId(1);
		User user2 = new User();
		user2.setAge(15);user2.setId(2);
		User user3 = new User();
		user3.setAge(12);user3.setId(3);
		
		comingBackList.add(user1);
		comingBackList.add(user2);
		comingBackList.add(user3);
		
		User aaa = user1;
		System.out.println(aaa.getAge()+"XXXXXX");
//		把大于15的切到前面
//		List<User> a = new ArrayList<User>();
//		List<User> b = new ArrayList<User>();
//		for(User billVo : comingBackList){
//			if(billVo.getAge()>=15){
//				a.add(billVo);
//			}else{
//				b.add(billVo);
//			}
//		}
//		comingBackList.clear();
//		comingBackList.addAll(a);
//		comingBackList.addAll(b);
		
		sortList(comingBackList);
		
		
		for(User u:comingBackList)
		System.out.println(u.getAge()+" "+u.getId());
	}
	
	public static ArrayList<User> sortList(ArrayList<User> al) {
		Collections.sort(al, new Comparator<User>(){
			@Override
			public int compare(User u1, User u2) {
				int rslt = 1;
				if(u1.getAge()>u2.getAge()) {
					rslt = 1;
				}else {
					rslt = -1;
				}
				return rslt;
			}
		});
		return al;
	}
	
}
