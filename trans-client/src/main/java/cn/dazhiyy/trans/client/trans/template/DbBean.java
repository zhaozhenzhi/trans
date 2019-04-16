package cn.dazhiyy.trans.client.trans.template;

import lombok.Data;

import java.util.List;

/**
 * @author dazhi
 * @projectName easy-search
 * @packageName cn.dazhiyy.db.middleware.propertise.bean
 * @className DbBean
 * @description 存放数据库模板的信息
 * @date 2019/3/27 14:25
 */
@Data
public class DbBean {

    /**
     * 数据库连接名
     */
    private String dbConnectionName;

    /**
     *
     */
    private List<Database> databaseList;

}
