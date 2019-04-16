package cn.dazhiyy.trans.server.transport.netty.yml;

import cn.dazhiyy.trans.server.transport.netty.constant.NettyOptionConst;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author dazhi
 * @projectName easy-trans
 * @packageName cn.dazhiyy.trans.server.transport.netty.yml
 * @className nettyYml
 * @description TODO
 * @date 2019/3/30 18:12
 */
@Data
@Component
@ConfigurationProperties(prefix = "trans.transport.netty")
public class NettyProperties {

    private Integer port = NettyOptionConst.PORT;

    private Integer backlog = NettyOptionConst.SO_BACKLOG;

}
