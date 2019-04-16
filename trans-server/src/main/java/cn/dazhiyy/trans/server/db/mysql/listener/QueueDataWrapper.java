package cn.dazhiyy.trans.server.db.mysql.listener;

import cn.dazhiyy.trans.common.enums.OpType;
import com.google.common.collect.Lists;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author dazhi
 * @projectName easy-trans
 * @packageName cn.dazhiyy.trans.server.listener
 * @className QueueDataWrapper
 * @description TODO
 * @date 2019/4/3 20:46
 */
@Data
public class QueueDataWrapper {

    private String connectName;
    private String dbName;
    private String tableName;

    private OpType opType;

    private List<Serializable[]> after = Lists.newArrayList();
    private List<Serializable[]> before = Lists.newArrayList();

}
