package cn.dazhiyy.trans.client.trans.transport.netty;

import cn.dazhiyy.trans.common.bean.TransDbConn;
import cn.dazhiyy.trans.netty.context.TransNettyContext;
import cn.dazhiyy.trans.netty.dto.SerializaDataDTO;
import cn.dazhiyy.trans.netty.dto.TransportSendDTO;
import cn.dazhiyy.trans.netty.enums.ProtocolEnum;
import cn.dazhiyy.trans.netty.enums.TransportStatusEnum;
import cn.dazhiyy.trans.netty.handler.AbstractTransNetHandler;
import com.alibaba.fastjson.JSON;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author dazhi
 * @projectName easy-trans
 * @packageName cn.dazhiyy.trans.client.transport.netty.handler
 * @className ClientHandler
 * @description TODO
 * @date 2019/3/31 00:06
 */
@Data
@Component
public class ClientHandler extends AbstractTransNetHandler {

    private TransDbConn transDbConn;



    @Override
    public Boolean doHandler(SerializaDataDTO transportSerializaDTO) {
        System.out.println(JSON.toJSONString(transportSerializaDTO));
        return Boolean.FALSE;
    }

    @Override
    public void activeClient(ChannelId channelId) {
        Channel channel = TransNettyContext.findChannel(channelId);
        TransportSendDTO transportSendDTO = new TransportSendDTO();
        transportSendDTO.setBehaviorCode(TransportStatusEnum.REGISTERED.getCode());
        transportSendDTO.setBehaviorMsg(TransportStatusEnum.REGISTERED.getMsg());
        transportSendDTO.setData(JSON.toJSONString(transDbConn));
        transportSendDTO.setIp(channel.remoteAddress().toString());
        transportSendDTO.setProtocol(ProtocolEnum.JSON.name());
        transportSendDTO.setTimestamp(System.currentTimeMillis());

        channel.writeAndFlush(JSON.toJSONString(transportSendDTO));
    }
}
