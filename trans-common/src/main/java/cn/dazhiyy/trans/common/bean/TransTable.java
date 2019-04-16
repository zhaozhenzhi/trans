package cn.dazhiyy.trans.common.bean;

import cn.dazhiyy.trans.common.enums.OpType;
import com.google.common.collect.Maps;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author dazhi
 * @projectName easy-trans
 * @packageName cn.dazhiyy.trans.server.bean
 * @className TransTable
 * @description TODO
 * @date 2019/4/3 20:58
 */
@Data
public class TransTable {

    private String tableName;

    private Map<OpType, List<String>> attrMap = Maps.newConcurrentMap();
}
