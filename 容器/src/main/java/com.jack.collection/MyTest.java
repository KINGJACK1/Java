package com.jack.collection;

public class MyTest {
    public static void main(String[] args) {
	/*	����arrayList
	 * List list=new MyArrayList();
		list.add("����");
		list.add("����");
		list.add("����");
		list.add("����");
		list.add("����");
		System.out.println("list:"+list);
		System.out.println("size="+list.size());
		System.out.println("�ǿգ�"+list.isEmpty());
		Object object = list.get(2);
		System.out.println("get(2):"+object);
		int index = list.indexOf("����");
		System.out.println("index:"+index);
		list.remove(1);
		System.out.println("================================================================");
		System.out.println("list:"+list);
		System.out.println("size="+list.size());*/
        //����linkedList
        List link = new MyLinkedList();
        link.add("����");
        link.add("����");
        link.add("����");
        link.add("����");
        link.add("����");
        System.out.println("link:" + link);
        System.out.println("size=" + link.size());
        System.out.println("�ǿգ�" + link.isEmpty());
    }
}
