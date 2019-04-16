package cn.dazhiyy.trans.client.trans;


import cn.dazhiyy.trans.client.trans.template.Column;
import cn.dazhiyy.trans.client.trans.template.Database;
import cn.dazhiyy.trans.client.trans.template.DbBean;
import cn.dazhiyy.trans.client.trans.template.Operate;
import cn.dazhiyy.trans.client.trans.template.Table;
import cn.dazhiyy.trans.client.trans.template.TransListenerConfig;
import cn.dazhiyy.trans.client.trans.transport.netty.NettySocketClient;
import cn.dazhiyy.trans.common.bean.TransDb;
import cn.dazhiyy.trans.common.bean.TransDbBean;
import cn.dazhiyy.trans.common.bean.TransDbConn;
import cn.dazhiyy.trans.common.bean.TransTable;
import cn.dazhiyy.trans.common.enums.OpType;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * @author dazhi
 * @projectName easy-trans
 * @packageName cn.dazhiyy.trans.client.trans
 * @className trans
 * @description TODO
 * @date 2019/4/3 13:39
 */
@Slf4j
@Data
public class Trans {

    private TransListenerConfig config;

    private TransDbConn transDbConn = new TransDbConn();

    private NettySocketClient client;

    public Trans(TransListenerConfig config){
        this.config = config;
    }

    public Trans(String path){
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream resourceAsStream = loader.getResourceAsStream(path);
        try {
             this.config = JSON.parseObject(resourceAsStream, Charset.defaultCharset(), TransListenerConfig.class);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public TransDbConn buildTransDbBean(TransListenerConfig config){
        TransDbConn transDbConn = new TransDbConn();
        Map<String, TransDbBean> connMap = transDbConn.getConnMap();
        List<DbBean> listenerConfig = config.getListenerConfig();
        for (DbBean dbBean : listenerConfig) {
            TransDbBean transDbBean = buildTransDb(dbBean);
            connMap.put(dbBean.getDbConnectionName(),transDbBean);
        }
        return transDbConn;
    }

    /**
     * 构建数据库索引
     * @param dbBean
     * @return
     */
    public TransDbBean buildTransDb(DbBean dbBean){
        TransDbBean transDbBean = new TransDbBean();
        Map<String, TransDb> transDbMap = transDbBean.getTransDbMap();
        List<Database> databaseList = dbBean.getDatabaseList();

        for (Database database : databaseList) {
            TransDb transDb = buildTransTable(database);
            transDbMap.put(database.getDatabaseName(),transDb);
        }
        transDbBean.setConnectionName(dbBean.getDbConnectionName());
        return transDbBean;
    }

    /**
     * 构建表索引
     * @param database
     * @return
     */
    public TransDb buildTransTable(Database database){
        TransDb transDb = new TransDb();
        Map<String, TransTable> transTableMap = transDb.getTransTableMap();
        List<Table> tableList = database.getTableList();
        for (Table table : tableList) {
            TransTable transTable = buildTransTable(table);
            transTableMap.put(table.getTableName(),transTable);
        }
        transDb.setDbName(database.getDatabaseName());
        return transDb;
    }

    /**
     * 构建属性索引
     *
     * @param table
     * @return
     */
    public TransTable buildTransTable(Table table){
        TransTable transFiled = new TransTable();
        Map<OpType, List<String>> attrMap = transFiled.getAttrMap();
        // 封装table数据
        List<Operate> op = table.getOp();
        for (Operate operate : op) {
            OpType opType = OpType.to(operate.getOpType());
            if (opType != null) {
                List<Column> opFiled = operate.getOpFiled();
                List<String> fields= Lists.newArrayList();
                for (Column column : opFiled) {
                    fields.add(column.getColumn());
                }
                attrMap.put(opType,fields);
            }
        }
        transFiled.setTableName(table.getTableName());
        return transFiled;
    }


    public void start(String ip,Integer port) throws InterruptedException {
        client = new NettySocketClient();
        client.setTransDbConn(buildTransDbBean(config));
        client.connection(ip,port);
    }


    public void close(){
        client.close();
    }

    public static void main(String[] args) {
        Trans trans = new Trans("template.json");
        TransDbConn transDbConn = trans.getTransDbConn();
        System.out.println(transDbConn);
        try {
            trans.start("127.0.0.1",8091);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
