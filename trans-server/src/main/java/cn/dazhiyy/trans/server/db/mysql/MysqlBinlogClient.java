package cn.dazhiyy.trans.server.db.mysql;

import cn.dazhiyy.trans.server.db.mysql.context.ListenerContext;
import cn.dazhiyy.trans.server.db.mysql.yml.DbInfo;
import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.Event;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author dazhi
 * @projectName easy-trans
 * @packageName cn.dazhiyy.trans.server.db.mysql
 * @className MysqlBinlogClient
 * @description TODO
 * @date 2019/4/2 11:23
 */
@Slf4j
public class MysqlBinlogClient extends AbstractMysqlBinlogClient implements BinaryLogClient.EventListener {

    @Setter
    private DbInfo dbInfo;
    @Setter
    private ListenerContext context;
    @Getter
    private Connection conn;

    public MysqlBinlogClient(String connectName){
        super(connectName);
    }

    @Override
    public void initClient(){
        init(dbInfo);
    }

    @Override
    public void bindListener(BinaryLogClient client) {
        client.registerEventListener(this);
    }


    private void init(DbInfo dbInfo){
        this.dbInfo = dbInfo;
        BinaryLogClient client = new BinaryLogClient(
                dbInfo.getHost(),dbInfo.getPort(),dbInfo.getUsername(),dbInfo.getPassword());

        if (!StringUtils.isEmpty(dbInfo.getBinlogName()) &&
                !dbInfo.getPosition().equals(-1L)) {
            client.setBinlogFilename(dbInfo.getBinlogName());
            client.setBinlogPosition(dbInfo.getPosition());
        }
        setClient(client);
        initConn(dbInfo);
        log.info("Database init,listener:connectName:{},host:{},port:{}"
                ,getConnectName(),dbInfo.getHost(),dbInfo.getPort());
    }

    private void initConn(DbInfo dbInfo) {
        StringBuilder sb = new StringBuilder();
        sb.append("jdbc:mysql://").append(dbInfo.getHost()).append(":").append(dbInfo.getPort()).append("/information_schema");
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(sb.toString(), dbInfo.getUsername(), dbInfo.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEvent(Event event) {
        context.onSender(getConnectName(),event);
    }
}
