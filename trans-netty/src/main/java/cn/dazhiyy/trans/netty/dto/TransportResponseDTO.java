package cn.dazhiyy.trans.netty.dto;

import lombok.Data;

/**
 * @author dazhi
 * @projectName easy-trans
 * @packageName cn.dazhiyy.trans.netty.dto
 * @className TransportResponseDTO
 * @description 响应的对象
 * @date 2020/2/1 15:51
 */
@Data
public class TransportResponseDTO extends BaseDTO {

    /** 结果码 */
    private Integer resultCode;

    /** 结果码解释 */
    private String resultMsg;

    /** 数据建议是JSON格式 */
    private Object data;

}
