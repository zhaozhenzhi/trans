package cn.dazhiyy.trans.server.handler;

import cn.dazhiyy.trans.server.db.mysql.context.ListenerContext;

/**
 * @author dazhi
 * @projectName cn.dazhiyy.advert
 * @packageName cn.dazhiyy.ad.handler
 * @className BinlogEventHandler
 * @description TODO
 * @date 2019/3/24 09:37
 */
public interface DataHandler<E extends ListenerContext,Y> {

    /**
     * 处理事件的
     *
     * @param event
     * @param context
     * @param connectName
     */
    void onHandler(Y event, E context,String connectName);

    /**
     * 是否支持
     *
     * @param event
     * @return
     */
    Boolean isSupport(Y event);
}
