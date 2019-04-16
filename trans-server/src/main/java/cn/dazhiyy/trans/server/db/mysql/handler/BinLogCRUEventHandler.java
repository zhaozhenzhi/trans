package cn.dazhiyy.trans.server.db.mysql.handler;

import cn.dazhiyy.trans.common.enums.OpType;
import cn.dazhiyy.trans.server.db.mysql.AbstractDataForQueueHandler;
import cn.dazhiyy.trans.server.db.mysql.context.AbstractMysqlListenerContext;
import cn.dazhiyy.trans.server.db.mysql.context.MysqlListenerContext;
import cn.dazhiyy.trans.server.db.mysql.listener.QueueDataWrapper;
import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.Event;
import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.EventType;
import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author dazhi
 * @projectName cn.dazhiyy.advert
 * @packageName cn.dazhiyy.ad.listener.handler
 * @className BinLogUpdateEventHandler
 * @description TODO
 * @date 2019/3/24 10:44
 */
@Slf4j
@Component
public class BinLogCRUEventHandler extends AbstractDataForQueueHandler {

    @Override
    public void onHandler(Event event, MysqlListenerContext context, String connectName) {
        //获得表名
        AbstractMysqlListenerContext.TableEventData tableEeventData = getTableEeventData(event, context, connectName);
        if (tableEeventData == null) {
            log.error("数据event:{},无法查询表名",event);
            return;
        }
        //TODO 2019/4/6 过滤 以后再实现
        // 发布事件
        QueueDataWrapper data = new QueueDataWrapper();
        data.setConnectName(connectName);
        data.setDbName(tableEeventData.getDatabase());
        data.setTableName(tableEeventData.getTable());
        getQueueDataWrapper(event.getData(),data);
        context.onEvent(data);
    }


    private MysqlListenerContext.TableEventData getTableEeventData(Event event, MysqlListenerContext context,String connectName) {
        EventData eventData =  event.getData();
        Map<Long, AbstractMysqlListenerContext.TableEventData> dataMap = context.getTableMap().get(connectName);
        if (dataMap == null ) {
            return null;
        }

        if (event.getData() instanceof WriteRowsEventData) {
            WriteRowsEventData data = event.getData();
            return dataMap.get(data.getTableId());

        }

        if (eventData instanceof UpdateRowsEventData) {
            UpdateRowsEventData data = event.getData();
            return dataMap.get(data.getTableId());
        }

        if (eventData instanceof DeleteRowsEventData) {
            DeleteRowsEventData data = event.getData();
            return dataMap.get(data.getTableId());
        }

        //获得表名
        return null;
    }

    private QueueDataWrapper getQueueDataWrapper(EventData eventData,QueueDataWrapper wrapper) {

        if (eventData instanceof WriteRowsEventData) {
            List<Serializable[]> rows = ((WriteRowsEventData) eventData).getRows();
            wrapper.setAfter(rows);
            wrapper.setOpType(OpType.ADD);
        }

        if (eventData instanceof UpdateRowsEventData) {
            List<Serializable[]> after = ((UpdateRowsEventData) eventData).getRows().stream()
                    .map(Map.Entry::getValue)
                    .collect(Collectors.toList());
            List<Serializable[]> before = ((UpdateRowsEventData) eventData).getRows().stream()
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
            wrapper.setAfter(after);
            wrapper.setBefore(before);
            wrapper.setOpType(OpType.UPDATE);
        }

        if (eventData instanceof DeleteRowsEventData) {
            List<Serializable[]> rows = ((DeleteRowsEventData) eventData).getRows();
            wrapper.setAfter(rows);
            wrapper.setOpType(OpType.DELETE);
        }

        return wrapper;
    }

    @Override
    public Boolean isSupport(Event event) {
        if (EventType.EXT_UPDATE_ROWS.equals(event.getHeader().getEventType())
            ||EventType.EXT_WRITE_ROWS.equals(event.getHeader().getEventType())
            ||EventType.EXT_DELETE_ROWS.equals(event.getHeader().getEventType())) {
            log.info("开始处理{}类型",event.getHeader().getEventType());
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
