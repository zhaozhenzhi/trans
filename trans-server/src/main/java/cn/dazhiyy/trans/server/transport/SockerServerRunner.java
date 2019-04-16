package cn.dazhiyy.trans.server.transport;

import cn.dazhiyy.trans.server.transport.netty.NettySocketServer;
import cn.dazhiyy.trans.server.transport.netty.yml.NettyProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

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
    private NettySocketServer server;

    @Autowired
    private NettyProperties properties;

    @Override
    public void run(String... args) throws Exception {
        log.info("nettyServer start,listener port {}.........",properties.getPort());
        server.start(properties.getPort());
    }
}
