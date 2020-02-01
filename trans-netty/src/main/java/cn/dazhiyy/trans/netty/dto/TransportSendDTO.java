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
public class TransportSendDTO extends BaseDTO {

    /** 行为码 */
    private Integer behaviorCode;

    /** 行为码解释 */
    private String behaviorMsg;

    /** 数据建议是JSON格式 */
    private Object data;

}
