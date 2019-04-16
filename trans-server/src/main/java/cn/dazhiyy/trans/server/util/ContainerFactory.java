package cn.dazhiyy.trans.server.util;

import java.util.Map;
import java.util.function.Supplier;

/**
 * @author dazhi
 * @projectName cn.dazhiyy.advert
 * @packageName cn.dazhiyy.ad.common.factory
 * @className SetFactory
 * @description TODO
 * @date 2019/3/19 21:29
 */
public class ContainerFactory {

    /**
     * 判断map中的key是否有值，如果没有创建一个新值
     * @param key 键
     * @param map map
     * @param factory 工厂
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K,V> V setorCreate(K key, Map<K,V> map, Supplier<V> factory){
        return map.computeIfAbsent(key,k -> factory.get());
    }


    /**
     * 返回bean的实例
     * @param factory 工厂
     * @param <V>
     * @return
     */
    public static <V> V newInstance(Supplier<V> factory){
        return factory.get();
    }


}
