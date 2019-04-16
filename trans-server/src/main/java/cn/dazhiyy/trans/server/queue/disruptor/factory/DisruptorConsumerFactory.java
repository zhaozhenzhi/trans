package cn.dazhiyy.trans.server.queue.disruptor.factory;

import cn.dazhiyy.trans.server.db.mysql.context.ListenerContext;
import cn.dazhiyy.trans.server.queue.disruptor.consumer.DisruptorConsumer;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * @author dazhi
 * @projectName easy-trans
 * @packageName cn.dazhiyy.trans.server.queue.disruptor.factory
 * @className DisruptorConsumerFactory
 * @description 消费者工厂类
 * @date 2019/3/31 13:45
 */
public class DisruptorConsumerFactory {

    private static final String DEFAULT_PREFIX = "consumer-";

    private DisruptorConsumerFactory(){}

    /**
     * 获得简单实例
     * @return
     */
    public static DisruptorConsumer getSimpleInstance(){
        return new DisruptorConsumer();
    }

    /**
     * 获得消费者数组，并且填充消费者映射
     *
     * @param num 消费者数量
     * @param instanceMap 消费者映射关系
     * @param prefix 消费者前缀
     * @return
     */
    public static DisruptorConsumer[] getMoreInstance(Integer num, Map<String,DisruptorConsumer> instanceMap, String prefix, ListenerContext context){
        DisruptorConsumer[] consumers = new DisruptorConsumer[num];
        for (int i = 0; i < num; i++) {
            DisruptorConsumer consumer = new DisruptorConsumer();
            consumer.setContext(context);
            consumers[i] = consumer;
            if (MapUtils.isNotEmpty(instanceMap)) {
                instanceMap.put((StringUtils.isBlank(prefix)?DEFAULT_PREFIX:prefix)+i, consumer);
            }
        }
        return consumers;
    }

    /**
     * 获得消费者数组，并且填充消费者映射
     *
     * @param num 消费者数量
     * @param instanceMap 消费者映射关系
     * @return
     */
    public static DisruptorConsumer[] getMoreInstance(Integer num, Map<String,DisruptorConsumer> instanceMap , ListenerContext context){
        return getMoreInstance(num,instanceMap,null,context);
    }

    /**
     * 获得消费者数组，并且填充消费者映射
     *
     * @param num 消费者数量
     * @return
     */
    public static DisruptorConsumer[] getMoreInstance(Integer num, ListenerContext context){
        return getMoreInstance(num,null,context);
    }

}
