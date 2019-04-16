package cn.dazhiyy.trans.client.trans.template;

import lombok.Data;

import java.util.List;

/**
 * @author dazhi
 * @projectName easy-search
 * @packageName cn.dazhiyy.db.middleware.propertise.bean
 * @className Table
 * @description TODO
 * @date 2019/3/27 16:09
 */
@Data
public class Table {

    private String tableName;

    private List<Operate> op;
}
