package cn.dazhiyy.trans.common.util;

import cn.dazhiyy.trans.common.transport.EventEnum;
import cn.dazhiyy.trans.common.transport.TransportData;
import com.alibaba.fastjson.JSON;

/**
 * @author dazhi
 * @projectName easy-trans
 * @packageName cn.dazhiyy.trans.common.util
 * @className SerialUtil
 * @description TODO
 * @date 2019/4/3 14:57
 */
public class SerialUtil {

    public static String serial(Object data){
        TransportData transportData = new TransportData();
        transportData.setEventCode(EventEnum.LISTEN.getCode());
        transportData.setData(data);
        return JSON.toJSON(transportData)+"\r\n";
    }

//    public static <T> T antierial(String string){
//        JSONObject jsonObject = JSON.parseObject(string);
//
//        Object clazz = jsonObject.get("clazz");
//        Object data = jsonObject.get("data");
//        try {
//            Class<?> aClass = Class.forName(clazz.toString());
//
//
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//
//
//    }
}
