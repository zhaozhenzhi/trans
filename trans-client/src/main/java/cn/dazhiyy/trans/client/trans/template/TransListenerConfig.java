package cn.dazhiyy.trans.client.trans.template;

import lombok.Data;

import java.util.List;

/**
 * @author dazhi
 * @projectName easy-trans
 * @packageName cn.dazhiyy.trans.client.trans.config
 * @className TransConfig
 * @description 监听配置
 * @date 2019/4/3 13:40
 */
@Data
public class TransListenerConfig {

    private List<DbBean> listenerConfig;


}
