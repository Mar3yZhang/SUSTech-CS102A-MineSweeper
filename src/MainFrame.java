
import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.applet.Applet;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Enumeration;
import java.util.Random;

public class MainFrame<textFont> extends JFrame {
    public static GameController controller;

    public static Skin skin = Skin.Day;

    public static Skin getSkin() {
        return skin;
    }

    public static void setSkin(Skin skin) {
        MainFrame.skin = skin;
    }

    public static Charactor P1;
    public static Charactor P2;
    public static String P1Name;
    public static String P2Name;
    public int xCount;
    public int yCount;
    public int mineCount;
    public static int counter = 0;
    ImageIcon imageIcon1 = new ImageIcon("TheWorldCharactor.png");
    ImageIcon imageIcon2 = new ImageIcon("KillerQueenCharactor.png");
    ImageIcon imageIcon3 = new ImageIcon("KingCrimeCharactor.png");
    ImageIcon imageIcon4 = new ImageIcon("RandomCharactor.png");
    ImageIcon imageIcon5 = new ImageIcon("computer.png");
    private final Random random = new Random();
    Prop initprop1 = Prop.No;
    Prop initprop2 = Prop.No;

    public static void setController(GameController controller) {
        MainFrame.controller = controller;
    }

    public static GameController getController() {
        return controller;
    }

    public int getxCount() {
        return xCount;
    }

    public int getyCount() {
        return yCount;
    }


