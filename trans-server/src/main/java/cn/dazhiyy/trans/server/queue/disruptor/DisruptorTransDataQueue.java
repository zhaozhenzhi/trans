package cn.dazhiyy.trans.server.queue.disruptor;

import cn.dazhiyy.trans.common.transport.DbDataWrapper;
import cn.dazhiyy.trans.server.db.mysql.context.ListenerContext;
import cn.dazhiyy.trans.server.queue.TransDataQueue;
import cn.dazhiyy.trans.server.queue.disruptor.factory.DisruptorContainerFactory;
import cn.dazhiyy.trans.server.queue.disruptor.producer.DisruptorProducer;
import cn.dazhiyy.trans.server.queue.disruptor.yml.DisruptorProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author dazhi
 * @projectName easy-trans
 * @packageName cn.dazhiyy.trans.server.queue.disruptor
 * @className DisruptorTransDataQueue
 * @description 使用disruptor实现系统架构队列
 * @date 2019/3/31 11:38
 */
@Slf4j
@Component
public class DisruptorTransDataQueue implements TransDataQueue {

    private DisruptorProducer disruptorProducer;

    @Autowired
    private DisruptorProperties properties;

    @Autowired
    private ListenerContext context;

    @PostConstruct
    public void init(){
        log.info("disruptor container init.....");
        DisruptorContainerFactory factory = DisruptorContainerFactory.getInstance();
        factory.init(properties,context);
        disruptorProducer = factory.getProducer();
    }

    @Override
    public boolean add(DbDataWrapper data) {
        return disruptorProducer.onProduce(data);
    }
}
