
import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.applet.Applet;
import java.awt.*;
import java.net.MalformedURLException;
import java.util.Enumeration;

public class SingleMainFrame<textFont> extends JFrame {
    public static SingleGameController controller;
    public static Skin skin = Skin.Day;

    public static Skin getSkin() {
        return skin;
    }

    public static void setSkin(Skin skin) {
        MainFrame.skin = skin;
    }


    public int xCount;
    public int yCount;
    public int mineCount;
    public static int counter = 0;

    public static void setController(SingleGameController controller) {
        SingleMainFrame.controller = controller;
    }

    public void setxCount(int xCount) {
        this.xCount = xCount;
    }

    public void setyCount(int yCount) {
        this.yCount = yCount;
    }

    public void setMineCount(int mineCount) {
        this.mineCount = mineCount;
    }

    public static SingleGameController getController() {
        return controller;
    }

    public int getxCount() {
        return xCount;
    }

    public int getyCount() {
        return yCount;
    }

    public int getMineCount() {
        return mineCount;
    }

    /////用于存档的构造方法
    /////用于存档的构造方法    /////用于存档的构造方法    /////用于存档的构造方法    /////用于存档的构造方法    /////用于存档的构造方法

    public SingleMainFrame(int xCount, int yCount, int mineCount) {
        //todo: change the count of xCount, yCount and mineCount by passing parameters from constructor


        this.xCount = xCount;       //格子的行数
        this.yCount = yCount;       //格子的列数
        this.mineCount = mineCount;   //雷数

        this.setTitle("扫雷");
        this.setLayout(null);
        this.setSize(yCount * SingleGridComponent.gridSize + 18, xCount * SingleGridComponent.gridSize + 80);
        ///根据格子的SIZE来决定MainFrame的SIZE
        this.setLocationRelativeTo(null);

        controller = new SingleGameController();
        SingleGamePanel gamePanel = new SingleGamePanel(xCount, yCount, mineCount);
        controller.setGamePanel(gamePanel);

        this.add(gamePanel);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        MusicAudioClip mac = new MusicAudioClip();
        try {
            mac.setAudioClip(Applet.newAudioClip((new java.io.File("MainFrameBGM.wav")).toURL()));//填写你自己的文件路径
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        mac.loop();//循环播放


        /////将文本输入框改为WIN10风格

        /////////////////////////////////(原来的游戏再玩一遍)
        JButton reset = new JButton("本局归零");
        reset.setSize(gamePanel.getWidth() / 4, 35);
        reset.setLocation(0, gamePanel.getHeight());
        this.add(reset);
        reset.addActionListener(e -> {
            for (int i = 0; i < xCount; i++) {
                for (int j = 0; j < yCount; j++) {
                    controller.getGamePanel().getMineField()[i][j].setStatus(GridStatus.Covered);
                    repaint();
                }
            }
        });


        ///////////////////////////////(用一个新棋盘重新玩)
        JButton restart = new JButton("重开一局");
        restart.setSize(gamePanel.getWidth() / 4, 35);
        restart.setLocation(controller.getGamePanel().getWidth() / 4, gamePanel.getHeight());
        this.add(restart);
        restart.addActionListener(e -> {
            for (int i = 0; i < xCount; i++) {
                for (int j = 0; j < yCount; j++) {
                    controller.getGamePanel().getMineField()[i][j].setStatus(GridStatus.Covered);
                }
            }
            controller.getGamePanel().reStart(xCount, yCount, mineCount);
            repaint();
        });
        //////////////////////////////分数在点击按钮时不能实现立即重置
//
//        JButton read = new JButton("快速存档");
//        reset.setSize(controller.getGamePanel().getWidth() / 6, 35);
//        reset.setLocation(controller.getGamePanel().getWidth() / 6 * 2, controller.getGamePanel().getHeight());
//        this.add(read);
//
//
//        ////////////////////////////////////////

//        JButton write = new JButton("快速读档");
//        reset.setSize(controller.getGamePanel().getWidth() / 6, 35);
//        reset.setLocation(controller.getGamePanel().getWidth() / 6 * 3, controller.getGamePanel().getHeight());
//        this.add(write);


        //////////////////////////(返回键方法)
        JButton Back = new JButton("返回");
        Back.setSize(gamePanel.getWidth() / 4, 35);
        Back.setLocation(gamePanel.getWidth() / 4 * 2, gamePanel.getHeight());
        this.add(Back);
        Back.addActionListener(e -> {
            ModeChooseFrame modeChooseFrame = new ModeChooseFrame();
            this.setVisible(false);
            mac.stop();
            /////在自定义时，格子的大小尺寸可能会出现BUG
        });
        ////////////////////////

        ///////////////////////////////////////

        //////////////////////(设置作弊模式)
        JButton cheat = new JButton("一键全开");
        cheat.setSize(gamePanel.getWidth() / 4, 35);
        cheat.setLocation(gamePanel.getWidth() / 4 * 3, gamePanel.getHeight());
        this.add(cheat);
        cheat.addActionListener(e -> {
            if (counter == 0) {
                for (int i = 0; i < this.getxCount(); i++) {
                    for (int j = 0; j < this.getyCount(); j++) {
                        if (gamePanel.getChessboard()[i][j] == -1
                                && !gamePanel.getMineField()[i][j].isFlaged()) {
                            gamePanel.getMineField()[i][j].setStatus(GridStatus.Clicked);
                            counter++;
                        }
                    }
                }
            } else {
                for (int i = 0; i < this.getxCount(); i++) {
                    for (int j = 0; j < this.getyCount(); j++) {
                        if (gamePanel.getMineField()[i][j].getStatus() == GridStatus.Clicked
                                && gamePanel.getChessboard()[i][j] == -1
                                && !gamePanel.getMineField()[i][j].isBombed()
                                && !gamePanel.getMineField()[i][j].isFlaged()) {
                            gamePanel.getMineField()[i][j].setStatus(GridStatus.Covered);
                        }
                    }
                }
                counter = 0;
            }
            gamePanel.repaint();
        });
        //////////////////////////////////

//
//        JButton save = new JButton("快速存档");
//        save.setSize(gamePanel.getWidth() / 7, 35);
//        save.setLocation(gamePanel.getWidth() / 7, gamePanel.getHeight());
//        add(save);
//        save.addActionListener(e -> {
//            try {
//                controller.writeDataToFile();
//            } catch (IOException ioException) {
//                ioException.printStackTrace();
//            }
//
//        });
//
//
//        JButton read = new JButton("快速读档");
//        read.setSize(gamePanel.getWidth() / 7, 35);
//        read.setLocation(0, gamePanel.getHeight());
//        add(read);
//        read.addActionListener(e -> {
//            try {
//                controller.readFileData();
//                MainFrame mainFrame = new MainFrame(controller.getGamePanel().getChessboard().length,
//                        controller.getGamePanel().getChessboard()[0].length,
//                        controller.getGamePanel().getMineNum(),
//                        controller);
//                this.dispose();
//            } catch (IOException ioException) {
//                ioException.printStackTrace();
//            }
//
//        });


        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


    }

    static void InitGlobalFont(Font font) {         /////将字体改为宋体的方法
        FontUIResource fontRes = new FontUIResource(font);
        for (Enumeration<Object> keys = UIManager.getDefaults().keys(); keys.hasMoreElements(); ) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                UIManager.put(key, fontRes);
            }
        }
    }
}
