package cn.jack.core;

/**
 * @Auther: ZhangXing
 * @Date: 2019/1/6 * @Description: cn.jack.core * @version: 1.0
 */
public class JhashMap<K, V> implements Map<K, V> {

    Node2[] table;    //位桶数组。bucket  array
    int size;                //存放的键值对的个数

    public JhashMap() {
        table = new Node2[16];
    }

    public void put(K key, V value) {
        //定义了新的节点对象
        Node2 newNode = new Node2();
        newNode.hash = myHash(key.hashCode(), table.length);
        System.out.println("hashCode:" + key.hashCode());
        newNode.key = key;
        newNode.value = value;
        newNode.next = null;

        Node2 firstNode = table[newNode.hash];
        Node2 theLast = null;
        if (firstNode == null) {
            table[newNode.hash] = newNode;
            size++;
        } else {
            boolean bol = true;
            //遍历查找
            while (firstNode != null) {
                //比较key是否相等
                if (firstNode.key.equals(key)) {
                    bol = false;
                    firstNode.value = value;
                } else {//继续查找
                    theLast = firstNode;
                    firstNode = firstNode.next;
                }
            }
            if (bol) {
                theLast.next = newNode;
                size++;
            }
        }
    }

    public V get(K key) {
        int hash = myHash(key.hashCode(), table.length);
        Node2 first = table[hash];
        while (first != null) {
            if (first.key.equals(key)) {
                return (V) first.value;
            } else {
                first = first.next;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("{");
        for (Node2 node2 : table) {
            while (node2 != null) {
                sb.append(node2.key + ":" + node2.value + ",");
                node2 = node2.next;
            }
        }

        sb.setCharAt(sb.length() - 1, ']');

        return sb.toString();
    }

    public Object[] forech() {
        Object[] obj = new Object[size];
        int length = 0;
        for (Node2 node2 : table) {
            while (node2 != null) {
                obj[length++] = node2.value;
                node2 = node2.next;
            }
        }
        return obj;
    }

    public boolean containsKey(K key) {
        int hash = myHash(key.hashCode(), table.length);
        Node2 first = table[hash];
        while (first != null) {
            if (first.key.equals(key)) {
                return true;
            } else {
                first = first.next;
            }
        }
        return false;
    }

    public static int myHash(int v, int length) {
//		System.out.println("hash in myHash:"+(v&(length-1)));		//直接位运算，效率高
//		System.out.println("hash in myHash:"+(v%(length-1)));		//取模运算，效率低
        return v & (length - 1);
    }

    public static void main(String[] args) {
       /* Map map=new JhashMap();
        map.put("1","张三");
        map.put("2","李四");
        System.out.println(map.toString());*/

        StringBuilder str = new StringBuilder("/Jspring_war_exploded/");
        System.out.println("string = " + str);

        // prints substring from index 3
        System.out.println("substring is = " + str.substring(3));

    /* prints substring from index 1 to 4 excluding character
    at 4th index */
        System.out.println("substring is = " + str.substring(1, 4));
    }
}