    public MainFrame(int xCount, int yCount, int mineCount) {
        //todo: change the count of xCount, yCount and mineCount by passing parameters from constructor



        this.xCount = xCount;       //格子的行数
        this.yCount = yCount;       //格子的列数
        this.mineCount = mineCount;   //雷数1

        this.setTitle("扫雷");
        this.setLayout(null);
        this.setSize(yCount * GridComponent.gridSize + (xCount * GridComponent.gridSize + 125) / 6 - 15, xCount * GridComponent.gridSize + 125);
        ///根据格子的SIZE来决定MainFrame的SIZE
        this.setLocationRelativeTo(null);

        MusicAudioClip mac = new MusicAudioClip();
        try {
            mac.setAudioClip(Applet.newAudioClip((new java.io.File("Killer1.wav")).toURL()));//填写你自己的文件路径
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        mac.loop();//循环播放

        Player p1 = new Player();
        Player p2 = new Player();
        controller = new GameController(p1, p2);
        GamePanel gamePanel = new GamePanel(xCount, yCount, mineCount);
        controller.setGamePanel(gamePanel);
        ScoreBoard scoreBoard = new ScoreBoard(p1, p2, xCount, yCount);
        controller.setScoreBoard(scoreBoard);
        if (P1 == Charactor.Rondom) {
            JLabel Play1 = new JLabel(imageIcon4);
            this.add(Play1);
            Play1.setVisible(true);
            Play1.setBounds(yCount * GridComponent.gridSize, -20,
                    (xCount * GridComponent.gridSize + 125) / 6 - 25, xCount * GridComponent.gridSize / 8 * 3);
        } else if (P1 == Charactor.KingCrisom) {
            JLabel Play1 = new JLabel(imageIcon3);
            this.add(Play1);
            Play1.setVisible(true);
            Play1.setBounds(yCount * GridComponent.gridSize, -20,
                    (xCount * GridComponent.gridSize + 125) / 6 - 25, xCount * GridComponent.gridSize / 8 * 3);
        } else if (P1 == Charactor.KillerQueen) {
            JLabel Play1 = new JLabel(imageIcon2);
            this.add(Play1);
            Play1.setVisible(true);
            Play1.setBounds(yCount * GridComponent.gridSize, -20,
                    (xCount * GridComponent.gridSize + 125) / 6 - 25, xCount * GridComponent.gridSize / 8 * 3);
        } else if (P1 == Charactor.TheWorld) {
            JLabel Play1 = new JLabel(imageIcon1);
            this.add(Play1);
            Play1.setVisible(true);
            Play1.setBounds(yCount * GridComponent.gridSize, -20,
                    (xCount * GridComponent.gridSize + 125) / 6 - 25, xCount * GridComponent.gridSize / 8 * 3);
        }


        if (P2 == Charactor.Rondom) {
            JLabel Play1 = new JLabel(imageIcon4);
            this.add(Play1);
            Play1.setVisible(true);
            Play1.setBounds(yCount * GridComponent.gridSize, xCount * GridComponent.gridSize / 8 * 3 + 90,
                    (xCount * GridComponent.gridSize + 125) / 6 - 25, xCount * GridComponent.gridSize / 8 * 3);
        } else if (P2 == Charactor.KingCrisom) {
            JLabel Play1 = new JLabel(imageIcon3);
            this.add(Play1);
            Play1.setVisible(true);
            Play1.setBounds(yCount * GridComponent.gridSize, xCount * GridComponent.gridSize / 8 * 3 + 90,
                    (xCount * GridComponent.gridSize + 125) / 6 - 25, xCount * GridComponent.gridSize / 8 * 3);
        } else if (P2 == Charactor.KillerQueen) {
            JLabel Play1 = new JLabel(imageIcon2);
            this.add(Play1);
            Play1.setVisible(true);
            Play1.setBounds(yCount * GridComponent.gridSize, xCount * GridComponent.gridSize / 8 * 3 + 90,
                    (xCount * GridComponent.gridSize + 125) / 6 - 25, xCount * GridComponent.gridSize / 8 * 3);
        } else if (P2 == Charactor.TheWorld) {
            JLabel Play1 = new JLabel(imageIcon1);
            this.add(Play1);
            Play1.setVisible(true);
            Play1.setBounds(yCount * GridComponent.gridSize, xCount * GridComponent.gridSize / 8 * 3 + 90,
                    (xCount * GridComponent.gridSize + 125) / 6 - 25, xCount * GridComponent.gridSize / 8 * 3);
        }

        gamePanel.getPropGrid1().setLocation(yCount * GridComponent.gridSize, xCount * GridComponent.gridSize / 8 * 3 - 35);
        gamePanel.getPropGrid2().setLocation(yCount * GridComponent.gridSize, xCount * GridComponent.gridSize - 150);

        p1.setUserName(P1Name);
        p2.setUserName(P2Name);
        p1.setCharactor(P1);
        p2.setCharactor(P2);
        scoreBoard.update();
        switch (p1.getCharactor()) {
            case Rondom: {
                int num = random.nextInt(4);
                switch (num) {
                    case 0: {
                        MainFrame.controller.getGamePanel().getPropGrid1().setGridStatus(Prop.No);
                        initprop1 = Prop.No;
                        break;
                    }
                    case 1: {
                        MainFrame.controller.getGamePanel().getPropGrid1().setGridStatus(Prop.TheWorld);
                        initprop1 = Prop.TheWorld;
                        break;
                    }
                    case 2: {
                        MainFrame.controller.getGamePanel().getPropGrid1().setGridStatus(Prop.KingCrisom);
                        initprop1 = Prop.KingCrisom;
                        break;
                    }
                    case 3: {
                        MainFrame.controller.getGamePanel().getPropGrid1().setGridStatus(Prop.KillerQueen);
                        initprop1 = Prop.KillerQueen;
                        break;
                    }
                }break;
            }
            case TheWorld: {
                MainFrame.controller.getGamePanel().getPropGrid1().setGridStatus(Prop.TheWorld);
                initprop1 = Prop.TheWorld;
                break;
            }
            case KingCrisom: {
                MainFrame.controller.getGamePanel().getPropGrid1().setGridStatus(Prop.KingCrisom);
                initprop1 = Prop.KingCrisom;
                break;
            }
            case KillerQueen: {
                MainFrame.controller.getGamePanel().getPropGrid1().setGridStatus(Prop.KillerQueen);
                initprop1 = Prop.KillerQueen;
                break;
            }
        }
        switch (p2.getCharactor()) {
            case Rondom: {
                int num = random.nextInt(4);
                switch (num) {
                    case 0: {
                        MainFrame.controller.getGamePanel().getPropGrid2().setGridStatus(Prop.No);
                        initprop2 = Prop.No;
                        break;
                    }
                    case 1: {
                        MainFrame.controller.getGamePanel().getPropGrid2().setGridStatus(Prop.TheWorld);
                        initprop2 = Prop.TheWorld;
                        break;
                    }
                    case 2: {
                        MainFrame.controller.getGamePanel().getPropGrid2().setGridStatus(Prop.KingCrisom);
                        initprop2 = Prop.KingCrisom;
                        break;
                    }
                    case 3: {
                        MainFrame.controller.getGamePanel().getPropGrid2().setGridStatus(Prop.KillerQueen);
                        initprop2 = Prop.KillerQueen;
                        break;
                    }
                }break;
            }
            case TheWorld: {
                MainFrame.controller.getGamePanel().getPropGrid2().setGridStatus(Prop.TheWorld);
                initprop2 = Prop.TheWorld;
                break;
            }
            case KingCrisom: {
                MainFrame.controller.getGamePanel().getPropGrid2().setGridStatus(Prop.KingCrisom);
                initprop2 = Prop.KingCrisom;
                break;
            }
            case KillerQueen: {
                MainFrame.controller.getGamePanel().getPropGrid2().setGridStatus(Prop.KillerQueen);
                initprop2 = Prop.KillerQueen;
                break;
            }
        }

        this.add(scoreBoard);
        this.add(gamePanel);



        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }


        /////////////////////////////////(原来的游戏再玩一遍)
        JButton reset = new JButton("本局归零");
        reset.setSize(scoreBoard.getWidth() / 6, 35);
        reset.setLocation(scoreBoard.getWidth() / 6 * 5, gamePanel.getHeight() + scoreBoard.getHeight());
        this.add(reset);
        reset.addActionListener(e -> {
            for (int i = 0; i < xCount; i++) {
                for (int j = 0; j < yCount; j++) {
                    controller.getGamePanel().getMineField()[i][j].setStatus(GridStatus.Covered);
                    repaint();
                }
            }
            controller.getP1().setScore(0);
            controller.getP2().setScore(0);
            controller.getP1().setMistake(0);
            controller.getP2().setMistake(0);
            GameController.number = 1;
            GameController.counter = 1;

            controller.getGamePanel().getPropGrid1().setGridStatus(initprop1);
            controller.getGamePanel().getPropGrid2().setGridStatus(initprop2);
            controller.getGamePanel().getPropGrid1().repaint();
            controller.getGamePanel().getPropGrid2().repaint();
            repaint();
            ////////分数重置部分
        });
        ////////////////////////////////
        //(计分系统的归零还没有实现)
//////////////////


        ///////////////////////////////

        ///////////////////////////////(用一个新棋盘重新玩)
        JButton restart = new JButton("重开一局");
        restart.setSize(scoreBoard.getWidth() / 6, 35);
        restart.setLocation(scoreBoard.getWidth() / 6 * 4, gamePanel.getHeight() + scoreBoard.getHeight());
        this.add(restart);
        restart.addActionListener(e -> {
            for (int i = 0; i < xCount; i++) {
                for (int j = 0; j < yCount; j++) {
                    controller.getGamePanel().getMineField()[i][j].setStatus(GridStatus.Covered);
                }
            }
            controller.getGamePanel().reStart(xCount, yCount, mineCount);
            controller.getP1().setScore(0);
            controller.getP2().setScore(0);
            controller.getP1().setMistake(0);
            controller.getP2().setMistake(0);
            GameController.number = 1;
            GameController.counter = 1;
            controller.getGamePanel().getPropGrid1().setGridStatus(initprop1);
            controller.getGamePanel().getPropGrid2().setGridStatus(initprop2);
            controller.getGamePanel().getPropGrid1().repaint();
            controller.getGamePanel().getPropGrid2().repaint();
            repaint();
        });
        //////////////////////////////分数在点击按钮时不能实现立即重置


        //////////////////////////(返回键方法)
        JButton Back = new JButton("返回");
        Back.setSize(scoreBoard.getWidth() / 6, 35);
        Back.setLocation(scoreBoard.getWidth() / 6 * 3, gamePanel.getHeight() + scoreBoard.getHeight());
        this.add(Back);
        Back.addActionListener(e -> {
            GridComponent.setGridSize(55);
            controller.getOnTurnPlayer().getTimer().stop();
            this.dispose();
            ModeChooseFrame modeChooseFrame = new ModeChooseFrame();
            GameController.number = 1;
            GameController.counter = 1;
            /////在自定义时，格子的大小尺寸可能会出现BUG
        });
        ////////////////////////


        ///////////////////////(设置玩家一次能操作多少步)
        JButton set = new JButton("设置");
        set.setSize(scoreBoard.getWidth() / 6, 35);
        set.setLocation(scoreBoard.getWidth() / 6 * 2, gamePanel.getHeight() + scoreBoard.getHeight());
        this.add(set);
        set.addActionListener(e -> {
            String Number = JOptionPane.showInputDialog(null, "请输入行动次数：\n", "设置玩家的行动次数（1-5） ", JOptionPane.PLAIN_MESSAGE);
            if (Number != null && Number.length() != 0) {
                char[] b = Number.toCharArray();
                for (char c : b) {
                    if (!Character.isDigit(c)) {
                        return;
                    }
                }
                int number1 = Integer.parseInt(Number);
                controller.setNumber(number1);
                controller.getScoreBoard().update();
            }
        });
        ///////////////////////////////////////

        //////////////////////(设置作弊模式)
        JButton cheat = new JButton("一键全开");
        cheat.setSize(scoreBoard.getWidth() / 6, 35);
        cheat.setLocation(scoreBoard.getWidth() / 6, gamePanel.getHeight() + scoreBoard.getHeight());
        this.add(cheat);
        cheat.addActionListener(e -> {
            if (counter == 0) {
                for (int i = 0; i < this.getxCount(); i++) {
                    for (int j = 0; j < this.getyCount(); j++) {
                        if (gamePanel.getChessboard()[i][j] == -1
                                && !gamePanel.getMineField()[i][j].isFlaged()
                        ) {
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
                                && !gamePanel.getMineField()[i][j].isBombed() && !gamePanel.getMineField()[i][j].isFlaged()) {
                            gamePanel.getMineField()[i][j].setStatus(GridStatus.Covered);
                        }
                    }
                }
                counter = 0;
            }
            repaint();
        });
        //////////////////////////////////


        JButton save = new JButton("快速存档");
        save.setSize(scoreBoard.getWidth() / 6, 35);
        save.setLocation(0, gamePanel.getHeight() + scoreBoard.getHeight());
        add(save);
        save.addActionListener(e -> {
            try {
                String fileName = JOptionPane.showInputDialog(null, "输入存档名\n", "请在此输入存档名", JOptionPane.PLAIN_MESSAGE);
                controller.writeDataToFile(fileName + ".txt");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        });

        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }


    ////////////////////////////////////人机的构造方法/////////////////////////////
    public MainFrame(int xCount, int yCount, int mineCount,Player p1) {
        //todo: change the count of xCount, yCount and mineCount by passing parameters from constructor


        this.xCount = xCount;       //格子的行数
        this.yCount = yCount;       //格子的列数
        this.mineCount = mineCount;   //雷数1

        this.setTitle("扫雷");
        this.setLayout(null);
        this.setSize(yCount * GridComponent.gridSize + (xCount * GridComponent.gridSize + 125) / 6 - 5, xCount * GridComponent.gridSize + 125);
        ///根据格子的SIZE来决定MainFrame的SIZE
        this.setLocationRelativeTo(null);

        MusicAudioClip mac = new MusicAudioClip();
        try {
            mac.setAudioClip(Applet.newAudioClip((new java.io.File("Killer.wav")).toURL()));//填写你自己的文件路径
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        mac.loop();//循环播放

        Player p2 = new Player();


        controller = new GameController(p1, p2);
        GamePanel gamePanel = new GamePanel(xCount, yCount, mineCount);
        controller.setGamePanel(gamePanel);
        ScoreBoard scoreBoard = new ScoreBoard(p1, p2, xCount, yCount);
        controller.setScoreBoard(scoreBoard);

        if (P1 == Charactor.No){
            JLabel Play1 = new JLabel(imageIcon5);
            this.add(Play1);
            Play1.setVisible(true);
            Play1.setBounds(yCount * GridComponent.gridSize, -20,
                    (xCount * GridComponent.gridSize + 125) / 6 - 25, xCount * GridComponent.gridSize / 8 * 3);
        }

        if (P1 == Charactor.Rondom) {
            JLabel Play1 = new JLabel(imageIcon4);
            this.add(Play1);
            Play1.setVisible(true);
            Play1.setBounds(yCount * GridComponent.gridSize, -20,
                    (xCount * GridComponent.gridSize + 125) / 6 - 25, xCount * GridComponent.gridSize / 8 * 3);
        } else if (P1 == Charactor.KingCrisom) {
            JLabel Play1 = new JLabel(imageIcon3);
            this.add(Play1);
            Play1.setVisible(true);
            Play1.setBounds(yCount * GridComponent.gridSize, -20,
                    (xCount * GridComponent.gridSize + 125) / 6 - 25, xCount * GridComponent.gridSize / 8 * 3);
        } else if (P1 == Charactor.KillerQueen) {
            JLabel Play1 = new JLabel(imageIcon2);
            this.add(Play1);
            Play1.setVisible(true);
            Play1.setBounds(yCount * GridComponent.gridSize, -20,
                    (xCount * GridComponent.gridSize + 125) / 6 - 25, xCount * GridComponent.gridSize / 8 * 3);
        } else if (P1 == Charactor.TheWorld) {
            JLabel Play1 = new JLabel(imageIcon1);
            this.add(Play1);
            Play1.setVisible(true);
            Play1.setBounds(yCount * GridComponent.gridSize, -20,
                    (xCount * GridComponent.gridSize + 125) / 6 - 25, xCount * GridComponent.gridSize / 8 * 3);
        }


        if (P2 == Charactor.Rondom) {
            JLabel Play1 = new JLabel(imageIcon4);
            this.add(Play1);
            Play1.setVisible(true);
            Play1.setBounds(yCount * GridComponent.gridSize, xCount * GridComponent.gridSize / 8 * 3 + 90,
                    (xCount * GridComponent.gridSize + 125) / 6 - 25, xCount * GridComponent.gridSize / 8 * 3);
        } else if (P2 == Charactor.KingCrisom) {
            JLabel Play1 = new JLabel(imageIcon3);
            this.add(Play1);
            Play1.setVisible(true);
            Play1.setBounds(yCount * GridComponent.gridSize, xCount * GridComponent.gridSize / 8 * 3 + 90,
                    (xCount * GridComponent.gridSize + 125) / 6 - 25, xCount * GridComponent.gridSize / 8 * 3);
        } else if (P2 == Charactor.KillerQueen) {
            JLabel Play1 = new JLabel(imageIcon2);
            this.add(Play1);
            Play1.setVisible(true);
            Play1.setBounds(yCount * GridComponent.gridSize, xCount * GridComponent.gridSize / 8 * 3 + 90,
                    (xCount * GridComponent.gridSize + 125) / 6 - 25, xCount * GridComponent.gridSize / 8 * 3);
        } else if (P2 == Charactor.TheWorld) {
            JLabel Play1 = new JLabel(imageIcon1);
            this.add(Play1);
            Play1.setVisible(true);
            Play1.setBounds(yCount * GridComponent.gridSize, xCount * GridComponent.gridSize / 8 * 3 + 90,
                    (xCount * GridComponent.gridSize + 125) / 6 - 25, xCount * GridComponent.gridSize / 8 * 3);
        }

        gamePanel.getPropGrid1().setLocation(yCount * GridComponent.gridSize, xCount * GridComponent.gridSize / 8 * 3 - 35);
        gamePanel.getPropGrid2().setLocation(yCount * GridComponent.gridSize, xCount * GridComponent.gridSize - 150);

        p1.setCharactor(P1);
        p2.setCharactor(P2);
        p2.setUserName(P2Name);
        scoreBoard.update();
        switch (p1.getCharactor()) {
            case Rondom: {
                int num = random.nextInt(4);
                switch (num) {
                    case 0: {
                        MainFrame.controller.getGamePanel().getPropGrid1().setGridStatus(Prop.No);
                        break;
                    }
                    case 1: {
                        MainFrame.controller.getGamePanel().getPropGrid1().setGridStatus(Prop.TheWorld);
                        break;
                    }
                    case 2: {
                        MainFrame.controller.getGamePanel().getPropGrid1().setGridStatus(Prop.KingCrisom);
                        break;
                    }
                    case 3: {
                        MainFrame.controller.getGamePanel().getPropGrid1().setGridStatus(Prop.KillerQueen);
                        break;
                    }
                }
            }
            case TheWorld: {
                MainFrame.controller.getGamePanel().getPropGrid1().setGridStatus(Prop.TheWorld);
                break;
            }
            case KingCrisom: {
                MainFrame.controller.getGamePanel().getPropGrid1().setGridStatus(Prop.KingCrisom);
                break;
            }
            case KillerQueen: {
                MainFrame.controller.getGamePanel().getPropGrid1().setGridStatus(Prop.KillerQueen);
                break;
            }
        }
        switch (p2.getCharactor()) {
            case Rondom: {
                int num = random.nextInt(4);
                switch (num) {
                    case 0: {
                        MainFrame.controller.getGamePanel().getPropGrid2().setGridStatus(Prop.No);
                        break;
                    }
                    case 1: {
                        MainFrame.controller.getGamePanel().getPropGrid2().setGridStatus(Prop.TheWorld);
                        break;
                    }
                    case 2: {
                        MainFrame.controller.getGamePanel().getPropGrid2().setGridStatus(Prop.KingCrisom);
                        break;
                    }
                    case 3: {
                        MainFrame.controller.getGamePanel().getPropGrid2().setGridStatus(Prop.KillerQueen);
                        break;
                    }
                }
            }
            case TheWorld: {
                MainFrame.controller.getGamePanel().getPropGrid2().setGridStatus(Prop.TheWorld);
                break;
            }
            case KingCrisom: {
                MainFrame.controller.getGamePanel().getPropGrid2().setGridStatus(Prop.KingCrisom);
                break;
            }
            case KillerQueen: {
                MainFrame.controller.getGamePanel().getPropGrid2().setGridStatus(Prop.KillerQueen);
                break;
            }
        }

        this.add(scoreBoard);
        this.add(gamePanel);



        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }


        /////////////////////////////////(原来的游戏再玩一遍)
        JButton reset = new JButton("本局归零");
        reset.setSize(scoreBoard.getWidth() / 6, 35);
        reset.setLocation(scoreBoard.getWidth() / 6 * 5, gamePanel.getHeight() + scoreBoard.getHeight());
        this.add(reset);
        reset.addActionListener(e -> {
            for (int i = 0; i < xCount; i++) {
                for (int j = 0; j < yCount; j++) {
                    controller.getGamePanel().getMineField()[i][j].setStatus(GridStatus.Covered);
                    repaint();
                }
            }
            controller.getP1().setScore(0);
            controller.getP2().setScore(0);
            controller.getP1().setMistake(0);
            controller.getP2().setMistake(0);
            GameController.number = 1;
            GameController.counter = 1;
            controller.getGamePanel().getPropGrid1().setGridStatus(Prop.No);
            controller.getGamePanel().getPropGrid2().setGridStatus(Prop.No);
            controller.getGamePanel().getPropGrid1().repaint();
            controller.getGamePanel().getPropGrid2().repaint();
            repaint();
            ////////分数重置部分
        });
        ////////////////////////////////
        //(计分系统的归零还没有实现)
//////////////////


        ///////////////////////////////

        ///////////////////////////////(用一个新棋盘重新玩)
        JButton restart = new JButton("重开一局");
        restart.setSize(scoreBoard.getWidth() / 6, 35);
        restart.setLocation(scoreBoard.getWidth() / 6 * 4, gamePanel.getHeight() + scoreBoard.getHeight());
        this.add(restart);
        restart.addActionListener(e -> {
            for (int i = 0; i < xCount; i++) {
                for (int j = 0; j < yCount; j++) {
                    controller.getGamePanel().getMineField()[i][j].setStatus(GridStatus.Covered);
                }
            }
            controller.getGamePanel().reStart(xCount, yCount, mineCount);
            controller.getP1().setScore(0);
            controller.getP2().setScore(0);
            controller.getP1().setMistake(0);
            controller.getP2().setMistake(0);
            GameController.number = 1;
            GameController.counter = 1;
            controller.getGamePanel().getPropGrid1().setGridStatus(Prop.No);
            controller.getGamePanel().getPropGrid2().setGridStatus(Prop.No);
            controller.getGamePanel().getPropGrid1().repaint();
            controller.getGamePanel().getPropGrid2().repaint();
            repaint();
        });
        //////////////////////////////分数在点击按钮时不能实现立即重置


        //////////////////////////(返回键方法)
        JButton Back = new JButton("返回");
        Back.setSize(scoreBoard.getWidth() / 6, 35);
        Back.setLocation(scoreBoard.getWidth() / 6 * 3, gamePanel.getHeight() + scoreBoard.getHeight());
        this.add(Back);
        Back.addActionListener(e -> {
            GridComponent.setGridSize(55);
            controller.getOnTurnPlayer().getTimer().stop();
            this.dispose();
            ModeChooseFrame modeChooseFrame = new ModeChooseFrame();
            GameController.number = 1;
            GameController.counter = 1;
            /////在自定义时，格子的大小尺寸可能会出现BUG
        });
        ////////////////////////


        ///////////////////////(设置玩家一次能操作多少步)
        JButton set = new JButton("设置");
        set.setSize(scoreBoard.getWidth() / 6, 35);
        set.setLocation(scoreBoard.getWidth() / 6 * 2, gamePanel.getHeight() + scoreBoard.getHeight());
        this.add(set);
        set.addActionListener(e -> {
            String Number = JOptionPane.showInputDialog(null, "请输入行动次数：\n", "设置玩家的行动次数（1-5） ", JOptionPane.PLAIN_MESSAGE);
            if (Number != null && Number.length() != 0) {
                char[] b = Number.toCharArray();
                for (char c : b) {
                    if (!Character.isDigit(c)) {
                        return;
                    }
                }
                int number1 = Integer.parseInt(Number);
                controller.setNumber(number1);
                controller.getScoreBoard().update();
            }
        });
        ///////////////////////////////////////

        //////////////////////(设置作弊模式)
        JButton cheat = new JButton("一键全开");
        cheat.setSize(scoreBoard.getWidth() / 6, 35);
        cheat.setLocation(scoreBoard.getWidth() / 6, gamePanel.getHeight() + scoreBoard.getHeight());
        this.add(cheat);
        cheat.addActionListener(e -> {
            if (counter == 0) {
                for (int i = 0; i < this.getxCount(); i++) {
                    for (int j = 0; j < this.getyCount(); j++) {
                        if (gamePanel.getChessboard()[i][j] == -1
                                && !gamePanel.getMineField()[i][j].isFlaged()
                        ) {
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
                                && !gamePanel.getMineField()[i][j].isBombed() && !gamePanel.getMineField()[i][j].isFlaged()) {
                            gamePanel.getMineField()[i][j].setStatus(GridStatus.Covered);
                        }
                    }
                }
                counter = 0;
            }
            repaint();
        });
        //////////////////////////////////


        JButton save = new JButton("快速存档");
        save.setSize(scoreBoard.getWidth() / 6, 35);
        save.setLocation(0, gamePanel.getHeight() + scoreBoard.getHeight());
        add(save);
        save.addActionListener(e -> {
            try {
                String fileName = JOptionPane.showInputDialog(null, "输入存档名", "请在此输入存档名", JOptionPane.PLAIN_MESSAGE);
                controller.writeDataToFile(fileName + ".txt");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        });
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
