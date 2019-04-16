package cn.dazhiyy.trans.common.transport;

import cn.dazhiyy.trans.common.enums.OpType;
import lombok.Data;

import java.util.Map;

/**
 * @author dazhi
 * @projectName easy-trans
 * @packageName cn.dazhiyy.trans.common.transport
 * @className DbListenerWrapper
 * @description TODO
 * @date 2019/4/7 00:52
 */
@Data
public class DbListenerWrapper {
    private String connectName;
    private String dbName;
    private String tableName;

    private OpType opType;

    private Map<String,String> after;
    private Map<String,String> before;

}
