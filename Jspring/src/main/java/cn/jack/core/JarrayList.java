package cn.jack.core;

/**
 * @Auther: ZhangXing
 * @Date: 2018/11/16 * @Description: cn.jack * @version: 1.0
 */
public class JarrayList implements List {
    private Object[] data;
    private int size;

    public JarrayList() {
        data = new Object[5];
    }

    public JarrayList(int length) {
        data = new Object[length];
    }

    public int size() {

        return this.size;
    }

    public Object get(int i) {
        //判断是否索引越界
        if (i < 0 || i > size) {
            throw new RuntimeException("==========数组越界异常==========");
        }
        return data[i];
    }

    public boolean isEmpty() {

        return size == 0 ? true : false;
    }

    public boolean contains(Object e) {

        return false;
    }

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


    public void add(Object e) {

        add(size, e);

    }

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


    public Object remove(int i) {
        for (int j = i; j < size - 1; j++) {
            data[j] = data[j + 1];
        }
        data[size - 1] = null;
        size--;
        return data;
    }

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

    public Object replace(int i, Object e) {
        //判断是否索引越界
        if (i < 0 || i > size) {
            throw new RuntimeException("==========数组越界异常==========");
        }
        data[i] = e;
        return data;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("[");
        for (int i = 0; i < size; i++) {
            sb.append(data[i] + ",");
        }
        sb.setCharAt(sb.length() - 1, ']');
        return sb.toString();
    }
}
