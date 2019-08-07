package com.neuedu.main;

import com.neuedu.constant.FrameConstant;
import com.neuedu.runtime.Background;
import com.neuedu.runtime.Blood;
import com.neuedu.runtime.Bomb;
import com.neuedu.runtime.Boss;
import com.neuedu.runtime.BossBullet;
import com.neuedu.runtime.Bullet;
import com.neuedu.runtime.EnemyBullet;
import com.neuedu.runtime.EnemyPlane;
import com.neuedu.runtime.Magic;
import com.neuedu.runtime.Plane;
import com.neuedu.runtime.Supplier;
import com.neuedu.util.ImageMap;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 主窗口类
 * 1.绘制主窗口
 * 2.初始化元素
 * 3.碰撞检测调用
 */

public class GameFrame extends Frame {

    //创建背景对象
    private Background background = new Background();

    //创建飞机对象
    private Plane plane = new Plane();
    //创建随机变量
    Random r = new Random();

    //创建子弹集合，CopyOnWriteArrayList保证线程安全，换用别的会产生并发问题
    public final List<Bullet> bulletList = new CopyOnWriteArrayList<>();

    //创建boss子弹集合
    public final List<BossBullet> bossBulletList = new CopyOnWriteArrayList<>();

    //创建敌机飞机集合
    public final List<EnemyPlane> enemyPlaneList = new CopyOnWriteArrayList<>();

    //创建敌机boss集合
    public final List<Boss> bossList = new CopyOnWriteArrayList<>();

    //敌方子弹集合
    public final List<EnemyBullet> enemyBulletList = new CopyOnWriteArrayList<>();

    //创建炸弹集合
    public final List<Bomb> bombList = new CopyOnWriteArrayList<>();

    //创建补给机
    public final List<Supplier> supplierList = new CopyOnWriteArrayList<>();

    //创建血包集合
    public final List<Blood> bloodList = new CopyOnWriteArrayList<>();

    //创建魔法值集合
    public final List<Magic> magicList = new CopyOnWriteArrayList<>();

    //游戏结束的状态
    public boolean gameOver = false;

    //飞机血量
    public int hp = 100;

    //飞机魔法值
    public int magic_hp = 100;

    //boss机血量
    public int blood = 1000;


    public List<Bullet> getBulletList() {
        return bulletList;
    }


