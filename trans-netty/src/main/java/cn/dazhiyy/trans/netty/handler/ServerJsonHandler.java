package cn.dazhiyy.trans.netty.handler;

import cn.dazhiyy.trans.netty.dto.SerializaDataDTO;
import cn.dazhiyy.trans.netty.dto.TransportSerializaDTO;
import cn.dazhiyy.trans.netty.manager.TransNetHandlerManager;
import com.alibaba.fastjson.JSON;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.commons.lang3.StringUtils;

/**
 * @author dazhi
 * @projectName easy-trans
 * @packageName cn.dazhiyy.trans.server.transport.netty.handler
 * @className ServerHandler
 * @description 序列号数据
 * @date 2019/3/30 18:45
 */
public class ServerJsonHandler extends SimpleChannelInboundHandler<String> {

    private TransNetHandlerManager transNetHandlerManager = new TransNetHandlerManager();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        if (StringUtils.isNotBlank(msg)) {
            TransportSerializaDTO transportSerializaDTO = JSON.parseObject(msg, TransportSerializaDTO.class);
            if (transportSerializaDTO != null) {
                Channel channel = ctx.channel();
                SerializaDataDTO dataDTO = new SerializaDataDTO();
                dataDTO.setChannelId(channel.id());
                dataDTO.setCode(transportSerializaDTO.getCode());
                dataDTO.setData(transportSerializaDTO.getData());
                dataDTO.setIp(transportSerializaDTO.getIp());
                dataDTO.setMsg(transportSerializaDTO.getMsg());
                dataDTO.setProtocol(transportSerializaDTO.getProtocol());
                dataDTO.setTimestamp(transportSerializaDTO.getTimestamp());
                transNetHandlerManager.handlerData(dataDTO);
            } else {
                // 打一条日志

            }
        }
    }



}

