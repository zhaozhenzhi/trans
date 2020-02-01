package cn.dazhiyy.trans;

/**
 * @author dazhi
 * @projectName easy-trans
 * @packageName cn.dazhiyy.trans.client.transport
 * @className SockerClient
 * @description 客户端
 * @date 2019/3/30 23:51
 */
public interface SockerClient {

    /**
     * 连接远程服务器
     *
     * @param ip 远程服务器的IP地址
     * @param port 远程服务器的端口号
     */
    void connection(String ip, Integer port) throws InterruptedException ;

    /**
     * 关闭连接
     */
    void close();
}
