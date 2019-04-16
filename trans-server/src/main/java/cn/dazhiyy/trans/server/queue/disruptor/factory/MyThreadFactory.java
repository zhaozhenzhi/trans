package cn.dazhiyy.trans.server.queue.disruptor.factory;

import java.util.concurrent.ThreadFactory;

/**
 * @author dazhi
 * @projectName easy-trans
 * @packageName cn.dazhiyy.trans.server.queue.disruptor.factory
 * @className MyThreadFactory
 * @description TODO
 * @date 2019/3/31 12:07
 */
public class MyThreadFactory implements ThreadFactory {


    @Override
    public Thread newThread(Runnable run) {
        return new Thread(run);
    }
}
