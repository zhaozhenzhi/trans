package cn.dazhiyy.trans.netty;

import cn.dazhiyy.trans.SocketServer;
import cn.dazhiyy.trans.netty.config.TransNettyProperties;
import cn.dazhiyy.trans.netty.context.TransNettyContext;
import cn.dazhiyy.trans.netty.handler.AbstractTransNetHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.List;


/**
 * @author dazhi
 * @projectName easy-trans
 * @packageName cn.dazhiyy.trans.server.transport.netty
 * @className NettySocketServer
 * @description natty 服务器
 * @date 2019/3/30 18:08
 */
public class NettySocketServer implements SocketServer {

    private ServerBootstrap server;
    private EventLoopGroup boss;
    private EventLoopGroup worker;

    public NettySocketServer(List<AbstractTransNetHandler> handlers){

        // 设置处理器
        TransNettyContext.setServerHandlers(handlers);

        // 初始化
        init();
    }

    /**
     * 初始化服务
     */
    private void init() {
        this.boss = new NioEventLoopGroup();
        this.worker = new NioEventLoopGroup();
        this.server = new ServerBootstrap();
        server.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, Integer.parseInt(TransNettyContext.nettyConfig.get(TransNettyProperties.SO_BACKLOG)))
                // 缓存的对象池 池化
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .handler(getLogHandler())
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        //处理空闲状态事件的处理器
                        TransNettyContext.setHandlerServer(ch.pipeline());
                    }
                });

    }

    /**
     * 获得日志级别
     *
     * @return 日志处理器
     */
    private LoggingHandler getLogHandler(){
        String logLevel = TransNettyContext.nettyConfig.get(TransNettyProperties.NETTY_LOG);

        for (LogLevel value : LogLevel.values()) {
            if (value.name().equals(logLevel)) {
                return new LoggingHandler(value);
            }
        }

        return new LoggingHandler();
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
