package cn.dazhiyy.trans.server.queue.disruptor.factory;

import cn.dazhiyy.trans.common.transport.DbDataWrapper;
import cn.dazhiyy.trans.server.db.mysql.context.ListenerContext;
import cn.dazhiyy.trans.server.queue.disruptor.consumer.DisruptorConsumer;
import cn.dazhiyy.trans.server.queue.disruptor.producer.DisruptorProducer;
import cn.dazhiyy.trans.server.queue.disruptor.yml.DisruptorProperties;
import com.google.common.collect.Maps;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

import java.util.Map;

/**
 * @author dazhi
 * @projectName easy-trans
 * @packageName cn.dazhiyy.trans.server.queue.disruptor.factory
 * @className DisruptorContainerFactory
 * @description disruptor容器的工厂类
 * @date 2019/3/31 11:41
 */
public class DisruptorContainerFactory {

    private Disruptor<DbDataWrapper> disruptor;

    /**
     * TODO 2019/3/31 这个版本只支持单生产者
     */
    private DisruptorProducer disruptorProducer;
    private RingBuffer<DbDataWrapper> ringBuffer;
    private Map<String, DisruptorConsumer> map = Maps.newConcurrentMap();
    private DisruptorProperties properties;
    private ListenerContext context;


    private DisruptorContainerFactory(){
        if (DisruptorHolder.factory!=null) {
            throw new RuntimeException("请使用getInstance()方法获得实例");
        }
    }

    public void init(DisruptorProperties properties,ListenerContext context){
        // 1.构建 disruptor容器
        this.disruptor = new Disruptor<>(
                new ProducerDataFactory(),
                properties.getRingBufferSize(),
                new MyThreadFactory(),
                properties.getType(),
                properties.getWaitStrategy()
        );

        // 2 构建消费者
        DisruptorConsumer[] moreInstance = DisruptorConsumerFactory.getMoreInstance(
                properties.getConsumerNum(),
                map,
                properties.getConsumerNamePrefix(),
                context
        );

        // 3 绑定消费者
        this.disruptor.handleEventsWithWorkerPool(moreInstance);
        this.ringBuffer = this.disruptor.start();
        this.properties = properties;
        this.context = context;
    }

    public DisruptorProducer getProducer(){
        if (disruptorProducer == null) {
            disruptorProducer = new DisruptorProducer(ringBuffer);
        }
        return disruptorProducer;
    }


    public void destroy(){
        disruptor.shutdown();
    }

    public static DisruptorContainerFactory getInstance(){
        return DisruptorHolder.factory;
    }


    private static class DisruptorHolder{
        private static DisruptorContainerFactory factory = new DisruptorContainerFactory();
    }

}
