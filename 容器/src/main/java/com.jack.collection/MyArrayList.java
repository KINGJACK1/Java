package com.jack.collection;


public class MyArrayList implements List {

    private Object[] data;
    private int size;

    public MyArrayList() {
        data = new Object[5];
    }

    public MyArrayList(int length) {
        data = new Object[length];
    }

    @Override
    public int size() {

        return this.size;
    }

    @Override
    public Object get(int i) {
        //判断是否索引越界
        if (i < 0 || i > size) {
            throw new RuntimeException("==========数组越界异常==========");
        }
        return data[i];
    }

    @Override
    public boolean isEmpty() {

        return size == 0 ? true : false;
    }

    @Override
    public boolean contains(Object e) {

        return false;
    }

    @Override
    public int indexOf(Object e) {
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (data[i].equals(e)) {
                index = i;
            }
            ;
        }
        return index;
    }

    @Override
    public void add(int i, Object e) {
        //判断是否索引越界
        if (i < 0 || i > size) {
            throw new RuntimeException("==========数组越界异常==========");
        }

        //判断扩容
        if (size == data.length) {
            grow();
        }
        for (int j = size; j < i; j--) {
            data[j] = data[j - 1];
        }
        data[i] = e;
        size++;
    }

    //数组扩容
    private void grow() {
        Object[] newData = new Object[size + (size >> 1)];
        for (int i = 0; i < size; i++) {
            newData[i] = data[i];
        }
        data = newData;
    }

    @Override
    public void add(Object e) {

        add(size, e);

    }

    @Override
    public boolean addBefore(Object obj, Object e) {
        boolean bol = false;
        for (int i = 0; i < size; i++) {
            if (data[i].equals(obj)) {
                bol = true;
                add(i, e);
            }
        }
        return bol;
    }

    @Override
    public boolean addAfter(Object obj, Object e) {
        boolean bol = false;
        for (int i = 0; i < size; i++) {
            if (data[i].equals(obj)) {
                bol = true;
                add(i + 1, e);
            }
        }
        return bol;
    }

    @Override
    public Object remove(int i) {
        for (int j = i; j < size - 1; j++) {
            data[j] = data[j + 1];
        }
        data[size - 1] = null;
        size--;
        return data;
    }

    @Override
    public boolean remove(Object e) {
        boolean bol = false;
        for (int i = 0; i < size; i++) {
            if (data[i].equals(e)) {
                bol = true;
                for (int j = i; j < size - 1; j++) {
                    data[j] = data[j + 1];
                }
                data[size - 1] = null;
                size--;
            }
        }
        return bol;
    }

    @Override
    public Object replace(int i, Object e) {
        //判断是否索引越界
        if (i < 0 || i > size) {
            throw new RuntimeException("==========数组越界异常==========");
        }
        data[i] = e;
        return data;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("[");
        for (int i = 0; i < size; i++) {
            sb.append(data[i] + ",");
        }
        sb.setCharAt(sb.length() - 1, ']');
        return sb.toString();
    }
}
