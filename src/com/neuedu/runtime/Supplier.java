package com.neuedu.runtime;

import com.neuedu.base.BaseSprite;
import com.neuedu.base.Drawable;
import com.neuedu.base.Moveable;
import com.neuedu.constant.FrameConstant;
import com.neuedu.main.GameFrame;
import com.neuedu.util.DataStore;
import com.neuedu.util.ImageMap;

import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;

/**
 * 补给飞机类
 */
public class Supplier extends BaseSprite implements Moveable, Drawable {


    private Image image;

    //创建一个随机变量，通过判断使补给飞机随机发射血包
    private Random random = new Random();

    //补给飞机速度
    private int speed = FrameConstant.GAME_SPEED * 2;

    public Supplier() {
    }

    public Supplier(int x, int y, Image image) {
        super(x, y);
        this.image = image;
    }

    @Override
    public void draw(Graphics g) {
        move();
        fire();
        borderTesting();
        g.drawImage(
                image,
                getX(),
                getY(),
                image.getWidth(null),
                image.getHeight(null),
                null);
    }



    public void fire() {
        GameFrame gameFrame = DataStore.get("gameFrame");
        if (random.nextInt(1000) > 990) {
            gameFrame.bloodList.add(new Blood(
                    getX() + (image.getWidth(null) / 2) - (ImageMap.get("blood01").getWidth(null) / 2),
                    getY() + image.getHeight(null),
                    ImageMap.get("blood02")));
        }
    }

    private boolean right = true;

    @Override
    public void move() {
        if (right) {
            setX(getX() + speed);
        }else {
            setX(getX() - speed);
        }

    }

    //边缘检测
    public void borderTesting() {
        if (getX() + image.getWidth(null) >= FrameConstant.FRAME_WIDTH) {
            right = false;
        }else if (getX() < 0){
            right = true;
        }
    }

   //补给飞机不需要碰撞









}
