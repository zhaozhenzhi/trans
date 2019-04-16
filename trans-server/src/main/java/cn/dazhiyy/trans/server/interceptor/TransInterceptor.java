package cn.dazhiyy.trans.server.interceptor;

/**
 * @author dazhi
 * @projectName easy-trans
 * @packageName cn.dazhiyy.trans.server.Interceptor
 * @className TransInterceptor
 * @description 拦截器
 * @date 2019/4/3 10:34
 */
public interface TransInterceptor<E,R> {

    /**
     * 调用方法前调用
     *
     * @param t 传参
     *
     * @return
     * @throws Exception
     */
    boolean preHandle(E t)throws Exception;

    /**
     * 方法调用后 回调
     *
     * @throws Exception
     */
    void postHandle(R r) throws Exception;

    /**
     *
     *
     * @throws Exception
     */
    void afterCompletion(Exception ex) throws Exception;
}
