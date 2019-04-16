package cn.dazhiyy.trans.client.trans.config;

import cn.dazhiyy.trans.client.trans.config.constant.NettyOptionConst;
import lombok.Data;

/**
 * @author dazhi
 * @projectName easy-trans
 * @packageName cn.dazhiyy.trans.server.transport.netty.yml
 * @className nettyYml
 * @description TODO
 * @date 2019/3/30 18:12
 */
@Data
public class NettyProperties {

    private Integer port = NettyOptionConst.PORT;

    private Integer backlog = NettyOptionConst.SO_BACKLOG;

    private String ip;
}
