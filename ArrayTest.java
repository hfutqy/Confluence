import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArrayTest {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);

        System.out.println("用for循环遍历");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }

        System.out.println("用增强for循环");
        for (Integer i : list) {
            System.out.println(i);
        }

        System.out.println("用iterator+while");
        Iterator<Integer> it = list.iterator();
        while (it.hasNext()) {
            int i = (Integer) it.next();
            System.out.println(i);
        }

        System.out.println("用iterator+for");
        for (Iterator<Integer> iter = list.iterator(); iter.hasNext();) {
            int i = (Integer) iter.next();
            System.out.println(i);
        }
    }
}
