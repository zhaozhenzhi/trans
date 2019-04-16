package cn.dazhiyy.trans.common.transport;

import lombok.Data;

import java.io.Serializable;

/**
 * @author dazhi
 * @projectName concurrent-start
 * @packageName cn.dazhiyy.concurrent.netty.server.entity
 * @className TranslatorData
 * @description 用于传输数据的bean
 * @date 2019/3/30 17:10
 */
@Data
public class DbDataWrapper implements Serializable {

    private static final long serialVersionUID = -534799172158208303L;
    private String connectName;
    private Object date;
}
