package cn.dazhiyy.trans.netty.handler;

import cn.dazhiyy.trans.netty.dto.SerializaDataDTO;
import cn.dazhiyy.trans.netty.manager.TransNetHandlerManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.commons.lang3.StringUtils;

/**
 * @author dazhi
 * @projectName easy-trans
 * @packageName cn.dazhiyy.trans.netty.handler
 * @className ClientJsonHandler
 * @description 序列号数据
 * @date 2020/2/1 15:39
 */
public class ClientJsonHandler extends SimpleChannelInboundHandler<String> {

    private TransNetHandlerManager transNetHandlerManager = new TransNetHandlerManager();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        if (StringUtils.isNotBlank(msg)) {
            SerializaDataDTO serializaDataDTO = transNetHandlerManager.serializaJsonData(msg, ctx.channel().id());
            if (serializaDataDTO != null) {
                transNetHandlerManager.handlerClientData(serializaDataDTO);
            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 通道活跃后,需要做的事
        transNetHandlerManager.activeClientData(ctx.channel().id());
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
