package com.jack.collection;

import cn.sxt.mycollection.MyHashMap;

public class TestMyMap {
    public static void main(String[] args) {
        System.out.println("==================����put��toString����");
        MyHashMap map = new MyHashMap();
        map.put(23, "����");
        map.put(421, "����");
        map.put(35, "����");
        map.put(134, "����");
        map.put(554, "����");
        System.out.println(map);
        Object object = map.get(23);
        System.out.println("==================����get����");
        System.out.println("object:" + object);
    }
}
