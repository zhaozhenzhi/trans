package cn.dazhiyy.trans.server.db.mysql.context;

import cn.dazhiyy.trans.common.bean.TransDb;
import cn.dazhiyy.trans.common.bean.TransDbBean;
import cn.dazhiyy.trans.common.bean.TransDbConn;
import cn.dazhiyy.trans.common.bean.TransTable;
import cn.dazhiyy.trans.common.enums.OpType;
import cn.dazhiyy.trans.common.transport.DbDataWrapper;
import cn.dazhiyy.trans.common.transport.DbListenerWrapper;
import cn.dazhiyy.trans.common.transport.EventEnum;
import cn.dazhiyy.trans.common.transport.TransportData;
import cn.dazhiyy.trans.server.db.mysql.AbstractDataForQueueHandler;
import cn.dazhiyy.trans.server.db.mysql.MysqlBinlogClient;
import cn.dazhiyy.trans.server.db.mysql.listener.EventWrapper;
import cn.dazhiyy.trans.server.db.mysql.listener.QueueDataWrapper;
import cn.dazhiyy.trans.server.db.mysql.listener.index.ListenerConn;
import cn.dazhiyy.trans.server.db.mysql.listener.index.ListenerDb;
import cn.dazhiyy.trans.server.db.mysql.listener.index.ListenerTable;
import cn.dazhiyy.trans.server.db.mysql.runner.MysqlBinLogRunner;
import com.alibaba.fastjson.JSON;
import com.github.shyiko.mysql.binlog.event.Event;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author dazhi
 * @projectName easy-trans
 * @packageName cn.dazhiyy.trans.server.db.mysql.context
 * @className MysqlIListnerContext
 * @description TODO
 * @date 2019/4/3 20:32
 */
@Slf4j
@Component
public class MysqlListenerContext extends AbstractMysqlListenerContext {

    @Autowired
    private MysqlBinLogRunner runner;
    private Map<String,Map<String,Integer>> filedNameIdMap= Maps.newConcurrentMap();
    private Map<String, ListenerConn> connMap = Maps.newConcurrentMap();


    @Override
    public void registered(EventWrapper eventWrapper) {
        registeredConn(eventWrapper);
    }

    @Override
    public void remove(EventWrapper eventWrapper) {
        Channel channel = eventWrapper.getChannel();


    }

    /**
     * 注册连接
     *
     * @param eventWrapper
     */
    private void registeredConn(EventWrapper eventWrapper) {
        TransDbConn transDbConn = eventWrapper.getTransDbConn();
        Map<String, TransDbBean> connAimMap = transDbConn.getConnMap();
        Channel channel = eventWrapper.getChannel();
        for (Map.Entry<String, TransDbBean> entry : connAimMap.entrySet()) {
            ListenerConn conn = connMap.get(entry.getKey());
            if (conn == null) {
                // 不存在监听缓存，直接保存
                ListenerConn listenerConn = buildListenerConn(entry.getValue(), channel);
                if (listenerConn!=null){
                    connMap.put(entry.getKey(),listenerConn);
                }
            } else {
                registeredDb(channel, entry, conn);
            }
        }
    }

    /**
     * 注册数据库
     *
     * @param channel
     * @param entry
     * @param conn
     */
    private void registeredDb(Channel channel, Map.Entry<String, TransDbBean> entry, ListenerConn conn) {
        Map<String, ListenerDb> dbMap = conn.getMap();
        TransDbBean transDbBean = entry.getValue();
        Map<String, TransDb> transDbMap = transDbBean.getTransDbMap();
        for (Map.Entry<String, TransDb> dbEntry : transDbMap.entrySet()) {
            ListenerDb listenerDb = dbMap.get(dbEntry.getKey());
            if (listenerDb == null) {
                ListenerDb db = buildDb(dbEntry.getValue(), channel);
                dbMap.put(dbEntry.getKey(),db);
            } else {
                registeredTable(dbEntry, listenerDb,channel);
            }
        }
    }

