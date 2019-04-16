package cn.dazhiyy.trans.client.trans.template;

import lombok.Data;

import java.util.List;

/**
 * @author dazhi
 * @projectName easy-search
 * @packageName cn.dazhiyy.db.middleware.propertise.bean
 * @className Op
 * @description TODO
 * @date 2019/3/27 16:26
 */
@Data
public class Operate {
    private String opType;

    private List<Column> opFiled;


}
