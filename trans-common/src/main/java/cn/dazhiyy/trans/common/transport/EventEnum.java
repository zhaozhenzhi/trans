package cn.dazhiyy.trans.common.transport;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author dazhi
 * @projectName easy-trans
 * @packageName cn.dazhiyy.trans.common.transport
 * @className Event
 * @description TODO
 * @date 2019/4/3 14:54
 */
@Getter
@AllArgsConstructor
public enum EventEnum {
    LISTEN("100000"),ACCEPT("1000002")
    ;
    private String code;


}
