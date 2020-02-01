package cn.dazhiyy.trans.netty.context;

import cn.dazhiyy.trans.netty.config.NettyConfig;
import cn.dazhiyy.trans.netty.config.TransNettyProperties;
import cn.dazhiyy.trans.netty.enums.ProtocolEnum;
import cn.dazhiyy.trans.netty.handler.AbstractTransNetHandler;
import cn.dazhiyy.trans.netty.handler.ClientJsonHandler;
import cn.dazhiyy.trans.netty.handler.ServerJsonHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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
    private static Set<AbstractTransNetHandler> serverHandlers;
    private static Set<AbstractTransNetHandler> clientHandlers;
    private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public static void setHandlerServer(ChannelPipeline pipeline) {
        String protocol = nettyConfig.get(TransNettyProperties.PROTOCOL);
        // JSON通信协议
        if (ProtocolEnum.JSON.name().equals(protocol)) {
            pipeline.addLast(new StringEncoder());
            pipeline.addLast(new StringDecoder());
            pipeline.addLast(new ServerJsonHandler());
        }
    }

    public static void setHandlerClient(ChannelPipeline pipeline) {
        String protocol = nettyConfig.get(TransNettyProperties.PROTOCOL);
        // JSON通信协议
        if (ProtocolEnum.JSON.name().equals(protocol)) {
            pipeline.addLast(new StringEncoder());
            pipeline.addLast(new StringDecoder());
            pipeline.addLast(new ClientJsonHandler());
        }
    }

    public static void setServerHandlers(List<AbstractTransNetHandler> handlers) {
         TransNettyContext.serverHandlers = new TreeSet<>(handlers);
    }

    public static void setClientHandlers(List<AbstractTransNetHandler> handlers) {
        TransNettyContext.clientHandlers = new TreeSet<>(handlers);
    }

    public static Set<AbstractTransNetHandler> getServerHandlers() {
        return serverHandlers;
    }
    public static Set<AbstractTransNetHandler> getClientHandlers(){
        return clientHandlers;
    }

    public static Boolean addChannel(Channel channel) {
        return channels.add(channel);
    }

    public static Boolean removeChannel(Channel channel) {
        return channels.remove(channel);
    }

    public static Channel findChannel(ChannelId id){
        return channels.find(id);
    }



}
