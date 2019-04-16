package cn.dazhiyy.trans.common.transport;

import lombok.Data;

/**
 * @author dazhi
 * @projectName easy-trans
 * @packageName cn.dazhiyy.trans.common.transport
 * @className TransportData
 * @description TODO
 * @date 2019/4/3 14:51
 */
@Data
public class TransportData {

    /**
     *  传输的事件
     */
    private String eventCode;

    /**
     * 传输的数据
     */
    private Object data;
}
