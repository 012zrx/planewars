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
 * 敌军普通飞机子弹类
 */
public class EnemyBullet extends BaseSprite implements Moveable, Drawable {

    private Image image;

    private int speed = FrameConstant.GAME_SPEED * 3;

    public EnemyBullet() {
    }

    public EnemyBullet(int x, int y, Image image) {
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

    //边缘检测,子弹发出边缘后自动消除
    public void borderTesting() {
        if (getY() > FrameConstant.FRAME_HEIGHT) {
            GameFrame gameFrame = DataStore.get("gameFrame");
            gameFrame.enemyBulletList.remove(this);
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
    //碰撞检测，敌方子弹打中我方飞机后消失，我方飞机血量-6
    public void collisionTesting(Plane plane) {
        GameFrame gameFrame = DataStore.get("gameFrame");
        if (plane.getRectangle().intersects(this.getRectangle())) {
            gameFrame.enemyBulletList.remove(this);
            gameFrame.magic_hp--;
            if (gameFrame.hp <= 100 && gameFrame.hp >= 0) {
                gameFrame.hp -= 2;
            }

        }
    }
}
