package cn.dazhiyy.trans.netty.config;

import cn.dazhiyy.trans.netty.constant.NettyOptionConst;

import java.util.HashMap;

/**
 * @author dazhi
 * @projectName easy-trans
 * @packageName cn.dazhiyy.trans.netty.config
 * @className TransNettyProperties
 * @description properties属性名
 * @date 2020/1/31 12:21
 */
public class TransNettyProperties extends HashMap<String, String> {

    public static final String PORT = "trans.net.server.port";
    public static final String SO_BACKLOG = "trans.net.server.so.backlog";
    public static final String NETTY_LOG = "trans.net.server.log.level";
    public static final String PROTOCOL = "trans.net.protocol";

    TransNettyProperties(){
        super();
        this.put(PORT, NettyOptionConst.PORT.toString());
        this.put(SO_BACKLOG, NettyOptionConst.SO_BACKLOG.toString());
        this.put(NETTY_LOG,NettyOptionConst.NETTY_LOG);
        this.put(PROTOCOL,NettyOptionConst.PROTOCOL);
    }
}
