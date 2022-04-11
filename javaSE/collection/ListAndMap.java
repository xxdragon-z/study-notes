package collection;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by XXDragon on 2022/4/11 16:12
 */
public class ListAndMap<T, K, V> {
    /**
     * 基本的有序集合
     * 负载因子
     * 关于负载因子 默认为0.75 也就是说，容量达到 n*0.75阈值会进行一个扩容
     * 举例子，默认容量为16，那么当第12个变量进入时，会进行一次扩容
     * 扩容会导致内存的复制，以及出现垃圾碎片，所以开发过程中要减少改情况
     * 在已知长度 l 的情况下，可得出一个公式   l*4/3+1 得到符合条件的初始容量，不会进行第二次扩容
     */
    private int initialCapacity;

    /**
     * @param initialCapacity
     * @return
     */
    public ArrayList<T> getArrayList(int initialCapacity) {
        return new ArrayList<>(initialCapacity * 4 / 3 + 1);
    }

    /**
     * 链表数据格式的集合
     * linkedList 链表 没有容量上限，所以不需要指定容量
     *
     * @param initialCapacity
     * @return
     */
    public LinkedList<T> getLinkedList(int initialCapacity) {
        return new LinkedList<>();
    }

    /**
     * 线程安全的arrayList
     * 通过在关键方法使用 synchronized 关键字修饰方法，实现的线程安全
     * 缺陷 add ，remove，方法会锁类，导致不可进行其他操作
     *
     * @param initialCapacity
     * @return
     */
    public Vector<T> getVector(int initialCapacity) {
        return new Vector<>(initialCapacity * 4 / 3 + 1);
    }

    /**
     * 保证数据唯一的集合
     * 存取唯一变量的集合 放入的变量必须实现equals和hashcode方法 否则无法唯一
     * 因为内部使用hashMap来实现唯一和存取，所以是无序数组
     *
     * @param initialCapacity
     * @return
     */
    public HashSet<T> getHashSet(int initialCapacity) {
        return new HashSet<>(initialCapacity * 4 / 3 + 1);
    }

    /**
     * 保证线程安全的有序数组
     * 使用锁替换了synchronized 优化锁
     * todo 每次 add 和 remove 都会进行内部的array （存变量的数组）重新复制 所以没有容量这一说
     * 内存不是最优
     *
     * @return
     */
    public CopyOnWriteArrayList<T> getCopyOnWriteArrayList() {
        return new CopyOnWriteArrayList<>();
    }

    /**
     * 保证数据唯一且线程安全的集合
     * 使用锁替换了synchronized 优化锁
     * 内部使用 CopyOnWriteArrayList 来实现存取
     * todo  每次add都会遍历内部 array 数组，判断是否存在相同数据
     * 每次 add 和 remove 都会进行内部的array （存变量的数组）重新复制 所以没有容量这一说
     * 存取唯一变量的集合 放入的变量必须实现equals和hashcode方法 否则无法唯一
     * 内存不是最优
     *
     * @return
     */
    public CopyOnWriteArraySet<T> CopyOnWriteArraySet() {
        return new CopyOnWriteArraySet<>();
    }

    /**
     * 存取key-value类型的 Entity 一对一格式数据 进阶集合
     * key必须是唯一的 ，并且hashset是使用key存取数据保证的数据唯一性
     * 内部使用一个二维数组来保存数据
     * [key.hashcode()] [key2.hashcode()] [key3.hashcode()]
     * [entity] [entity] [entity]
     * <p>
     * 查询是通过散列行数，将二维数组转换成一个树，每个树的标识为hashcode，查询效率非常之高 详情参考 todo 红黑树
     * 如果key的hashcode相同，但eauals方法返回结果值判定不相等，会发生hashcode碰撞，会在当前key的hashcode下，新增一个数据保存改该条数据
     * [key.hashcode()-value] [key2.hashcode()-value] [key3.hashcode()-value]
     * [entity1] [entity] [entity]
     * [entity2] [] []
     * 这种情况会导致查找效率变慢,因为在查找时，即使确认了hashcode的位置，但是该位置下，还包含多个entity，此时还需要遍历entity根据key值判断 导致查询变慢
     * 此时，需要进行更完善的hashcode解析来处理这种问题
     */
    public HashMap<K, V> getHashMap(int initialCapacity) {
        return new HashMap<>(initialCapacity * 4 / 3 + 1);
    }

    /**
     * 基础 hashmap 的升级版
     * 在put等关键方法 使用 synchronized 修饰 实现线程安全
     *
     * @param initialCapacity
     * @return
     */
    public Hashtable<K, V> getHashtable(int initialCapacity) {
        return new Hashtable<>(initialCapacity * 4 / 3 + 1);
    }

    /**
     * hashmap线程安全升级版本
     * 通过使用 锁 替换 synchronized 优化锁的map
     * @param initialCapacity
     * @return
     */
    public ConcurrentHashMap<K, V> getConcurrentHashMap(int initialCapacity) {
        return new ConcurrentHashMap<>(initialCapacity * 4 / 3 + 1);
    }
}
