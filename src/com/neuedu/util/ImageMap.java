package com.neuedu.util;

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

/**
 * image存储类
 */
public class ImageMap {

    //静态，整个项目执行时只执行一次
    private static final Map<String, Image> map = new HashMap<>();
    //静态代码块
    static {

        //背景图
        map.put("bg01",ImageUtil.getImage("com\\neuedu\\imgs\\bg\\bg.jpg"));

        //我方的第一个飞机
        map.put("my01",ImageUtil.getImage("com\\neuedu\\imgs\\plane\\my_1.png"));

        //我方飞机的子弹
        map.put("myb01",ImageUtil.getImage("com\\neuedu\\imgs\\bullet\\myb_1.png"));

        //敌方飞机
        map.put("ep01",ImageUtil.getImage("com\\neuedu\\imgs\\plane\\ep_1.png"));
        map.put("ep02",ImageUtil.getImage("com\\neuedu\\imgs\\plane\\ep_2.png"));
        map.put("ep03",ImageUtil.getImage("com\\neuedu\\imgs\\plane\\ep_3.png"));

        //敌方飞机的子弹
        map.put("epb01",ImageUtil.getImage("com\\neuedu\\imgs\\bullet\\epb_1.png"));

        //画补给机
        map.put("sp01",ImageUtil.getImage("com\\neuedu\\imgs\\supplier\\sp_02.png"));

        //画boss机的集合
        map.put("boss01",ImageUtil.getImage("com\\neuedu\\imgs\\boss\\BOSS01_1.png"));
        map.put("boss02",ImageUtil.getImage("com\\neuedu\\imgs\\boss\\BOSS01_2.png"));
        map.put("boss03",ImageUtil.getImage("com\\neuedu\\imgs\\boss\\BOSS01_3.png"));
        map.put("boss04",ImageUtil.getImage("com\\neuedu\\imgs\\boss\\BOSS01_4.png"));
        map.put("boss05",ImageUtil.getImage("com\\neuedu\\imgs\\boss\\BOSS01_5.png"));
        map.put("boss06",ImageUtil.getImage("com\\neuedu\\imgs\\boss\\BOSS01_6.png"));
        map.put("boss07",ImageUtil.getImage("com\\neuedu\\imgs\\boss\\BOSS01_7.png"));
        map.put("boss08",ImageUtil.getImage("com\\neuedu\\imgs\\boss\\BOSS01_8.png"));

        //画炸弹
        map.put("bomb01",ImageUtil.getImage("com\\neuedu\\imgs\\bomb\\bomb.png"));

        //boss机子弹
        map.put("bossbullet01",ImageUtil.getImage("com\\neuedu\\imgs\\bullet\\bpb_05.png"));

        //血包
        map.put("blood01",ImageUtil.getImage("com\\neuedu\\imgs\\blood\\blood_2.png"));
        map.put("blood02",ImageUtil.getImage("com\\neuedu\\imgs\\blood\\blood_1.png"));
        map.put("blood03",ImageUtil.getImage("com\\neuedu\\imgs\\blood\\blood_3.png"));


    }

    public static Image get(String key) {
        return map.get(key);
    }

}
