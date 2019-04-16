package cn.dazhiyy.trans.server.queue.disruptor.factory;

import cn.dazhiyy.trans.common.transport.DbDataWrapper;
import com.lmax.disruptor.EventFactory;

/**
 * @author dazhi
 * @projectName easy-trans
 * @packageName cn.dazhiyy.trans.server.queue.disruptor.factory
 * @className ProducerEventFactory
 * @description TODO
 * @date 2019/3/31 11:56
 */
public class ProducerDataFactory implements EventFactory<DbDataWrapper> {

    @Override
    public DbDataWrapper newInstance() {
        return new DbDataWrapper();
    }
}
