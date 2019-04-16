package cn.dazhiyy.trans.client.trans.transport.netty;

import cn.dazhiyy.trans.client.trans.config.NettyProperties;
import cn.dazhiyy.trans.client.trans.transport.SockerClient;
import cn.dazhiyy.trans.common.bean.TransDbConn;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author dazhi
 * @projectName easy-trans
 * @packageName cn.dazhiyy.trans.server.transport.netty
 * @className NettySocketServer
 * @description natty 服务器
 * @date 2019/3/30 18:08
 */
@Data
@Slf4j
public class NettySocketClient implements SockerClient {

    private Bootstrap client;

    private EventLoopGroup worker;

    private Channel channel;

    private TransDbConn transDbConn;

    private NettyProperties properties;

    private void init() {
        log.info("nettyClient init........");
        this.worker = new NioEventLoopGroup();
        this.client = new Bootstrap();

        client.group(worker)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
//                        ch.pipeline().addLast(new LoggingHandler(LogLevel.INFO));
                        ch.pipeline().addLast(new StringEncoder());
                        ch.pipeline().addLast(new StringDecoder());
                        ch.pipeline().addLast(new ClientHandler(transDbConn));
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
