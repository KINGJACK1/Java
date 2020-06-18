package com.jack.collection;

import cn.sxt.mycollection.MyHashMap;

public class TestMyMap {
    public static void main(String[] args) {
        System.out.println("==================测试put和toString方法");
        MyHashMap map = new MyHashMap();
        map.put(23, "张三");
        map.put(421, "李四");
        map.put(35, "王五");
        map.put(134, "赵六");
        map.put(554, "田七");
        System.out.println(map);
        Object object = map.get(23);
        System.out.println("==================测试get方法");
        System.out.println("object:" + object);
    }
}
