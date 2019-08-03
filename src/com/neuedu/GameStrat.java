package com.neuedu;

import com.neuedu.main.GameFrame;
import com.neuedu.util.DataStore;

public class GameStrat {

    public static void main(String[] args) {
        // 设置窗口的方法init
        GameFrame gameFrame = new GameFrame();
        DataStore.put("gameFrame", gameFrame);
        gameFrame.init();

    }

}
