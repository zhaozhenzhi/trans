package cn.dazhiyy.trans.server.db.mysql;

import cn.dazhiyy.trans.server.db.mysql.context.AbstractMysqlListenerContext;
import cn.dazhiyy.trans.server.handler.DataHandler;
import com.github.shyiko.mysql.binlog.event.Event;

/**
 * @author dazhi
 * @projectName easy-trans
 * @packageName cn.dazhiyy.trans.server.db.mysql
 * @className AbstractMysql
 * @description TODO
 * @date 2019/4/2 16:38
 */
public abstract class AbstractDataHandler implements DataHandler<AbstractMysqlListenerContext, Event> {

}
