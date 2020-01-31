package cn.dazhiyy.trans.netty.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author dazhi
 * @projectName easy-trans
 * @packageName cn.dazhiyy.trans.netty.enums
 * @className TransportStatusEnum
 * @description 网络传输状态码
 * @date 2020/1/31 22:54
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum TransportStatusEnum {

    DATA(1000,"数据请求"),
    REGISTERED(2000,"注册请求")
    ;
    private Integer code;
    private String msg;
}
