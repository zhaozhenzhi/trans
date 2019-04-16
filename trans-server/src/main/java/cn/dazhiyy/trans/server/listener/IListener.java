package cn.dazhiyy.trans.server.listener;


/**
 * @author dazhi
 * @projectName cn.dazhiyy.advert
 * @packageName cn.dazhiyy.ad.listener
 * @className IListener
 * @description 监听器 用于监听binlog事件
 * @date 2019/3/23 17:39
 */
public interface IListener<E,T> {

    /**
     * 注册监听
     */
    void registered(T t);

    /**
     * 去除监听
     *
     * @param t
     */
    void remove(T t);

    /**
     * 处理事件
     * @param data binlog传递过来的数据
     */
    void onEvent(E data);



}
