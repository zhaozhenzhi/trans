package cn.dazhiyy.trans.server.transport.netty;

import cn.dazhiyy.trans.server.transport.SocketServer;
import cn.dazhiyy.trans.server.transport.netty.handler.ServerHandler;
import cn.dazhiyy.trans.server.transport.netty.route.ClientMsgRouter;
import cn.dazhiyy.trans.server.transport.netty.yml.NettyProperties;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.AdaptiveRecvByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author dazhi
 * @projectName easy-trans
 * @packageName cn.dazhiyy.trans.server.transport.netty
 * @className NettySocketServer
 * @description natty 服务器
 * @date 2019/3/30 18:08
 */
@Slf4j
@Component
public class NettySocketServer implements SocketServer {

    private ServerBootstrap server;

    private EventLoopGroup boss;

    private EventLoopGroup worker;

    @Autowired
    private NettyProperties properties;

    @Autowired
    private ClientMsgRouter clientMsgRouter;

    @PostConstruct
    private void init() {
        log.info("nettyServer init........");
        this.boss = new NioEventLoopGroup();
        this.worker = new NioEventLoopGroup();
        this.server = new ServerBootstrap();

        server.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG,properties.getBacklog())
                .option(ChannelOption.RCVBUF_ALLOCATOR, AdaptiveRecvByteBufAllocator.DEFAULT)
                // 缓存的对象池 池化
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        //处理空闲状态事件的处理器
                       // ch.pipeline().addLast(new IdleStateHandler(5,7,10, TimeUnit.SECONDS));
                        ch.pipeline().addLast(new StringEncoder());
                        ch.pipeline().addLast(new StringDecoder());
                        ch.pipeline().addLast(new ServerHandler(clientMsgRouter));
                    }
                });
    }

    @Override
    public void start(Integer port) throws InterruptedException {
        ChannelFuture sync = server.bind(port).sync();
        sync.channel().closeFuture();
    }

    @Override
    public void close() {
        boss.shutdownGracefully();
        worker.shutdownGracefully();
    }
}
