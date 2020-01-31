package cn.dazhiyy.trans.netty.dto;

import lombok.Data;

/**
 * @author dazhi
 * @projectName easy-trans
 * @packageName cn.dazhiyy.trans.netty.dto
 * @className TransportDTO
 * @description 传输类
 * @date 2020/1/31 20:40
 */
@Data
public class TransportSerializaDTO {

    /** 协议 */
    private String protocol;

    /** IP地址*/
    private String ip;

    /** 时间戳 */
    private Long timestamp;

    /** 状态码 */
    private Integer code;

    /** 状态码解释 */
    private String msg;

    /** 数据建议是JSON格式 */
    private Object data;

}
