package cn.dazhiyy.trans.server.db.mysql.yml;

import com.google.common.collect.Maps;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author dazhi
 * @projectName easy-trans
 * @packageName cn.dazhiyy.trans.server.db.mysql.yml
 * @className MysqlBinlogProperties
 * @description TODO
 * @date 2019/4/2 09:44
 */
@Data
@Component
@ConfigurationProperties(prefix = "trans.db.mysql")
public class MysqlBinlogProperties {

    private Map<String,DbInfo> dataSource = Maps.newHashMap();

}
