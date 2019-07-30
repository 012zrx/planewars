package com.neuedu.runtime;

import com.neuedu.base.BaseSprite;
import com.neuedu.base.Drawable;
import com.neuedu.base.Moveable;
import com.neuedu.util.ImageMap;

import java.awt.Graphics;
import java.awt.Image;

public class Background extends BaseSprite implements Moveable, Drawable {

    //添加背景图片
    private Image image;

    //给自己保留一个无参的构造函数,在构造函数时传入图片
    public Background() {
        this(0, 0, ImageMap.get("bg01"));
    }

    public Background(int x, int y, Image image) {
        super(x, y);
        this.image = image;
    }

    @Override
    public void move() {
        setY(getY() - 1);
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image,getX(),getY(),image.getWidth(null),image.getHeight(null),null);
        move();
    }
}
