package cn.dazhiyy.trans.server.db.mysql.listener.index;

import com.google.common.collect.Maps;
import io.netty.channel.ChannelId;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author dazhi
 * @projectName easy-trans
 * @packageName cn.dazhiyy.trans.server.db.mysql.listener.index
 * @className ListenerDb
 * @description TODO
 * @date 2019/4/5 00:24
 */
@Data
public class ListenerDb {
    private String dbName;

    /**
     *
     */
    private Map<ChannelId,Map<String,ListenerTable>> channelMap = Maps.newConcurrentMap();

    /**
     *  string-> 表名
     */
    private Map<String, List<ChannelId>> tableMap = Maps.newConcurrentMap();
}
