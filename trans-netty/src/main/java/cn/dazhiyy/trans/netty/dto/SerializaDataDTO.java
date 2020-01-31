package cn.dazhiyy.trans.netty.dto;

import io.netty.channel.ChannelId;
import lombok.Data;

/**
 * @author dazhi
 * @projectName easy-trans
 * @packageName cn.dazhiyy.trans.netty.dto
 * @className SerializaDataDTO
 * @description 转换成相应的数据
 * @date 2020/1/31 22:19
 */
@Data
public class SerializaDataDTO extends TransportSerializaDTO {

    /** 管道的唯一标识 */
    private ChannelId channelId;
}
