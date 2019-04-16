package cn.dazhiyy.trans.server.db.mysql;

import cn.dazhiyy.trans.server.db.DbLogClient;
import com.github.shyiko.mysql.binlog.BinaryLogClient;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author dazhi
 * @projectName easy-search
 * @packageName cn.dazhiyy.db.middleware.client.mysql
 * @className MysqlBinlogClient
 * @description mysql binlog客户端
 * @date 2019/3/27 10:52
 */
@Data
@Slf4j
public abstract class AbstractMysqlBinlogClient implements DbLogClient {
    private String connectName;
    private BinaryLogClient client;

    AbstractMysqlBinlogClient(String connectName){
        this.connectName = connectName;
    }

    @Override
    public void connect() {
        try {
            // 初始化client
            initClient();
            // 绑定监听
            bindListener(this.client);
            // 发动连接连接
            this.client.connect();
            log.info("binlog start listener");
        } catch (IOException e) {
            e.printStackTrace();
            log.error("Database configuration error");
        }


    }

    @Override
    public void close() {
        try {
            log.info("close to mysql start");
            this.client.disconnect();
            log.info("close to mysql done");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化 client信息
     */
    public void initClient(){
        throw new NullPointerException();
    }

    /**
     * 绑定监听
     * @param client 客户端
     */
    public abstract void bindListener(BinaryLogClient client);
}
