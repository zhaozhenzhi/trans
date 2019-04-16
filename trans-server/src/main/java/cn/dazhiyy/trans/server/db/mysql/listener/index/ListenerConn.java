package cn.dazhiyy.trans.server.db.mysql.listener.index;

import com.google.common.collect.Maps;
import lombok.Data;

import java.util.Map;

/**
 * @author dazhi
 * @projectName easy-trans
 * @packageName cn.dazhiyy.trans.server.db.mysql.listener.index
 * @className ListenerConn
 * @description TODO
 * @date 2019/4/5 00:24
 */
@Data
public class ListenerConn {

    private String connName;

    Map<String,ListenerDb> map = Maps.newConcurrentMap();
}
