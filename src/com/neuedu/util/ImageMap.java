package com.neuedu.util;

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

public class ImageMap {


    //静态，整个项目执行时只执行一次
    private static final Map<String, Image> map = new HashMap<>();

    static {
        map.put("bg01",ImageUtil.getImage("com\\neuedu\\imgs\\bg\\bg.jpg"));
    }

    public static Image get(String key) {
        return map.get(key);
    }

}
