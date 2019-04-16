package cn.dazhiyy.trans.server.db.mysql.configuration;

import cn.dazhiyy.trans.server.db.mysql.runner.MysqlBinLogRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.annotation.Order;

/**
 * @author dazhi
 * @projectName easy-search
 * @packageName cn.dazhiyy.db.middleware.client.mysql.configuration
 * @className MysqlBinlogConfiguration
 * @description TODO
 * @date 2019/3/27 10:55
 */
@Configuration
@ConditionalOnProperty(name = "mysql.binlog.switch", havingValue = "true",matchIfMissing=true)
public class MysqlBinlogConfiguration {

    @Bean
    @Order(value = PriorityOrdered.HIGHEST_PRECEDENCE)
    public MysqlBinLogRunner getMysqlBinLogRunner(){
        return new MysqlBinLogRunner();
    }
}
