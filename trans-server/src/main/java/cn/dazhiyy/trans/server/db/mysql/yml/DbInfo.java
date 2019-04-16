package cn.dazhiyy.trans.server.db.mysql.yml;

import lombok.Data;

/**
 * @author dazhi
 * @projectName easy-trans
 * @packageName cn.dazhiyy.trans.server.db.mysql.yml
 * @className DbInfo
 * @description TODO
 * @date 2019/4/2 09:48
 */
@Data
public class DbInfo {

    private String host;

    private Integer port;

    private String username;

    private String password;

    private String binlogName;

    private Long position;
}
