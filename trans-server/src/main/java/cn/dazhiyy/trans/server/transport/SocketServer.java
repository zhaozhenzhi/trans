package cn.dazhiyy.trans.server.transport;

/**
 * @author dazhi
 * @projectName easy-trans
 * @packageName cn.dazhiyy.trans.server.transport
 * @className Server
 * @description 传输服务器
 * @date 2019/3/30 17:59
 */
public interface SocketServer {

    /**
     * 启动远程连接的服务
     * @param port 监听的端口
     */
    void start(Integer port) throws InterruptedException;

    /**
     * 关闭服务
     */
    void close();
}
