package com.jack.collection;

public class MyTest {
    public static void main(String[] args) {
	/*	测试arrayList
	 * List list=new MyArrayList();
		list.add("张三");
		list.add("李四");
		list.add("王五");
		list.add("赵六");
		list.add("田七");
		System.out.println("list:"+list);
		System.out.println("size="+list.size());
		System.out.println("是空："+list.isEmpty());
		Object object = list.get(2);
		System.out.println("get(2):"+object);
		int index = list.indexOf("田七");
		System.out.println("index:"+index);
		list.remove(1);
		System.out.println("================================================================");
		System.out.println("list:"+list);
		System.out.println("size="+list.size());*/
        //测试linkedList
        List link = new MyLinkedList();
        link.add("张三");
        link.add("李四");
        link.add("王五");
        link.add("赵六");
        link.add("田七");
        System.out.println("link:" + link);
        System.out.println("size=" + link.size());
        System.out.println("是空：" + link.isEmpty());
    }
}
