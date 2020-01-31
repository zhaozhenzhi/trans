package cn.dazhiyy.trans.netty.context;

import cn.dazhiyy.trans.netty.config.NettyConfig;
import cn.dazhiyy.trans.netty.config.TransNettyProperties;
import cn.dazhiyy.trans.netty.enums.ProtocolEnum;
import com.google.common.collect.Lists;
import io.netty.channel.ChannelHandler;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * @author dazhi
 * @projectName easy-trans
 * @packageName cn.dazhiyy.trans.netty.context
 * @className TransNettyContext
 * @description 上下文对象
 * @date 2020/1/31 12:19
 */
public class TransNettyContext {

    public static NettyConfig nettyConfig = new NettyConfig();

    private static List<ChannelHandler> channelHandlers;

    public static List<ChannelHandler> getHandlers(){
        if (CollectionUtils.isEmpty(channelHandlers)) {
            String protocol = nettyConfig.get(TransNettyProperties.PROTOCOL);
            channelHandlers = Lists.newArrayList();
            // JSON通信协议
            if (ProtocolEnum.JSON.name().equals(protocol)) {
                channelHandlers.add(new StringEncoder());
                channelHandlers.add(new StringDecoder());
            }
        }
        return channelHandlers;
    }


}
