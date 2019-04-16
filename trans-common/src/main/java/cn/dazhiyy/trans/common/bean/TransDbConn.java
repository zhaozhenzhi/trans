package cn.dazhiyy.trans.common.bean;

import com.google.common.collect.Maps;
import lombok.Data;

import java.util.Map;

/**
 * @author dazhi
 * @projectName easy-trans
 * @packageName cn.dazhiyy.trans.common.bean
 * @className TransDbConn
 * @description TODO
 * @date 2019/4/4 22:30
 */
@Data
public class TransDbConn {

    /**
     * 连接名 -> 数据库集合
     */
    Map<String,TransDbBean> connMap = Maps.newConcurrentMap();
}
