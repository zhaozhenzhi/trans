package cn.dazhiyy.trans.server.queue;

import cn.dazhiyy.trans.common.transport.DbDataWrapper;

/**
 * @author dazhi
 * @projectName easy-trans
 * @packageName cn.dazhiyy.trans.server.queue
 * @className TransDataQueue
 * @description TODO
 * @date 2019/3/31 11:37
 */
public interface TransDataQueue {

    /**
     * 入队
     *
     * @param e
     * @return
     */
    boolean add(DbDataWrapper e);
}
