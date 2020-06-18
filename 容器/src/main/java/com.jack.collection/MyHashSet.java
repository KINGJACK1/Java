package com.jack.collection;

public class MyHashSet {
    MyHashMap map;
    private static final Object PRESENT = new Object();

    //�޲ι��췽��
    public MyHashSet() {
        map = new MyHashMap();
    }

    //size
    public int size() {
        return map.size;
    }

    //add����
    public void add(Object key) {
        map.put(key, PRESENT);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("[");
        for (int i = 0; i < map.table.length; i++) {
            Node2 node = map.table[i];
            while (node != null) {
                sb.append(node.key + ",");
                node = node.next;
            }
        }
        sb.setCharAt(sb.length() - 1, ']');
        return sb.toString();
    }

    public static void main(String[] args) {
        MyHashSet set = new MyHashSet();
        set.add("����");
        set.add("����");
        set.add("����");
        set.add("����");
        System.out.println(set);
    }
}
