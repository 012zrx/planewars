package com.neuedu.base;

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

}

