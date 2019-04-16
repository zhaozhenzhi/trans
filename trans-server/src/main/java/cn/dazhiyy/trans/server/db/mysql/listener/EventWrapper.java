package cn.dazhiyy.trans.server.db.mysql.listener;

import cn.dazhiyy.trans.common.bean.TransDbConn;
import io.netty.channel.Channel;
import lombok.Data;

/**
 * @author dazhi
 * @projectName easy-trans
 * @packageName cn.dazhiyy.trans.server.listener
 * @className QueueEventWrapper
 * @description TODO
 * @date 2019/4/3 20:40
 */
@Data
public class EventWrapper {

    private TransDbConn transDbConn;

    private Channel channel;

}
