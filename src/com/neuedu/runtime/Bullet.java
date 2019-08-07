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
import java.util.List;

/**
 * 我方飞机子弹类
 */
public class Bullet extends BaseSprite implements Moveable, Drawable {


    private Image image;
    //子弹速度
    private int speed = FrameConstant.GAME_SPEED * 5;

    public Bullet() {
        this(0, 0, ImageMap.get("myb01"));
    }

    public Bullet(int x, int y, Image image) {
        super(x, y);
        this.image = image;
    }

    //画子弹
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
    //子弹移动方法
    @Override
    public void move() {
        setY(getY() - speed);
    }

    //边缘检测,子弹发出边缘后自动消除
    public void borderTesting() {
        if (getY() < 30 - image.getHeight(null)) {
            GameFrame gameFrame = DataStore.get("gameFrame");
            gameFrame.bulletList.remove(this);
        }
    }

    //重写Rectangle方法判断我方子弹与敌方飞机是否碰撞
    @Override
    public Rectangle getRectangle() {
        return new Rectangle(
                getX(),
                getY(),
                image.getWidth(null),
                image.getHeight(null)
        );
    }

    //碰撞检测,传入敌方飞机，用敌方飞机的矩形与我方子弹的矩形判断
    //我方子弹打中敌方飞机后消失
    public void collisionTesting(List<EnemyPlane> enemyPlaneList) {
        GameFrame gameFrame = DataStore.get("gameFrame");
        //先遍历敌方飞机
        for (EnemyPlane enemyPlane : enemyPlaneList) {
            if (enemyPlane.getRectangle().intersects(this.getRectangle())) {
                //如果敌方飞机与我方子弹发生碰撞，将敌方飞机移除
                //内部实现原因：发生的是址传递
                enemyPlaneList.remove(enemyPlane);
                gameFrame.bulletList.remove(this);
                FrameConstant.score += 5;
            }
        }
        if (getY()<30){
            gameFrame.bulletList.remove(this);
        }
    }
    //我方子弹击中boss机，boss机减血50
    public void collisionBossTesting(List<Boss> bossList) {
        GameFrame gameFrame = DataStore.get("gameFrame");
        for (Boss boss : bossList) {
            if (boss.getRectangle().intersects(this.getRectangle())) {
                gameFrame.bulletList.remove(this);
                gameFrame.blood -= 50;
                FrameConstant.score += 20;
                }
            if (gameFrame.blood <= 0) {
                bossList.remove(boss);
                gameFrame.bossList.remove(this);

            }

        }
    }

}
