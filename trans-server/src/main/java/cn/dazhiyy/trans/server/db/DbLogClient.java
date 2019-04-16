package cn.dazhiyy.trans.server.db;

import java.io.IOException;

/**
 * @author dazhi
 * @projectName easy-search
 * @packageName cn.dazhiyy.db.middleware.client
 * @className DbClient
 * @description 数据库客户端
 * @date 2019/3/27 10:36
 */
public interface DbLogClient {

    /**
     * 连接配置的所有的binlog
     */
    void connect() throws IOException;


    /**
     * 关闭所有的binlog连接
     */
    void close();

}
