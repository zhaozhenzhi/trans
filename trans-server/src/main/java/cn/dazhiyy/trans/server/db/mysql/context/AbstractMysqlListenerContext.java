package cn.dazhiyy.trans.server.db.mysql.context;

import cn.dazhiyy.trans.common.transport.DbDataWrapper;
import cn.dazhiyy.trans.server.db.mysql.AbstractDataForQueueHandler;
import cn.dazhiyy.trans.server.db.mysql.AbstractDataHandler;
import cn.dazhiyy.trans.server.interceptor.TransInterceptor;
import cn.dazhiyy.trans.server.queue.TransDataQueue;
import com.github.shyiko.mysql.binlog.event.Event;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * @author dazhi
 * @projectName easy-trans
 * @packageName cn.dazhiyy.trans.server.db.mysql.context
 * @className MysqlListenerContext
 * @description TODO
 * @date 2019/4/2 16:32
 */
@Data
public abstract class AbstractMysqlListenerContext implements ListenerContext<Event,DbDataWrapper> {

    @Autowired
    private TransDataQueue queue;
    @Autowired
    private List<AbstractDataHandler> handlers;
    @Autowired
    private List<AbstractDataForQueueHandler> handlerQueues;
    @Autowired(required = false)
    private List<TransInterceptor> interceptors;

    private Map<String, Map<Long,TableEventData>> tableMap = Maps.newConcurrentMap();
    private Map<String, Map<String,Long>> tableIdMap = Maps.newConcurrentMap();
    private Map<String, Map<String,TableEventData>> tableInfoMap = Maps.newConcurrentMap();

    @Override
    public void onSender(String connectName, Event event) {
        for (AbstractDataHandler handler : handlers) {
            if (handler.isSupport(event)){
                handler.onHandler(event,this,connectName);
                return;
            }
        }
        // 把数据交由handler处理
        DbDataWrapper data = new DbDataWrapper();
        data.setConnectName(connectName);
        data.setDate(event);
        queue.add(data);
    }

    public TableEventData getInstantTableEventData(){
        return new TableEventData();
    }

    public TableEventData getInstantTableEventData(Long tableId, String database, String table){
        return new TableEventData(tableId,database,table);
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class TableEventData{
        private Long tableId;
        private String database;
        private String table;
    }

}
