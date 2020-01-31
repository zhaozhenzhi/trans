package cn.dazhiyy.trans.netty.config;

import java.io.InputStream;
import java.util.Properties;

/**
 * @author dazhi
 * @projectName queue
 * @packageName cn.dazhi.queue.config
 * @className QueueConfig
 * @description 配置类
 * @date 2020/1/22 15:49
 */
public class NettyConfig {

    /** 配置文件 */
    private Properties properties;
    /** 默认配置文件 */
    private TransNettyProperties defaultProperties;


    /**
     * 获得配置文件的中配置
     *
     * @param key 配置文件中的key
     * @return value 配置文件中的值
     */
    public String get(String key){
        // 查询配置文件
        if (properties==null) {
            properties = new Properties();
            InputStream inputStream = this.getClass().getResourceAsStream("/queue.properties");
            if (inputStream!=null) {
                try {
                    properties.load(inputStream);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        Object o = properties.get(key);
        if (o == null) {
            // 不存在配置,查询是否存在默认值
            if (defaultProperties == null) {
                defaultProperties = new TransNettyProperties();
            }
            return defaultProperties.get(key);
        } else {
            return o.toString();
        }
    }


}
