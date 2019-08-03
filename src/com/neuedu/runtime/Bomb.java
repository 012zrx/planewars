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
 * 炸弹道具类
 */
public class Bomb extends BaseSprite implements Moveable, Drawable {

    private Image image;
    //炸弹速度
    private int speed = FrameConstant.GAME_SPEED * 4;

    public Bomb() {
    }

    public Bomb(int x, int y, Image image) {
        super(x, y);
        this.image = image;
    }

    @Override
    public void draw(Graphics g) {
        if (FrameConstant.score >= 10) {
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
    }

    @Override
    public void move() {
        setY(getY() + speed);
        borderTesting();
    }

    //边缘检测,血包飞出边缘后自动消除
    public void borderTesting() {
        if (getY() > FrameConstant.FRAME_HEIGHT) {
            GameFrame gameFrame = DataStore.get("gameFrame");
            gameFrame.bombList.remove(this);
        }
    }


    //碰撞检测
    public void collisionTesting(Plane plane) {
        GameFrame gameFrame = DataStore.get("gameFrame");
        if (plane.getRectangle().intersects(this.getRectangle())) {
            gameFrame.bombList.remove(this);
            if (gameFrame.hp < 100 && gameFrame.hp > 0) {
                gameFrame.hp -= 5;
            }
        }
    }

    //矩形框，用来检测碰撞
    @Override
    public Rectangle getRectangle() {
        return new Rectangle(
                getX(),
                getY(),
                image.getWidth(null),
                image.getHeight(null));
    }


}
