package cn.jack.core;

/**
 * @Auther: ZhangXing
 * @Date: 2019/1/6 * @Description: cn.jack.core * @version: 1.0
 */
public interface Map<K, V> {
    public void put(K key, V value);

    public V get(K key);

    public Object[] forech();

    public boolean containsKey(K key);

    public String toString();
}
