package com.datastructure.linetable;

public class MyLinkedList implements List{

	private Node head;//头结点，不存储数据，为了编程方便
	private int size;//一共有几个结点
	public MyLinkedList() {
		head=new Node();
	}

	@Override
	public int size() {	
		return size;
	}
	@Override
	public Object get(int i) {
		//判断是否索引越界
		if(i<0||i>size) {
			throw new RuntimeException("==========数组越界异常==========");
		}
		Node temp=head;
		for(int j=0;j<=i;j++) {
			temp=temp.next;
		}
		return temp.data;
	}
	@Override
	public boolean isEmpty() {
		return size==0;
	}
	@Override
	public boolean contains(Object e) {
		
		return false;
	}
	@Override
	public int indexOf(Object e) {
		int index=-1;
		Node temp=head;
		for(int i=0;i<size;i++) {
			temp=temp.next;
			if(temp.data.equals(e)) {
				index=i;	
			}
		}
		return index;
	}

	@Override
	public void add(int i, Object e) {
	//判断是否索引越界
	if(i<0||i>size) {
		throw new RuntimeException("==========数组越界异常==========");
	}		
	//从head开始遍历,找到索引前一个节点
	Node temp=head;
	for(int j=0;j<i;j++) {
		temp=temp.next;
	}
	//新创建一个结点
	Node newNode = new Node();
	newNode.data = e;
	newNode.next=temp.next;
	temp.next=newNode;
	size++;
	}
	@Override
	public void add(Object e) {
		add(size,e);	
	}
	@Override
	public boolean addBefore(Object obj, Object e) {
		boolean bol=false;
		Node temp=head;
		for(int i=0;i<size;i++) {
			temp=temp.next;
			if(temp.data.equals(obj)) {
				add(i,e);
				bol=true;
			}
		}
		return bol;
	}

	@Override
	public boolean addAfter(Object obj, Object e) {
		boolean bol=false;
		Node temp=head;
		for(int i=0;i<size;i++) {
			temp=temp.next;
			if(temp.data.equals(obj)) {
				//新创建一个结点
				Node newNode = new Node();
				newNode.data = e;
				newNode.next=temp.next;
				temp.next=newNode;
				size++;
				bol=true;
			}
		}
		return bol;
	}
	@Override
	public Object remove(int i) {
		//从head开始遍历,找到索引前一个节点
		Node temp=head;
		for(int j=0;j<i;j++) {
			temp=temp.next;
		}
		Node index=temp.next;
		temp.next=index.next;
		return temp;
	}

	@Override
	public boolean remove(Object e) {
		
		return false;
	}
	@Override
	public Object replace(int i, Object e) {
		
		return null;
	}

	@Override
	public String toString() {
		if(size == 0){
			return "[]";
		}
		StringBuilder builder = new StringBuilder("[");
		Node temp=head;
		for(int i=0;i<size;i++) {//size=5;i=0,data=0
			temp=temp.next;
			builder.append(temp.data+",");
		}
		builder.setCharAt(builder.length()-1, ']');
		return builder.toString();
	}
}
