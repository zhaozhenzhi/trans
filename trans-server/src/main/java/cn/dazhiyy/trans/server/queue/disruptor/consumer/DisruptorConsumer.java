package cn.dazhiyy.trans.server.queue.disruptor.consumer;

import cn.dazhiyy.trans.common.transport.DbDataWrapper;
import cn.dazhiyy.trans.server.db.mysql.context.ListenerContext;
import cn.dazhiyy.trans.server.queue.TransDataConsumer;
import com.lmax.disruptor.WorkHandler;
import lombok.Setter;

/**
 * @author dazhi
 * @projectName easy-trans
 * @packageName cn.dazhiyy.trans.server.queue.disruptor.consumer
 * @className DisruptorConsumer
 * @description TODO
 * @date 2019/3/31 13:43
 */
public class DisruptorConsumer implements TransDataConsumer<DbDataWrapper>, WorkHandler<DbDataWrapper> {
    @Setter
    private ListenerContext context;

    @Override
    public void onEvent(DbDataWrapper event) throws Exception {
        if (context == null ) {
            throw new Exception();
        }

        context.handler(event);
    }
}
