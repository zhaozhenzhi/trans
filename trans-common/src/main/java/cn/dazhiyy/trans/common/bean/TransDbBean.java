package cn.dazhiyy.trans.common.bean;

import com.google.common.collect.Maps;
import lombok.Data;

import java.util.Map;

/**
 * @author dazhi
 * @projectName easy-trans
 * @packageName cn.dazhiyy.trans.server.bean
 * @className TransDbBean
 * @description TODO
 * @date 2019/4/3 20:53
 */
@Data
public class TransDbBean {

    private String connectionName;
    /**
     *  String -> 库名 数据库
     */
    Map<String,TransDb> transDbMap = Maps.newConcurrentMap();
}
