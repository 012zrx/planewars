package com.neuedu.runtime;

import com.neuedu.base.BaseSprite;
import com.neuedu.base.Drawable;
import com.neuedu.base.Moveable;
import com.neuedu.constant.FrameConstant;
import com.neuedu.main.GameFrame;
import com.neuedu.util.DataStore;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

/**
 * 魔法值道具类
 */
public class Magic extends BaseSprite implements Moveable, Drawable {


    private Image image;

    private int speed = FrameConstant.GAME_SPEED * 2;

    public Magic() {
    }

    public Magic(int x, int y, Image image, int speed) {
        super(x, y);
        this.image = image;
        this.speed = speed;
    }

    @Override
    public void draw(Graphics g) {
        move();
        borderTesting();
        g.drawImage(
                image,
                getX(),
                getY(),
                image.getWidth(null),
                image.getHeight(null),
                null);
    }

    //边缘检测,血包飞出边缘后自动消除
    public void borderTesting() {
        if (getY() > FrameConstant.FRAME_HEIGHT) {
            GameFrame gameFrame = DataStore.get("gameFrame");
            gameFrame.magicList.remove(this);
        }
    }

    @Override
    public void move() {
//        setX(getX() + speed);
        setY(getY() + speed);
        borderTesting();
    }


    public void collisionTesting(Plane plane) {
        GameFrame gameFrame = DataStore.get("gameFrame");
        if (plane.getRectangle().intersects(this.getRectangle())) {
            gameFrame.magicList.remove(this);
            if (gameFrame.magic_hp < 100 && gameFrame.hp > 0) {
                gameFrame.magic_hp += 3;
            }
        }
    }

    @Override
    public Rectangle getRectangle() {
        return new Rectangle(
                getX(),
                getY(),
                image.getWidth(null),
                image.getHeight(null));
    }
}
