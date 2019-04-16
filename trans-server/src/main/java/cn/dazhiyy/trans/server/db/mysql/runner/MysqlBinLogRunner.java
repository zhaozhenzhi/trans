package cn.dazhiyy.trans.server.db.mysql.runner;

import cn.dazhiyy.trans.server.db.mysql.context.ListenerContext;
import cn.dazhiyy.trans.server.db.mysql.MysqlBinlogClient;
import cn.dazhiyy.trans.server.db.mysql.yml.DbInfo;
import cn.dazhiyy.trans.server.db.mysql.yml.MysqlBinlogProperties;
import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author dazhi
 * @projectName easy-search
 * @packageName cn.dazhiyy.db.middleware.runner
 * @className MysqlBinLogRunner
 * @description Mysql binlog监听服务启动 一个客户端对应一个数据库
 * @date 2019/3/27 09:57
 */
@Slf4j
public class MysqlBinLogRunner implements CommandLineRunner {

    @Getter
    private Map<String,MysqlBinlogClient> clients = Maps.newConcurrentMap();
    private ExecutorService executor;
    @Autowired
    private MysqlBinlogProperties properties;

    @Autowired
    private ListenerContext context;

    @Override
    public void run(String... args) throws Exception {
       log.info("mysql binlog client start........" );
       if (MapUtils.isNotEmpty(clients)) {
           for (MysqlBinlogClient client : clients.values()) {
               executor.execute(client::connect);
           }
       } else {
           log.warn("no mysql clients,Unable to start service.....");
       }
    }

    @PostConstruct
    public void initClients(){
        Map<String, DbInfo> dataSource = properties.getDataSource();

        for (Map.Entry<String, DbInfo> entry : dataSource.entrySet()) {
            MysqlBinlogClient client = new MysqlBinlogClient(entry.getKey());
            client.setDbInfo(entry.getValue());
            client.setContext(context);
            clients.put(entry.getKey(),client);
        }

        //初始化线程池
        this.executor = Executors.newFixedThreadPool(dataSource.size());

    }


}