    /**
     * 注册table
     *
     * @param dbEntry
     * @param listenerDb
     */
    private void registeredTable(Map.Entry<String, TransDb> dbEntry, ListenerDb listenerDb,Channel channel) {
        Map<String, List<Channel>> tableMap = listenerDb.getTableMap();
        TransDb transDb = dbEntry.getValue();
        Map<String, TransTable> transTableMap = transDb.getTransTableMap();
        for (Map.Entry<String, TransTable> tableEntry : transTableMap.entrySet()) {
            List<Channel> channelList = tableMap.get(tableEntry.getKey());
            Map<Channel, Map<String,ListenerTable>> channelMap = listenerDb.getChannelMap();
            if (CollectionUtils.isEmpty(channelList)) {
                List<Channel> channels = Lists.newArrayList();
                channels.add(channel);
                tableMap.put(tableEntry.getKey(),channels);
                ListenerTable table = buildTable(tableEntry.getValue());
                Map<String,ListenerTable> tables = Maps.newConcurrentMap();
                tables.put(tableEntry.getKey(),table);
                channelMap.put(channel,tables);
            } else {
                if (!channelList.contains(channel)) {
                    channelList.add(channel);
                }
                // channel 已经存在 更新一遍 Map<Channel, List<ListenerTable>>
                ListenerTable table = buildTable(tableEntry.getValue());
                Map<String, ListenerTable> tables = channelMap.get(channel);
                if (tables == null) {
                    tables = Maps.newConcurrentMap();
                }
                tables.put(tableEntry.getKey(),table);
                channelMap.put(channel,tables);
            }
        }
    }

    /**
     * 构建conn
     *
     * @param transDbBean
     * @param channel
     * @return
     */
    private ListenerConn buildListenerConn(TransDbBean transDbBean, Channel channel){
        if (transDbBean!=null) {
            ListenerConn conn = new ListenerConn();
            Map<String, ListenerDb> dbMap = conn.getMap();
            Map<String, TransDb> transDbMap = transDbBean.getTransDbMap();
            for (Map.Entry<String, TransDb> entry : transDbMap.entrySet()) {
                ListenerDb listenerDb = buildDb(entry.getValue(), channel);
                dbMap.put(entry.getKey(),listenerDb);
            }
            conn.setConnName(transDbBean.getConnectionName());
            return conn;
        }
        return null;
    }

    /**
     * 构建监听数据
     *
     * @param transDb
     * @param channel
     * @return
     */
    private ListenerDb buildDb(TransDb transDb, Channel channel){
        if (transDb != null) {
            ListenerDb listenerDb = new ListenerDb();
            listenerDb.setDbName(transDb.getDbName());
            Map<String, TransTable> transTableMap = transDb.getTransTableMap();
            Map<Channel, Map<String,ListenerTable>> channelMap = listenerDb.getChannelMap();
            Map<String,ListenerTable> tables = Maps.newConcurrentMap();
            Map<String, List<Channel>> tableMap = listenerDb.getTableMap();
            for (Map.Entry<String, TransTable> entry : transTableMap.entrySet()) {
                TransTable transTable = entry.getValue();
                ListenerTable table = buildTable(transTable);
                tables.put(entry.getKey(),table);
                //绑定channel
                List<Channel> channelList = Lists.newArrayList();
                channelList.add(channel);
                tableMap.put(entry.getKey(),channelList);
            }
            channelMap.put(channel,tables);
            return listenerDb;
        }
        return null;
    }

    /**
     * 构建表结构
     * @param transTable 表
     * @return
     */
    private ListenerTable buildTable(TransTable transTable){
        if (transTable!=null) {
            ListenerTable table = new ListenerTable();
            table.setTableName(transTable.getTableName());
            table.setAttrMap(transTable.getAttrMap());
            return table;
        }
        return null;
    }




    @Override
    public void onEvent(QueueDataWrapper data) {
        String key = data.getConnectName()+"-"+ data.getDbName()+"-"+data.getTableName();
        Map<String, Integer> integerStringMap = filedNameIdMap.get(key);
        if (integerStringMap == null) {
            integerStringMap = selectDbTableInfo(data);
            if (integerStringMap == null) {
                log.error("连接未建立！");
            }
        }
        filedNameIdMap.put(key,integerStringMap);

        //发送信息
        onSender(data, integerStringMap);
    }

