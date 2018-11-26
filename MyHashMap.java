package cn.sxt.mycollection;

public class MyHashMap {
	Node2[]  table;    //位桶数组。bucket  array
	int size;				//存放的键值对的个数
	public MyHashMap() {
		table=new Node2[16];	
	}
	
	public  void   put(Object key,  Object   value){
		//定义了新的节点对象
		Node2 newNode=new Node2();
		newNode.hash=myHash(key.hashCode(),table.length);
		System.out.println("hashCode:"+key.hashCode());
		newNode.key=key;
		newNode.value=value;
		newNode.next=null;
		
		Node2 firstNode = table[newNode.hash];
		Node2 theLast=null;
		if(firstNode==null) {
			table[newNode.hash]=newNode;
			size++;
		}else {
			boolean bol=true;
		//遍历查找
		while(firstNode!=null) {
		//比较key是否相等
			if(firstNode.key.equals(key)) {
				bol=false;
				firstNode.value=value;
			}else {//继续查找
				theLast=firstNode;
				firstNode=firstNode.next;
			}
			}
		if(bol) {
			theLast.next=newNode;
			size++;
		}
		}
	}
	@Override
	public String toString() {
	StringBuffer sb=new StringBuffer("{");
	for (Node2 node2 : table) {
		while(node2!=null) {
			sb.append(node2.key+":"+node2.value+",");
			node2=node2.next;
		}
	}
		
	sb.setCharAt(sb.length()-1, ']');	
		
		return sb.toString();
	}
	public static  int  myHash(int  v, int length){
//		System.out.println("hash in myHash:"+(v&(length-1)));		//直接位运算，效率高
//		System.out.println("hash in myHash:"+(v%(length-1)));		//取模运算，效率低
		return  v&(length-1);
	}
}
