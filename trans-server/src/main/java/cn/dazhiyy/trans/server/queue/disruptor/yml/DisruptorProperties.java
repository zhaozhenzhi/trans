package cn.dazhiyy.trans.server.queue.disruptor.yml;

import cn.dazhiyy.trans.server.queue.disruptor.constant.DisruptorPropertiesInitConst;
import cn.dazhiyy.trans.server.queue.disruptor.enums.DisruptorWaitStrategyEnum;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.ProducerType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author dazhi
 * @projectName easy-trans
 * @packageName cn.dazhiyy.trans.server.queue.disruptor.yml
 * @className DisruptorProperties
 * @description TODO
 * @date 2019/3/31 12:00
 */
@Data
@Component
@ConfigurationProperties(prefix = "trans.queue.disruptor")
public class DisruptorProperties {

    private Integer ringBufferSize = DisruptorPropertiesInitConst.RING_BUFFER_SIZE;

    private Integer consumerNum = DisruptorPropertiesInitConst.CONSUMER_NUM;

    private ProducerType type = DisruptorPropertiesInitConst.INIT_PRODUCER_TYPE;

    private String consumerNamePrefix;

    private DisruptorWaitStrategyEnum waitStrategy = DisruptorPropertiesInitConst.DEFAULT_WAIT_STRATEGY;

    public WaitStrategy getWaitStrategy(){
        return this.waitStrategy.getWaitStrategy();
    }
}
