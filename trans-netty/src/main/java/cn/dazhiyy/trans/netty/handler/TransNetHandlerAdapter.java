package cn.dazhiyy.trans.netty.handler;

import cn.dazhiyy.trans.netty.dto.SerializaDataDTO;


/**
 * @author dazhi
 * @projectName easy-trans
 * @packageName cn.dazhiyy.trans.netty.handler
 * @className TransNetHandlerAdapter
 * @description 适配器
 * @date 2020/1/31 21:46
 */
public class TransNetHandlerAdapter extends AbstractTransNetHandler {

    @Override
    public Boolean doHandler(SerializaDataDTO transportSerializaDTO) {
        return Boolean.FALSE;
    }
}
