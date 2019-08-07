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
import java.awt.Rectangle;

/**
 * 血包道具类
 */
public class Blood extends BaseSprite implements Moveable, Drawable {

    private Image image;
    //规定血包移动速度
    private int speed = FrameConstant.GAME_SPEED * 2;


    public Blood() {
        this(0,0, ImageMap.get("blood03"));
    }

    public Blood(int x, int y, Image image) {
        super(x, y);
        this.image = image;

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

    @Override
    public void move() {
        setY(getY() + speed);
        borderTesting();
    }


    //边缘检测,血包飞出边缘后自动消除
    public void borderTesting() {
        if (getY() > FrameConstant.FRAME_HEIGHT) {
            GameFrame gameFrame = DataStore.get("gameFrame");
            gameFrame.bloodList.remove(this);
        }
    }


    //碰撞检测，吃掉血包我方飞机血量+2
    public void collisionTesting(Plane plane) {
        GameFrame gameFrame = DataStore.get("gameFrame");
        if (plane.getRectangle().intersects(this.getRectangle())) {
            gameFrame.bloodList.remove(this);
            if (gameFrame.hp < 100 && gameFrame.hp > 0) {
                gameFrame.hp += 5;
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
