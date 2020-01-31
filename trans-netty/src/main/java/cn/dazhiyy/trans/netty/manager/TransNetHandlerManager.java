package cn.dazhiyy.trans.netty.manager;

import cn.dazhiyy.trans.netty.context.TransNettyContext;
import cn.dazhiyy.trans.netty.dto.SerializaDataDTO;
import cn.dazhiyy.trans.netty.handler.AbstractTransNetHandler;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Set;

/**
 * @author dazhi
 * @projectName easy-trans
 * @packageName cn.dazhiyy.trans.netty.manager
 * @className TransNetHandlerManager
 * @description 用于管理相关数据的处理器
 * @date 2020/1/31 21:51
 */
public class TransNetHandlerManager {

    /**
     * 处理远程客户端的数据
     *
     * @param serializaDataDTO 封装好的数据DTO
     */
    public void handlerData(SerializaDataDTO serializaDataDTO) {
        Set<AbstractTransNetHandler> handlers = TransNettyContext.getHandlers();

        if (CollectionUtils.isNotEmpty(handlers)) {
            for (AbstractTransNetHandler handler : handlers) {
                if (!handler.doHandler(serializaDataDTO)) {
                    // 放回false 表示拦截,不继续执行下面的处理器了
                    break;
                }
            }
        }
    }
}
