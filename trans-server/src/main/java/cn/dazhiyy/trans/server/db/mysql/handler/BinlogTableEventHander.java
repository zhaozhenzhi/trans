package cn.dazhiyy.trans.server.db.mysql.handler;

import cn.dazhiyy.trans.server.db.mysql.AbstractDataHandler;
import cn.dazhiyy.trans.server.db.mysql.context.AbstractMysqlListenerContext;
import cn.dazhiyy.trans.server.util.ContainerFactory;
import com.github.shyiko.mysql.binlog.event.Event;
import com.github.shyiko.mysql.binlog.event.EventType;
import com.github.shyiko.mysql.binlog.event.TableMapEventData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author dazhi
 * @projectName cn.dazhiyy.advert
 * @packageName cn.dazhiyy.ad.listener
 * @className BinlogEventHanderImpl
 * @description TODO
 * @date 2019/3/24 09:37
 */
@Slf4j
@Component
public class BinlogTableEventHander extends AbstractDataHandler {

    @Override
    public void onHandler(Event event, AbstractMysqlListenerContext context, String connectName) {
        TableMapEventData data = event.getData();
        //获得context内的容器
        Map<String, Long> tableIdMap = ContainerFactory.setorCreate(connectName, context.getTableIdMap(), ConcurrentHashMap::new);
        Map<Long, AbstractMysqlListenerContext.TableEventData> tableMap = ContainerFactory.setorCreate(connectName,context.getTableMap(), ConcurrentHashMap::new);
        Map<String, AbstractMysqlListenerContext.TableEventData> tableInfoMap = ContainerFactory.setorCreate(connectName,context.getTableInfoMap(), ConcurrentHashMap::new);

        Long tableId = tableIdMap.get(data.getTable());
        AbstractMysqlListenerContext.TableEventData tableEventData = tableMap.get(data.getTableId());
        if (tableEventData == null && tableId == null) {
            //没有存储关系
            AbstractMysqlListenerContext.TableEventData tableEvent = context.getInstantTableEventData(data.getTableId(),data.getDatabase(),data.getTable());
            tableMap.put(data.getTableId(),tableEvent);
            tableIdMap.put(data.getTable(),data.getTableId());
            tableInfoMap.put(data.getTable(),tableEvent);
        } else {
            // 说明关系已经变化
            AbstractMysqlListenerContext.TableEventData table = tableInfoMap.get(data.getTable());
            Long oldId = table.getTableId();
            table.setTableId(data.getTableId());
            tableMap.remove(oldId);
            tableMap.put(data.getTableId(),table);
            tableIdMap.put(data.getTable(),data.getTableId());
        }
        log.info("结束处理{}类型",event.getHeader().getEventType());
    }

    @Override
    public Boolean isSupport(Event event) {
        if (EventType.TABLE_MAP.equals(event.getHeader().getEventType())) {
            log.info("开始处理{}类型",event.getHeader().getEventType());
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

}
