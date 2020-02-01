package cn.dazhiyy.trans.netty.handler;

import lombok.Data;

/**
 * @author dazhi
 * @projectName easy-trans
 * @packageName cn.dazhiyy.trans.netty.handler
 * @className TransNet
 * @description 网络请求适配器适配器
 * @date 2020/1/31 21:21
 */
@Data
public abstract class AbstractTransNetHandler implements TransNetHandler, Comparable<AbstractTransNetHandler> {

    /**
     * 排序，默认0
     */
    private Integer sort = 0;

    @Override
    public int compareTo(AbstractTransNetHandler num) {
        return sort - num.sort;
    }



}
