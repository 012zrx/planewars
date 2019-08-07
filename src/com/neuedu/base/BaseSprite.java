/**
 * 精灵父类，共同属性X，Y坐标
 */
package com.neuedu.base;

import java.awt.Rectangle;

//抽象类必须以abstract或者base开头
public abstract class BaseSprite {

    private int x;
    private int y;

    //无参构造函数
    public BaseSprite() {
    }
    //全参构造函数
    public BaseSprite(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //获取和修改坐标的方法
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    //碰撞检测要用到的矩形来判断是否相交，相交即发生碰撞
    public Rectangle getRectangle() {
        return null;
    }
}