    private void onSender(QueueDataWrapper data, Map<String, Integer> integerStringMap) {
        ListenerConn listenerConn = connMap.get(data.getConnectName());
        if (listenerConn!=null) {
            ListenerDb listenerDb = listenerConn.getMap().get(data.getDbName());
            if (listenerDb!=null) {
                Map<String, List<Channel>> tableMap = listenerDb.getTableMap();
                List<Channel> channels = tableMap.get(data.getTableName());
                if (CollectionUtils.isNotEmpty(channels)){
                    for (Channel channel : channels) {
                        Map<String, ListenerTable> listenerTableMap = listenerDb.getChannelMap().get(channel);
                        ListenerTable table = listenerTableMap.get(data.getTableName());
                        //拿出对应操作的字段
                        Map<OpType, List<String>> attrMap = table.getAttrMap();
                        List<String> fileds = attrMap.get(data.getOpType());
                        if (CollectionUtils.isEmpty(fileds)) {
                            log.warn("该字段不支持该操作");
                            continue;
                        }

                        //封装数据
                        Map<String,String> after = Maps.newHashMap();
                        Map<String,String> before = Maps.newHashMap();

                        for (String filed : fileds) {
                            Integer integer = integerStringMap.get(filed);
                            OpType opType = data.getOpType();
                            List<Serializable[]> after1 = data.getAfter();
                            // 操作后的数
                            for (Serializable[] serializables : after1) {
                                if (integer >= 0 && (integer-1) <= serializables.length ) {
                                    Serializable v = serializables[integer-1];
                                    after.put(filed, v.toString());
                                }
                            }

                            // 操作前的数
                            if (OpType.UPDATE.equals(opType)) {
                                List<Serializable[]> before1 = data.getBefore();
                                for (Serializable[] serializables : before1) {
                                    if (integer >= 0 && (integer-1) <= serializables.length ) {
                                        Serializable v = serializables[integer-1];
                                        before.put(filed, v.toString());
                                    }
                                }
                            }
                        }
                        DbListenerWrapper wrapper = new DbListenerWrapper();
                        wrapper.setConnectName(data.getConnectName());
                        wrapper.setDbName(data.getDbName());
                        wrapper.setTableName(data.getTableName());
                        wrapper.setOpType(data.getOpType());
                        wrapper.setAfter(after);
                        wrapper.setBefore(before);

                        TransportData transportData = new TransportData();
                        transportData.setEventCode(EventEnum.ACCEPT.getCode());
                        transportData.setData(wrapper);
                        channel.writeAndFlush(JSON.toJSON(transportData)+"\r\n");
                    }
                }
            }
        }
    }

    private Map<String, Integer> selectDbTableInfo(QueueDataWrapper data) {
        Map<String, Integer> integerStringMap = Maps.newConcurrentMap();
        // 查询字段对应的结构
        MysqlBinlogClient mysqlBinlogClient = runner.getClients().get(data.getConnectName());
        if (mysqlBinlogClient != null) {
            Connection conn = mysqlBinlogClient.getConn();
            final String sql = "SELECT TABLE_SCHEMA ,TABLE_NAME,COLUMN_NAME,ORDINAL_POSITION FROM `COLUMNS` WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ?";
            PreparedStatement pstmt=null;
            ResultSet rs=null;
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1,data.getDbName());
                pstmt.setString(2,data.getTableName());
                rs = pstmt.executeQuery();
                integerStringMap = Maps.newConcurrentMap();
                while (rs.next()){
                    int pos = rs.getInt("ORDINAL_POSITION");
                    String colName = rs.getString("COLUMN_NAME");
                    integerStringMap.put(colName,pos);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (pstmt!= null) {
                        pstmt.close();
                    }
                    if (rs !=null) {
                        rs.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return integerStringMap;
    }

    @Override
    public void handler(DbDataWrapper wrapper) {
        Object date = wrapper.getDate();
        if (date instanceof Event) {
            handler(wrapper.getConnectName(), (Event) date);
        }
    }

    private void handler(String connectName, Event event) {
        for (AbstractDataForQueueHandler handler: getHandlerQueues()) {
            if (handler.isSupport(event)){
                handler.onHandler(event,this,connectName);
                return;
            }
        }
    }
}
