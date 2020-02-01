package cn.dazhiyy.trans.netty.manager;

import cn.dazhiyy.trans.netty.context.TransNettyContext;
import cn.dazhiyy.trans.netty.dto.SerializaDataDTO;
import cn.dazhiyy.trans.netty.dto.TransportSendDTO;
import cn.dazhiyy.trans.netty.handler.AbstractTransNetHandler;
import com.alibaba.fastjson.JSON;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Set;

/**
 * @author dazhi
 * @projectName easy-trans
 * @packageName cn.dazhiyy.trans.netty.manager
 * @className TransNetHandlerManager
 * @description 用于管理相关数据的处理器
 * @date 2020/1/31 21:51
 */
public class TransNetHandlerManager {

    /**
     * 处理远程客户端的数据
     *
     * @param serializaDataDTO 封装好的数据DTO
     */
    public void handlerServerData(SerializaDataDTO serializaDataDTO) {
        Set<AbstractTransNetHandler> handlers = TransNettyContext.getServerHandlers();

        if (CollectionUtils.isNotEmpty(handlers)) {
            for (AbstractTransNetHandler handler : handlers) {
                if (!handler.doHandler(serializaDataDTO)) {
                    // 放回false 表示拦截,不继续执行下面的处理器了
                    break;
                }
            }
        }
    }

    /**
     * 处理远程服务端的数据
     *
     * @param serializaDataDTO 封装好的数据DTO
     */
    public void handlerClientData(SerializaDataDTO serializaDataDTO) {
        Set<AbstractTransNetHandler> handlers = TransNettyContext.getClientHandlers();

        if (CollectionUtils.isNotEmpty(handlers)) {
            for (AbstractTransNetHandler handler : handlers) {
                if (!handler.doHandler(serializaDataDTO)) {
                    // 放回false 表示拦截,不继续执行下面的处理器了
                    break;
                }
            }
        }
    }

    public SerializaDataDTO serializaJsonData(String msg, ChannelId id){
        TransportSendDTO transportSerializaDTO = JSON.parseObject(msg, TransportSendDTO.class);
        if (transportSerializaDTO != null) {
            SerializaDataDTO dataDTO = new SerializaDataDTO();
            dataDTO.setChannelId(id);
            dataDTO.setBehaviorCode(transportSerializaDTO.getBehaviorCode());
            dataDTO.setData(transportSerializaDTO.getData());
            dataDTO.setIp(transportSerializaDTO.getIp());
            dataDTO.setBehaviorMsg(transportSerializaDTO.getBehaviorMsg());
            dataDTO.setProtocol(transportSerializaDTO.getProtocol());
            dataDTO.setTimestamp(transportSerializaDTO.getTimestamp());
            return dataDTO;
        }
        return null;
    }

    public void registered(Channel channel) {
        TransNettyContext.addChannel(channel);
    }

    public void activeClientData(ChannelId channelId) {
        Set<AbstractTransNetHandler> handlers = TransNettyContext.getClientHandlers();

        for (AbstractTransNetHandler handler : handlers) {
            handler.activeClient(channelId);
        }
    }
}
