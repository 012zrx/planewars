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
import java.util.Random;

/**
 * boss敌机类
 */
public class Boss extends BaseSprite implements Moveable, Drawable {

    private Image image;

    //创建一个随机变量，通过判断使敌方飞机随机发射子弹
    private Random random = new Random();

    //敌方飞机速度
    private int speed = FrameConstant.GAME_SPEED * 5;

    public Boss() {
    }

    public Boss(int x, int y, Image image) {
        super(x, y);
        this.image = image;
    }

    @Override
    public void draw(Graphics g) {

        //当score分数大于50，出现boss机
        if (FrameConstant.score >= 50) {
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

}


    //开火方法
    public void fire() {
        GameFrame gameFrame = DataStore.get("gameFrame");
        if (random.nextInt(1000) > 985) {
            gameFrame.enemyBulletList.add(new EnemyBullet(
                    getX() + (image.getWidth(null) / 2) - (ImageMap.get("epb01").getWidth(null) / 2),
                    getY() + image.getHeight(null),
                    ImageMap.get("bossbullet01")));
        }
    }
    //用来控制横向移动的布尔变量
    private boolean right = false;

    @Override
    public void move() {
       if (right){
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

    //重写Rectangle方法判断敌方飞机与我方子弹是否发生碰撞
    @Override
    public Rectangle getRectangle() {
        return new Rectangle(
                getX(),
                getY(),
                image.getWidth(null),
                image.getHeight(null)
        );
    }
}
