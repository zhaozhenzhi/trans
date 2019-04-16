package cn.dazhiyy.trans.server.queue.disruptor.constant;

import cn.dazhiyy.trans.server.queue.disruptor.enums.DisruptorWaitStrategyEnum;
import com.lmax.disruptor.dsl.ProducerType;

/**
 * @author dazhi
 * @projectName easy-trans
 * @packageName cn.dazhiyy.trans.server.queue.disruptor.constant
 * @className DisruptorPropertiesInitConst
 * @description disruptor容器 初始化常量值
 * @date 2019/3/31 12:02
 */
public class DisruptorPropertiesInitConst {

    public static final  Integer RING_BUFFER_SIZE= 1024;

    public static final ProducerType INIT_PRODUCER_TYPE = ProducerType.SINGLE;

    public static final Integer CONSUMER_NUM = 20;

    public static final DisruptorWaitStrategyEnum DEFAULT_WAIT_STRATEGY= DisruptorWaitStrategyEnum.BLOCKING;

}
