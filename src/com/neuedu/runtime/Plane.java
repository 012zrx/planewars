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
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * 我方飞机类
 */
public class Plane extends BaseSprite implements Moveable, Drawable {

    private Image image;

    //移动开关控制
    private boolean up, right, down, left;

    //开火开关控制
    private boolean fire;
    //飞机速度
    public int speed = FrameConstant.GAME_SPEED * 9;
    public Plane() {
        this(
                (FrameConstant.FRAME_WIDTH - ImageMap.get("my01").getWidth(null)) / 2,
                FrameConstant.FRAME_HEIGHT - ImageMap.get("my01").getHeight(null),
                ImageMap.get("my01"));
    }

    public Plane(int x, int y, Image image) {
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


        //使子弹刷新减慢的方法
        if (fire) {
            index++;
            if (index >= 10) {
                index = 0;
            }
        }
    }


    private int index = 0;

    /**
     * 开火方法
     * 判断开关是否打开
     * 创建一个子弹对象放入到gameFrame的子弹集合中
     */
    public void fire() {
        if (fire && index == 0) {
            GameFrame gameFrame = DataStore.get("gameFrame");
            gameFrame.bulletList.add(new Bullet(
                    getX() + (image.getWidth(null) / 2) - (ImageMap.get("myb01").getWidth(null) / 2),
                    getY() - ImageMap.get("myb01").getHeight(null),
                    ImageMap.get("myb01")

            ));
        }
    }




    //


    /**
     * 移动方法
     */
    @Override
    public void move() {
        if (up) {
            setY(getY() - speed);
        }
        if (right) {
            setX(getX() + speed);
        }
        if (down) {
            setY(getY() + speed);
        }
        if (left) {
            setX(getX() - speed);
        }
        borderTesting();
    }

    public void borderTesting() {
        if (getX() < 0) {
            setX(0);
        }
        if (getX() > FrameConstant.FRAME_WIDTH - image.getWidth(null)) {
            setX(FrameConstant.FRAME_WIDTH - image.getWidth(null));
        }
        //工具栏大概30
        if (getY() < 30) {
            setY(30);
        }
        if (getY() > FrameConstant.FRAME_HEIGHT - image.getHeight(null)) {
            setY(FrameConstant.FRAME_HEIGHT - image.getHeight(null));
        }
    }


    /*
        让飞机也能处理这个功能,在使用时GamFrame类直接调用即可
        处理按键
        开关控制移动保证每次刷新移动是固定的，使动作更流畅
    */
    public void keyPressed(KeyEvent e) {
        //键盘按下时打开
        //写多个if而不是多个else if是为了能同时执行
        if (e.getKeyCode() == KeyEvent.VK_W) {
            up = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            right = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            down = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            left = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            fire = true;
        }
    }

    public void keyReleased(KeyEvent e) {
        //键盘抬起后关闭
        if (e.getKeyCode() == KeyEvent.VK_W) {
            up = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            right = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            down = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            left = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            fire = false;
        }
    }

    public void mouseClicked(MouseEvent e) {
        GameFrame gameFrame = DataStore.get("gameFrame");
        if (gameFrame.hp<=0){
        gameFrame.bombList.clear();
        gameFrame.enemyPlaneList.clear();
        gameFrame.enemyBulletList.clear();
        gameFrame.bossList.clear();
        gameFrame.bossBulletList.clear();
        gameFrame.bulletList.clear();
        gameFrame.bloodList.clear();
        gameFrame.magicList.clear();
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
