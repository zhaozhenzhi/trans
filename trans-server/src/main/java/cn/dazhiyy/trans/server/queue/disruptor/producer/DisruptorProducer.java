package cn.dazhiyy.trans.server.queue.disruptor.producer;

import cn.dazhiyy.trans.common.transport.DbDataWrapper;
import cn.dazhiyy.trans.server.queue.TransDataProducer;
import com.lmax.disruptor.RingBuffer;

/**
 * @author dazhi
 * @projectName easy-trans
 * @packageName cn.dazhiyy.trans.server.queue.disruptor.producer
 * @className DisruptorProducer
 * @description TODO
 * @date 2019/3/31 11:53
 */
public class DisruptorProducer implements TransDataProducer<DbDataWrapper> {

    private RingBuffer<DbDataWrapper> ringBuffer;

    public DisruptorProducer(RingBuffer<DbDataWrapper> ringBuffer){
        this.ringBuffer = ringBuffer;
    }


    @Override
    public Boolean onProduce(DbDataWrapper data) {
        long next = ringBuffer.next();

        try{
            DbDataWrapper data1 = ringBuffer.get(next);
            data1.setConnectName(data.getConnectName());
            data1.setDate(data.getDate());
        }finally {
            ringBuffer.publish(next);
        }
        return Boolean.TRUE;
    }
}
