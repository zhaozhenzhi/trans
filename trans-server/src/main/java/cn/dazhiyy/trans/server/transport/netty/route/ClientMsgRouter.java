package cn.dazhiyy.trans.server.transport.netty.route;

import cn.dazhiyy.trans.common.transport.TransportData;
import cn.dazhiyy.trans.server.db.mysql.context.ListenerContext;
import cn.dazhiyy.trans.server.db.mysql.listener.EventWrapper;
import com.alibaba.fastjson.JSON;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author dazhi
 * @projectName easy-trans
 * @packageName cn.dazhiyy.trans.server.transport.netty.route
 * @className ClientMsgRouter
 * @description TODO
 * @date 2019/4/4 23:02
 */
@Slf4j
@Component
public class ClientMsgRouter {

    @Autowired
    private List<MsgRouter> routers;

    @Autowired
    private ListenerContext context;

    public void router(String msg, Channel channel){
        TransportData transportData = null;
        try {
            transportData = JSON.parseObject(msg, TransportData.class);
        } catch (Exception e) {
            log.error("客户端:{} 传递数据转换错误！",channel.remoteAddress());
        }

        if (transportData != null){
            handler(transportData,channel);
        }
    }

    private void handler(TransportData transportData,Channel channel){
        for (MsgRouter router : routers) {
            if (router.isSupport(transportData.getEventCode())){
                router.route(transportData.getData().toString(),channel);
            }
        }
    }


    public void remove(Channel channel) {
        EventWrapper eventWrapper = new EventWrapper();
        eventWrapper.setChannel(channel);
        context.remove(eventWrapper);
    }
}
