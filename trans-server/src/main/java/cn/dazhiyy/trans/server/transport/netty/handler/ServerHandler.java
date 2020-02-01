package cn.dazhiyy.trans.server.transport.netty.handler;

import cn.dazhiyy.trans.netty.dto.SerializaDataDTO;
import cn.dazhiyy.trans.netty.handler.TransNetHandlerAdapter;
import cn.dazhiyy.trans.server.db.mysql.context.ListenerContext;
import cn.dazhiyy.trans.server.db.mysql.listener.EventWrapper;
import cn.dazhiyy.trans.server.transport.netty.route.MsgRouter;
import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author dazhi
 * @projectName easy-trans
 * @packageName cn.dazhiyy.trans.server.transport.netty.handler
 * @className ServerHandler
 * @description TODO
 * @date 2020/1/31 22:02
 */
@Slf4j
@Component
public class ServerHandler extends TransNetHandlerAdapter {

    @Autowired
    private ListenerContext context;

    @Autowired
    private List<MsgRouter> routers;

    @Override
    public Boolean doHandler(SerializaDataDTO transportSerializaDTO) {
        log.debug("接受client消息:{}", JSON.toJSONString(transportSerializaDTO));
        handler(transportSerializaDTO);
        return Boolean.FALSE;
    }


    private void handler(SerializaDataDTO transportSerializaDTO){
        for (MsgRouter router : routers) {
            if (router.isSupport(transportSerializaDTO.getBehaviorCode())){
                Object data = transportSerializaDTO.getData();
                // TODO 可以加一层日志记录，记录注册次数
                if (data!=null) {
                    router.route(transportSerializaDTO.getData().toString(),transportSerializaDTO.getChannelId());
                }
            }
        }
    }


    public void remove(ChannelId channel) {
        EventWrapper eventWrapper = new EventWrapper();
        eventWrapper.setChannel(channel);
        context.remove(eventWrapper);
    }
}
