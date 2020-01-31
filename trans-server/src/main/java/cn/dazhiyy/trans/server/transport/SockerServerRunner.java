package cn.dazhiyy.trans.server.transport;

import cn.dazhiyy.trans.SocketServer;
import cn.dazhiyy.trans.netty.NettySocketServer;
import cn.dazhiyy.trans.netty.config.TransNettyProperties;
import cn.dazhiyy.trans.netty.context.TransNettyContext;
import cn.dazhiyy.trans.netty.handler.AbstractTransNetHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author dazhi
 * @projectName easy-trans
 * @packageName cn.dazhiyy.trans.server.transport
 * @className SockerServerRunner
 * @description 服务器启动类 (用于spring容器加载)
 * @date 2019/3/30 18:00
 */
@Slf4j
@Component
public class SockerServerRunner implements CommandLineRunner {

    @Autowired
    private List<AbstractTransNetHandler> handlers;

    @Override
    public void run(String... args) throws Exception {
        SocketServer server = new NettySocketServer(handlers);
        Integer port = Integer.parseInt(TransNettyContext.nettyConfig.get(TransNettyProperties.PORT));
        log.info("nettyServer start,listener port {}.........", port);
        server.start(port);
    }

}
