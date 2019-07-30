package com.neuedu.main;

import com.neuedu.constant.FrameConstant;
import com.neuedu.runtime.Background;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class GameFrame extends Frame {

    private Background background = new Background();

    @Override
    public void paint(Graphics g) {
        background.draw(g);
    }


    /**
     * 使用这个跟方法初始化窗口
     */
    public void init() {
        //设置窗口尺寸
        setSize(FrameConstant.FRAME_WIDTH, FrameConstant.FRAME_HEIGHT);
        //设置窗口居中
        setLocationRelativeTo(null);
        //禁止启动窗口时有图片
        enableInputMethods(false);
        //设置窗口关闭
        addWindowListener(new WindowAdapter() {
            //重写的是点击关闭按钮关闭窗口的功能
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        //设置窗口可见
        setVisible(true);


        new Thread() {
            @Override
            public void run() {

                while (true) {
                    repaint();
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                }
        }.start();
    }

    private Image offScreenImage = null;//创建缓冲区

    @Override
    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(
                    FrameConstant.FRAME_WIDTH,
                    FrameConstant.FRAME_HEIGHT);
        }
        Graphics goff = offScreenImage.getGraphics();//创建离线图片的实例，在图片缓冲区绘图

        paint(goff);
        g.drawImage(offScreenImage,0,0,null);//将缓冲图片绘制到窗口目标
    }
}
