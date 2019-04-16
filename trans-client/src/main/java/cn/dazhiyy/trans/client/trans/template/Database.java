package cn.dazhiyy.trans.client.trans.template;

import lombok.Data;

import java.util.List;

/**
 * @author dazhi
 * @projectName easy-search
 * @packageName cn.dazhiyy.db.middleware.propertise.bean
 * @className Database
 * @description TODO
 * @date 2019/3/27 14:30
 */
@Data
public class Database {

    private String databaseName;

    private List<Table> tableList;
}
