package cn.dazhiyy.trans.common.enums;

import com.github.shyiko.mysql.binlog.event.EventType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author dazhi
 * @projectName cn.dazhiyy.advert
 * @packageName cn.dazhiyy.ad.handler
 * @className OpType
 * @description TODO
 * @date 2019/3/22 14:06
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum OpType {
    ADD("insert"),UPDATE("update"),DELETE("delete"),OTHER("other");

    private String msg;


    public static OpType to(EventType eventType) {

        switch (eventType) {
            case EXT_WRITE_ROWS:
                return ADD;
            case EXT_UPDATE_ROWS:
                return UPDATE;
            case EXT_DELETE_ROWS:
                return DELETE;

            default:
                return OTHER;
        }
    }

    public static OpType to(String msg){
        OpType[] values = OpType.values();
        for (OpType value : values) {
            if (value.msg.equals(msg)){
                return value;
            }
        }
        return null;
    }
}
