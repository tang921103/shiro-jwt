package com.sj.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回的restful格式
 */
public class MsgUtil {
    public static Map<String,Object> returnRightMsg(Object data){
        Map<String,Object> map = new HashMap<>();
        map.put("status","ok");
        map.put("code","200");
        map.put("data",data);
        return map;
    }
    public static Map<String,Object> returnErrorMsg(Object data){
        Map<String,Object> map = new HashMap<>();
        map.put("status","error");
        map.put("code","500");
        map.put("data",data);
        return map;
    }
}
