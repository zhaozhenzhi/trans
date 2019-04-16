package cn.dazhiyy.trans.server.db.mysql.listener.index;

import cn.dazhiyy.trans.common.enums.OpType;
import com.google.common.collect.Maps;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author dazhi
 * @projectName easy-trans
 * @packageName cn.dazhiyy.trans.server.db.mysql.listener.index
 * @className ListenerTable
 * @description TODO
 * @date 2019/4/5 00:33
 */
@Data
public class ListenerTable {

    private String tableName;

    private Map<OpType, List<String>> attrMap = Maps.newConcurrentMap();




}