    @Override
    public void paint(Graphics g) {
        if (!gameOver) {
            //飞机要在背景之后，因为后能覆盖前
            background.draw(g);
            plane.draw(g);

            //随机生成元素
            if (r.nextInt(1000) > 985) {
                enemyPlaneList.add(new EnemyPlane(r.nextInt(800), 0, ImageMap.get("ep01")));
                enemyPlaneList.add(new EnemyPlane(r.nextInt(800), 0, ImageMap.get("ep02")));
                enemyPlaneList.add(new EnemyPlane(r.nextInt(800), 0, ImageMap.get("ep03")));
            }
            if(r.nextInt(1000) > 992) {
                bombList.add(new Bomb(r.nextInt(800), 0, ImageMap.get("bomb01")));
            }
            if (r.nextInt(1000) > 990) {
                bloodList.add(new Blood(r.nextInt(800), 0, ImageMap.get("blood01")));
                bloodList.add(new Blood(r.nextInt(800), 0, ImageMap.get("blood02")));
                magicList.add(new Magic(r.nextInt(800), 0, ImageMap.get("blood03"), FrameConstant.GAME_SPEED * 2));
            }

            //画子弹
            for (Bullet bullet : bulletList) {
                bullet.draw(g);
            }


            //画敌方飞机
            for (EnemyPlane enemyPlane : enemyPlaneList) {
                enemyPlane.draw(g);
            }
            //在每次绘制完飞机和子弹之后检测是否发生碰撞
            for (Bullet bullet : bulletList) {
                bullet.collisionTesting(enemyPlaneList);
            }
            //判断我方飞机与普通敌方飞机是否碰撞
            for (EnemyPlane enemyPlane : enemyPlaneList) {
                enemyPlane.collisionTesting(plane);
            }
            //画敌方子弹
            for (EnemyBullet enemyBullet : enemyBulletList) {
                enemyBullet.draw(g);
            }
            //判断敌方飞机是否打中我放飞机
            for (EnemyBullet enemyBullet : enemyBulletList) {
                enemyBullet.collisionTesting(plane);
            }


            //画补给飞机
            for (Supplier supplier : supplierList) {
                supplier.draw(g);
            }
            //判断我方飞机是否与血包碰撞
            for (Blood blood : bloodList) {
                blood.draw(g);
                blood.collisionTesting(plane);
            }
            //判断我方飞机是否吃掉糖果
            for (Magic magic : magicList) {
                magic.draw(g);
                magic.collisionTesting(plane);
            }
            //判断我方飞机是否碰撞炸弹
            for (Bomb bomb : bombList) {
                bomb.draw(g);
                bomb.collisionTesting(plane);
            }


            //画boss机
            for (Boss boss : bossList) {
                boss.draw(g);
            }
            //判断boss机是否打中我方飞机
            if (FrameConstant.score > 50) {
                for (BossBullet bossBullet : bossBulletList) {
                    bossBullet.collisionTesting(plane);
                }
            }
            //判断我方子弹是否 打中boss飞机
            if (FrameConstant.score > 50) {
                for (Bullet bullet : bulletList) {
                    bullet.collisionBossTesting(bossList);
                }
            }

            //检测血包飞出边缘后是否从内存中移除
            g.setColor(new Color(253, 14, 7));
            g.setFont(new Font("楷体", Font.BOLD, 20));
            g.drawString("血包数量：" + bloodList.size(), 30, 150);
            //检测敌机在界面内的数量
            g.setColor(new Color(255, 104, 5));
            g.setFont(new Font("楷体", Font.BOLD, 20));
            g.drawString("敌机数量：" + enemyPlaneList.size(), 30, 250);
            //记录得分情况
            g.setFont(new Font("楷体", Font.BOLD, 20));
            g.setColor(new Color(251, 255, 9));
            g.drawString("分数：" + FrameConstant.score, 30, 350);

            //我方飞机血量
            g.setFont(new Font("楷体", Font.BOLD, 20));
            g.setColor(new Color(26, 200, 0));
            g.drawString("战机血量：" + hp, 30, 450);

            //我方飞机体力
            g.setFont(new Font("楷体", Font.BOLD, 20));
            g.setColor(new Color(2, 188, 200));
            g.drawString("战机体力：" + magic_hp, 30, 550);

            //boss机血量
            g.setFont(new Font("楷体", Font.BOLD, 20));
            g.setColor(new Color(15, 27, 209));
            g.drawString("Boss血量：" + blood, 30, 650);

        }

        //判断飞机魔法值，小于10飞机速度减慢
        if (magic_hp <= 10) {
            plane.speed = FrameConstant.GAME_SPEED * 2;
        }


        //游戏结束
        if (FrameConstant.score >= 1000) {
            g.setFont(new Font("楷体", Font.BOLD, 64));
            g.setColor(new Color(189, 8, 255));
            g.drawString("恭喜通关！！！", 200, 514);
        }
        //当boss死亡时游戏胜利
        if (hp <= 0 || blood <= 0) {
            hp = 0;
            magic_hp = 0;
            g.setFont(new Font("楷体", Font.BOLD, 64));
            g.setColor(new Color(189, 8, 255));
            g.drawString("游戏结束！！！", 200, 514);
        }


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
        //设置窗口不可拉伸
        setResizable(false);


        //添加键盘监听，实现键盘按键控制飞机移动的功能
        addKeyListener(new KeyAdapter() {
            @Override
            //按下
            public void keyPressed(KeyEvent e) {
                plane.keyPressed(e);
            }

            @Override
            //抬起
            public void keyReleased(KeyEvent e) {
                plane.keyReleased(e);
            }
        });

        //鼠标监听，点击清屏
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                plane.mouseClicked(e);
            }
        });


        /**
         * 定义刷新次数
         */
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


        //游戏初始化时添加一些敌方飞机，限制在2000高度以内
/*            enemyPlaneList.add(new EnemyPlane(110, 0, ImageMap.get("ep01")));
            enemyPlaneList.add(new EnemyPlane(410, 130, ImageMap.get("ep01")));
            enemyPlaneList.add(new EnemyPlane(587, -200, ImageMap.get("ep01")));
            enemyPlaneList.add(new EnemyPlane(701, 980, ImageMap.get("ep01")));
            enemyPlaneList.add(new EnemyPlane(557, -359, ImageMap.get("ep01")));
            enemyPlaneList.add(new EnemyPlane(249, -332, ImageMap.get("ep01")));

            enemyPlaneList.add(new EnemyPlane(120, -2000, ImageMap.get("ep03")));
            enemyPlaneList.add(new EnemyPlane(624, -1888, ImageMap.get("ep03")));
            enemyPlaneList.add(new EnemyPlane(624, -1046, ImageMap.get("ep03")));*/


        //起始时添加补给机
        supplierList.add(new Supplier(0, 30, ImageMap.get("sp01")));

        //起始时添加一些血包
/*            bloodList.add(new Blood(56,100,ImageMap.get("blood02")));
            bloodList.add(new Blood(666,-673,ImageMap.get("blood02")));
            bloodList.add(new Blood(888,-20,ImageMap.get("blood02")));
            bloodList.add(new Blood(111,-79,ImageMap.get("blood02")));
            bloodList.add(new Blood(221,-10,ImageMap.get("blood02")));*/

        //添加boss机
        bossList.add(new Boss(750, 150, ImageMap.get("boss01")));


        //设置窗口可见
        setVisible(true);
    }


    /**
     * 双缓冲解决闪屏问题
     */
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
        g.drawImage(offScreenImage, 0, 0, null);//将缓冲图片绘制到窗口目标
    }


}
