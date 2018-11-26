package cn.sxt.mycollection;

public class MyHashMap {
	Node2[]  table;    //λͰ���顣bucket  array
	int size;				//��ŵļ�ֵ�Եĸ���
	public MyHashMap() {
		table=new Node2[16];	
	}
	
	public  void   put(Object key,  Object   value){
		//�������µĽڵ����
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
		//��������
		while(firstNode!=null) {
		//�Ƚ�key�Ƿ����
			if(firstNode.key.equals(key)) {
				bol=false;
				firstNode.value=value;
			}else {//��������
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
//		System.out.println("hash in myHash:"+(v&(length-1)));		//ֱ��λ���㣬Ч�ʸ�
//		System.out.println("hash in myHash:"+(v%(length-1)));		//ȡģ���㣬Ч�ʵ�
		return  v&(length-1);
	}
}
