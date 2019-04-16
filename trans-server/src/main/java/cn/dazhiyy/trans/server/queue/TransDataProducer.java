package cn.dazhiyy.trans.server.queue;

/**
 * @author dazhi
 * @projectName easy-trans
 * @packageName cn.dazhiyy.trans.server.queue.disruptor
 * @className TransDataProducer
 * @description 队列生产者
 * @date 2019/3/31 11:49
 */
public interface TransDataProducer<E> {

    /**
     * 生产数据
     * @param data 数据
     * @return
     */
    Boolean onProduce(E data);
}
