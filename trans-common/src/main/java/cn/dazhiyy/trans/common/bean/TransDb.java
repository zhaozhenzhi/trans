package cn.dazhiyy.trans.common.bean;

import com.google.common.collect.Maps;
import lombok.Data;

import java.util.Map;

/**
 * @author dazhi
 * @projectName easy-trans
 * @packageName cn.dazhiyy.trans.server.bean
 * @className TransDb
 * @description TODO
 * @date 2019/4/3 20:55
 */
@Data
public class TransDb {

    /**
     * 数据库名
     */
    private String dbName;

    /**
     * 该数据库中所有表 string -> 表 TransTable数据
     */
    Map<String,TransTable> transTableMap= Maps.newConcurrentMap();
}
