#### for里面不可以使用remove(i)的方法
迭代器从底层集合中删除刚返回的元素（可选操作）。该方法只能在每次调用next后调用一次。 <br>
如果底层集合已被修改而迭代器正在进程中（除了调用本方法），则迭代器的行为不能确定。 <br>
---
所以要想在循环体内使用remove就得用在next后，使用Itertor
```
Iterator<MyObject> iterator = myList.iterator();
while(iterator.hasNext()){
    MyObject myobj = iterator.next();
    if (myobj == null) {
        //使用iterator.remove删掉该list的一个元素
        myobj.remove();
        continue;
    }
    //do someting
}
```
