package com.jack.collection;

/**
 * @author jack
 * @title: List
 * @projectName 容器
 * @description: TODO
 * @date 2020/6/187:51 PM
 */
public interface List {
    int size();

    Object get(int i);

    boolean isEmpty();

    boolean contains(Object e);

    int indexOf(Object e);

    void add(int i, Object e);

    void add(Object e);

    boolean addBefore(Object obj, Object e);

    boolean addAfter(Object obj, Object e);

    Object remove(int i);

    boolean remove(Object e);

    Object replace(int i, Object e);
}
