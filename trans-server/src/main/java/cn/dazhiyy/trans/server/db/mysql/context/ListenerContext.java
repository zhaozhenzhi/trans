package cn.dazhiyy.trans.server.db.mysql.context;

import cn.dazhiyy.trans.server.db.mysql.listener.QueueListener;

/**
 * @author dazhi
 * @projectName easy-trans
 * @packageName cn.dazhiyy.trans.server.context
 * @className ListenerContext
 * @description TODO
 * @date 2019/4/2 16:28
 */
public interface ListenerContext<T,E> extends QueueListener {

    /**
     * 发布数据
     * @param data 数据
     * @param connectName 连接名
     */
    void onSender(String connectName,T data);

    /**
     * 处理数据
     *
     * @param wrapper
     */
    void handler(E wrapper);

}
