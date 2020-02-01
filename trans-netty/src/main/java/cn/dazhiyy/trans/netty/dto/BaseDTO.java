package cn.dazhiyy.trans.netty.dto;

import lombok.Data;

/**
 * @author dazhi
 * @projectName easy-trans
 * @packageName cn.dazhiyy.trans.netty.dto
 * @className BaseDTO
 * @description 基础数据类型
 * @date 2020/2/1 15:52
 */
@Data
public class BaseDTO {

    /** 时间戳 */
    private Long timestamp;

    /** 协议 */
    private String protocol;

    /** IP地址*/
    private String ip;

    /** 加密 */
    private String sign;

}
