package com.neuedu.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局变量静态访问
 */
public class DataStore {

    private static final Map<String, Object> map = new HashMap<>();
    //ImageMap类中用键值对存储图片
    public static void put(String key, Object value) {
        map.put(key, value);
    }


    //泛型方法
    public static <T> T get(String key) {
        return (T) map.get(key);
    }

}
