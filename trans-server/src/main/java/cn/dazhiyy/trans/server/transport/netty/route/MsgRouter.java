package cn.dazhiyy.trans.server.transport.netty.route;

import io.netty.channel.Channel;

/**
 * @author dazhi
 * @projectName easy-trans
 * @packageName cn.dazhiyy.trans.server.transport.netty.route
 * @className MsgRouter
 * @description TODO
 * @date 2019/4/4 23:05
 */
public interface MsgRouter {

    /**
     * 是否支持路由
     *
     * @param eventData
     * @return
     */
    Boolean isSupport(String eventData);

    /**
     * 路由处理
     *
     * @param msg
     * @param channel
     */
    void route(String msg, Channel channel);
}
