package cn.dazhiyy.trans.client.trans.transport.netty;

import cn.dazhiyy.trans.common.bean.TransDbConn;
import cn.dazhiyy.trans.common.util.SerialUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author dazhi
 * @projectName easy-trans
 * @packageName cn.dazhiyy.trans.client.transport.netty.handler
 * @className ClientHandler
 * @description TODO
 * @date 2019/3/31 00:06
 */
public class ClientHandler extends SimpleChannelInboundHandler<String> {

    private TransDbConn transDbConn;

    public ClientHandler(TransDbConn transDbConn){
        this.transDbConn = transDbConn;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(SerialUtil.serial(transDbConn));
        ctx.writeAndFlush(SerialUtil.serial(transDbConn));
    }
}
