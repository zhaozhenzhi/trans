package cn.dazhiyy.trans.server.queue.disruptor.enums;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.LiteBlockingWaitStrategy;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.YieldingWaitStrategy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author dazhi
 * @projectName easy-trans
 * @packageName cn.dazhiyy.trans.server.queue.disruptor.enums
 * @className DisruptorWaitStrategyEnum
 * @description TODO
 * @date 2019/3/31 12:14
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum  DisruptorWaitStrategyEnum {

    BLOCKING(new BlockingWaitStrategy()),
    BUSY_SPIN(new BusySpinWaitStrategy()),
    LITE_BLOCKING(new LiteBlockingWaitStrategy()),
    SLEEPING(new SleepingWaitStrategy()),
    YIELDING(new YieldingWaitStrategy()),
//    TIMEOUT_BLOCKING(new TimeoutBlockingWaitStrategy()),
//    PHASED_BACK_OFF(new PhasedBackoffWaitStrategy()),
//    LITE_TIMEOUT_BLOCKING(new LiteTimeoutBlockingWaitStrategy());
    ;

    private WaitStrategy waitStrategy;

}
