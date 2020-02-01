package cn.dazhiyy.trans.netty.handler;

import cn.dazhiyy.trans.netty.dto.SerializaDataDTO;
import cn.dazhiyy.trans.netty.manager.TransNetHandlerManager;
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
            SerializaDataDTO serializaDataDTO = transNetHandlerManager.serializaJsonData(msg, ctx.channel().id());
            if (serializaDataDTO != null) {
                transNetHandlerManager.handlerServerData(serializaDataDTO);
            }
        }
    }

    /**
     * channel注册的时候回调
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        transNetHandlerManager.registered(ctx.channel());
    }
}

