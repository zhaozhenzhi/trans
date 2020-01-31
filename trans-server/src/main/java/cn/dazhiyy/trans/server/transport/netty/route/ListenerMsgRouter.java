package cn.dazhiyy.trans.server.transport.netty.route;

import cn.dazhiyy.trans.common.bean.TransDbConn;
import cn.dazhiyy.trans.common.transport.EventEnum;
import cn.dazhiyy.trans.netty.enums.TransportStatusEnum;
import cn.dazhiyy.trans.server.db.mysql.context.ListenerContext;
import cn.dazhiyy.trans.server.db.mysql.listener.EventWrapper;
import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author dazhi
 * @projectName easy-trans
 * @packageName cn.dazhiyy.trans.server.transport.netty.route
 * @className ListenerMsgRouter
 * @description TODO
 * @date 2019/4/4 23:08
 */
@Component
public class ListenerMsgRouter implements MsgRouter {

    private EventEnum event = EventEnum.LISTEN;

    @Autowired
    private ListenerContext context;

    @Override
    public Boolean isSupport(Integer code){
        return TransportStatusEnum.REGISTERED.getCode().equals(code);
    }

    @Override
    public void route(String msg, ChannelId channelId) {
        TransDbConn transDbConn = JSON.parseObject(msg, TransDbConn.class);
        if (transDbConn != null) {
            // 注册监听
            EventWrapper eventWrapper = new EventWrapper();
            eventWrapper.setChannel(channelId);
            eventWrapper.setTransDbConn(transDbConn);
            context.registered(eventWrapper);
        }
    }


}
