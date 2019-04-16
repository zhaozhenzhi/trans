package cn.dazhiyy.trans.server.transport.netty.handler;

import cn.dazhiyy.trans.server.transport.netty.route.ClientMsgRouter;
import cn.dazhiyy.trans.server.transport.netty.serializa.SerializaData;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author dazhi
 * @projectName easy-trans
 * @packageName cn.dazhiyy.trans.server.transport.netty.handler
 * @className ServerHandler
 * @description TODO
 * @date 2019/3/30 18:45
 */
@Slf4j
public class ServerHandler extends SimpleChannelInboundHandler<String> {

    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public SerializaData serializaData = new SerializaData();

    private ClientMsgRouter clientMsgRouter;

    public ServerHandler(ClientMsgRouter clientMsgRouter){
        this.clientMsgRouter = clientMsgRouter;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        log.info("接受client消息:{}",msg);
        clientMsgRouter.router(msg,ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        log.info("client{},减除监听",ctx.channel().remoteAddress());
        clientMsgRouter.remove(ctx.channel());
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("5678");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(1111);
    }


    //    @Override
//    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
//        IdleStateEvent event =(IdleStateEvent)evt;
//
//        String eventType = null;
//
//        switch (event.state()){
//            case READER_IDLE:
//                eventType = "读空闲";
//                break;
//            case WRITER_IDLE:
//                eventType = "写空闲";
//                break;
//            case ALL_IDLE:
//                eventType ="读写空闲";
//                break;
//            default: eventType="";
//        }
//        log.info(ctx.channel().remoteAddress() + "超时事件：" +eventType);
//        ctx.channel().close();
//    }

}

