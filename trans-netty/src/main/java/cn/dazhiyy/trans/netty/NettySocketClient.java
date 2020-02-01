package cn.dazhiyy.trans.netty;

import cn.dazhiyy.trans.SockerClient;
import cn.dazhiyy.trans.netty.context.TransNettyContext;
import cn.dazhiyy.trans.netty.handler.AbstractTransNetHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Data;

import java.util.List;

/**
 * @author dazhi
 * @projectName easy-trans
 * @packageName cn.dazhiyy.trans.server.transport.netty
 * @className NettySocketServer
 * @description natty 服务器
 * @date 2019/3/30 18:08
 */
@Data
public class NettySocketClient implements SockerClient {

    private Bootstrap client;

    private EventLoopGroup worker;

    private Channel channel;

    public NettySocketClient(List<AbstractTransNetHandler> handlers){
        // 设置处理器
        TransNettyContext.setClientHandlers(handlers);
    }

    private void init() {
        this.worker = new NioEventLoopGroup();
        this.client = new Bootstrap();

        client.group(worker)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        TransNettyContext.setHandlerClient(ch.pipeline());
                    }
                });


    }

    @Override
    public void connection(String ip, Integer port) throws InterruptedException {
        init();
        ChannelFuture connect = client.connect(ip, port).sync();
        this.channel = connect.channel();
    }

    @Override
    public void close() {
        worker.shutdownGracefully();
    }
}
