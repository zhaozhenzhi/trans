package cn.dazhiyy.trans.netty.handler;

import cn.dazhiyy.trans.netty.dto.SerializaDataDTO;
import io.netty.channel.ChannelId;

/**
 * @author dazhi
 * @projectName easy-trans
 * @packageName cn.dazhiyy.trans.netty.handler
 * @className TransNetHandler
 * @description trans网络请求处理器，对外的
 * @date 2020/1/31 21:19
 */
interface TransNetHandler {

    /**
     * 处理数据
     *
     * @param transportSerializaDTO
     * @return
     */
    Boolean doHandler(SerializaDataDTO transportSerializaDTO);

    /**
     * 通道活跃后,回调
     *
     * @param channelId
     */
    void activeClient(ChannelId channelId);

}
