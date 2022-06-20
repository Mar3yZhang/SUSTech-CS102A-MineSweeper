import javax.imageio.ImageIO;
import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Random;

public class GridComponent extends BasicComponent {

    public static int gridSize = 55;
    private int row;
    private int col;
    private GridStatus status = GridStatus.Covered;
    private int content = 0;
    private boolean bombed = false;
    private boolean flaged = false;
    private Prop prop = Prop.No;
    public static int x;
    public static int y;
    private final Random random = new Random();

    public Prop getProp() {
        return prop;
    }

    public void setProp(Prop prop) {
        this.prop = prop;
    }

    public boolean isFlaged() {
        return flaged;
    }

    public void setFlaged(boolean flaged) {
        this.flaged = flaged;
    }

    public void setBombed(boolean bombed) {
        this.bombed = bombed;
    }


    public boolean isBombed() {
        return bombed;
    }

    public static int getGridSize() {
        return gridSize;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public GridStatus getStatus() {
        return status;
    }

    public int getContent() {
        return content;
    }

    public static void setGridSize(int gridSize) {
        GridComponent.gridSize = gridSize;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setStatus(GridStatus status) {
        this.status = status;
    }


    public GridComponent(int x, int y) {
        this.setSize(gridSize, gridSize);   //////（设置棋盘下画布的尺寸）
        this.row = x;        ////////（某一个格子的横坐标）
        this.col = y;        ////////（某一个格子的纵坐标）
    }

    public void computerOperateMiddle() {//中等人机
        int random1 = random.nextInt(100);//1-99随机数
        if (this.content != -1) {
            if (random1 < 70) {
                onMouseLeftClicked();
            } else {
                onMouseRightClicked();
            }
        }
        if(this.content==-1){
            if (random1 < 70) {
                onMouseRightClicked();
            } else {
                onMouseLeftClicked();
            }
        }

    }

    public void catchProp() {
        Prop onGrid;
        if(MainFrame.controller.getOnTurnPlayer()==MainFrame.controller.getP1()){
            onGrid=MainFrame.controller.getGamePanel().getPropGrid1().getGridStatus();
        }else {onGrid=MainFrame.controller.getGamePanel().getPropGrid2().getGridStatus();}
        if (MainFrame.controller.getOnTurnPlayer().getProp() == Prop.No
        &&onGrid==Prop.No) {
            if (this.prop == Prop.TheWorld) {
                MainFrame.controller.getOnTurnPlayer().setProp(Prop.TheWorld);
                if (MainFrame.controller.getOnTurnPlayer() == MainFrame.controller.getP1()) {
                    MainFrame.controller.getGamePanel().getPropGrid1().setGridStatus(Prop.TheWorld);
                } else {
                    MainFrame.controller.getGamePanel().getPropGrid2().setGridStatus(Prop.TheWorld);
                }
            }
            if (this.prop == Prop.KillerQueen) {
                MainFrame.controller.getOnTurnPlayer().setProp(Prop.KillerQueen);
                if (MainFrame.controller.getOnTurnPlayer() == MainFrame.controller.getP1()) {
                    MainFrame.controller.getGamePanel().getPropGrid1().setGridStatus(Prop.KillerQueen);
                } else {
                    MainFrame.controller.getGamePanel().getPropGrid2().setGridStatus(Prop.KillerQueen);
                }
            }
            if (this.prop == Prop.KingCrisom) {
                MainFrame.controller.getOnTurnPlayer().setProp(Prop.KingCrisom);
                if (MainFrame.controller.getOnTurnPlayer() == MainFrame.controller.getP1()) {
                    MainFrame.controller.getGamePanel().getPropGrid1().setGridStatus(Prop.KingCrisom);
                } else {
                    MainFrame.controller.getGamePanel().getPropGrid2().setGridStatus(Prop.KingCrisom);
                }
            }
            MainFrame.controller.getGamePanel().getPropGrid1().repaint();
            MainFrame.controller.getGamePanel().getPropGrid2().repaint();

        }
    }


    @Override

    public void onMouseLeftClicked() {
        x = row;
        y = col;
        System.out.printf("格子 (%d,%d) 被鼠标左键点击.\n", row, col);
        this.catchProp();
        if (this.status == GridStatus.Covered || this.status == GridStatus.Entered) {
            while (MainFrame.controller.getGamePanel().isFirstMine() && this.content == -1) {//防止首发触雷
                MainFrame.controller.getGamePanel().reStart(MainFrame.controller.getGamePanel().getChessboard().length,
                        MainFrame.controller.getGamePanel().getChessboard()[0].length,
                        MainFrame.controller.getGamePanel().getMineNum());
            }
            if (this.content == -1 && !MainFrame.controller.getGamePanel().isFirstMine()) {
                MainFrame.controller.getOnTurnPlayer().costScore();
                this.setBombed(true);

                MusicAudioClip mac = new MusicAudioClip();
                try {
                    mac.setAudioClip(Applet.newAudioClip((new java.io.File("Bomb.wav")).toURL()));//填写你自己的文件路径
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                mac.play();//播放
            }
            if (this.content == 0 && isOkToOpen(row, col)) {
                openZero(row, col);
                MusicAudioClip mac = new MusicAudioClip();
                try {
                    mac.setAudioClip(Applet.newAudioClip((new java.io.File("Click.wav")).toURL()));//填写你自己的文件路径
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                mac.play();//播放
            }
            this.status = GridStatus.Clicked;
            MusicAudioClip mac = new MusicAudioClip();
            try {
                mac.setAudioClip(Applet.newAudioClip((new java.io.File("Click.wav")).toURL()));//填写你自己的文件路径
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            mac.play();//播放
            repaint();
            MainFrame.controller.nextTurn();


            //TODO: 在左键点击一个格子的时候，还需要做什么？
            ////（若左键点击格子时触雷，格子下的所有数组随机数立即重置，实现第一步不会触雷）
        }
    }

    private boolean isOkToOpen(int x, int y) {
        boolean check = false;
        if (x >= 0 && y >= 0
                && x < MainFrame.controller.getGamePanel().getMineField().length
                && y < MainFrame.controller.getGamePanel().getMineField()[0].length
        ) {
            if (MainFrame.controller.getGamePanel().getMineField()[x][y].status == GridStatus.Covered
                    || MainFrame.controller.getGamePanel().getMineField()[x][y].status == GridStatus.Entered
            ) {
                check = true;
            }
        }
        return check;
    }

    private void openZero(int X, int Y) {
        if (MainFrame.controller.getGamePanel().getMineField()[X][Y].status == GridStatus.Clicked) {
            return;
        }
        if (MainFrame.controller.getGamePanel().getMineField()[X][Y].content != 0
                && MainFrame.controller.getGamePanel().getMineField()[X][Y].content != -1) {
            MainFrame.controller.getGamePanel().getMineField()[X][Y].catchProp();
            MainFrame.controller.getGamePanel().getMineField()[X][Y].setStatus(GridStatus.Clicked);
            MainFrame.controller.getGamePanel().getMineField()[X][Y].repaint();
            return;
        }
        MainFrame.controller.getGamePanel().getMineField()[X][Y].catchProp();
        MainFrame.controller.getGamePanel().getMineField()[X][Y].status = GridStatus.Clicked;
        MainFrame.controller.getGamePanel().getMineField()[X][Y].repaint();
        if (isOkToOpen(X - 1, Y - 1)) {
            openZero(X - 1, Y - 1);
        }
        if (isOkToOpen(X - 1, Y)) {
            openZero(X - 1, Y);
        }
        if (isOkToOpen(X - 1, Y + 1)) {
            openZero(X - 1, Y + 1);
        }
        if (isOkToOpen(X, Y - 1)) {
            openZero(X, Y - 1);
        }
        if (isOkToOpen(X, Y + 1)) {
            openZero(X, Y + 1);
        }
        if (isOkToOpen(X + 1, Y - 1)) {
            openZero(X + 1, Y - 1);
        }

        if (isOkToOpen(X + 1, Y + 1)) {
            openZero(X + 1, Y + 1);
        }
        if (isOkToOpen(X + 1, Y)) {
            openZero(X + 1, Y);
        }

    }

    @Override
    public void onMouseRightClicked() {
        x = row;
        y = col;
        this.catchProp();
        System.out.printf("格子 (%d,%d) 被鼠标右键点击.\n", row, col);
        if (this.status == GridStatus.Covered || this.status == GridStatus.Entered) {
            if (this.content == -1) {
                MainFrame.controller.getOnTurnPlayer().addScore();
                this.setFlaged(true);
                this.status = GridStatus.Flag;
                repaint();

                MusicAudioClip mac = new MusicAudioClip();
                try {
                    mac.setAudioClip(Applet.newAudioClip((new java.io.File("Flag.wav")).toURL()));//填写你自己的文件路径
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                mac.play();//播放
            }
            if (this.content != -1) {
                MainFrame.controller.getOnTurnPlayer().addMistake();
                this.status = GridStatus.Clicked;
                repaint();

                MusicAudioClip mac = new MusicAudioClip();

                try {
                    mac.setAudioClip(Applet.newAudioClip((new java.io.File("mistake.wav")).toURL()));//填写你自己的文件路径
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                mac.play();//循环播放
            }

            MainFrame.controller.nextTurn();
        }
    }

    @Override
    public void OnMouseEntered() {
//        System.out.println("Mouse Enters");
        if (this.status == GridStatus.Covered) {
            this.status = GridStatus.Entered;
            repaint();
        }

    }

    @Override
    public void OnMouseExited() {
//        System.out.println("Mouse Exits");
        if (this.status == GridStatus.Entered) {
            this.status = GridStatus.Covered;
            repaint();
        }
    }


    //TODO: 在右键点击一个格子的时候，还需要做什么？
    /////（需要检验该格子下的数字是否真的表示类，对该玩家得分和错误数进行更新）

    public void draw(Graphics g) {

        if (this.status == GridStatus.Covered || this.status == GridStatus.Entered) {
            if (MainFrame.skin == Skin.Day) {
                try {
                    Image img1 = ImageIO.read(new File("Enter.png"));
                    g.drawImage(img1, 0, 0, getWidth() - 1, getHeight() - 1, null);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (MainFrame.skin == Skin.Night) {
                try {
                    Image img1 = ImageIO.read(new File("EnterNight.png"));
                    g.drawImage(img1, 0, 0, getWidth() - 1, getHeight() - 1, null);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        if (this.status == GridStatus.Covered) {
            if (MainFrame.skin == Skin.Day) {
                if ((row + col) % 2 == 1) {
                    try {
                        Image img1 = ImageIO.read(new File("lawn1.png"));
                        g.drawImage(img1, 0, 0, getWidth() - 1, getHeight() - 1, null);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if ((row + col) % 2 == 0) {
                    try {
                        Image img2 = ImageIO.read(new File("lawn2.png"));
                        g.drawImage(img2, 0, 0, getWidth() - 1, getHeight() - 1, null);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (MainFrame.skin == Skin.Night) {
                if ((row + col) % 2 == 1) {
                    try {
                        Image img1 = ImageIO.read(new File("LawnNight1.png"));
                        g.drawImage(img1, 0, 0, getWidth() - 1, getHeight() - 1, null);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if ((row + col) % 2 == 0) {
                    try {
                        Image img2 = ImageIO.read(new File("LawnNight2.png"));
                        g.drawImage(img2, 0, 0, getWidth() - 1, getHeight() - 1, null);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }


        if (this.status == GridStatus.Clicked) {///(当格子状态为被点击时，添加雷与数量)
            if (MainFrame.skin == Skin.Day) {
                try {
                    Image img3 = ImageIO.read(new File("floor1.png"));
                    g.drawImage(img3, 0, 0, getWidth() - 1, getHeight() - 1, null);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (content != -1) {
                    g.setColor(Color.BLACK);
                    g.drawString(Integer.toString(content), getWidth() / 2 - 5, getHeight() / 2 + 5);
                }
                if (content == -1) {
                    try {
                        Image img = ImageIO.read(new File("bomb.png"));
                        g.drawImage(img, 0, 0, getWidth() - 1, getHeight() - 1, null);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }else if (MainFrame.skin == Skin.Night){
                try {
                    Image img3 = ImageIO.read(new File("floor1.png"));
                    g.drawImage(img3, 0, 0, getWidth() - 1, getHeight() - 1, null);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (content != -1) {
                    g.setColor(Color.BLACK);
                    g.drawString(Integer.toString(content), getWidth() / 2 - 5, getHeight() / 2 + 5);
                }
                if (content == -1) {
                    try {
                        Image img = ImageIO.read(new File("BombNight.png"));
                        g.drawImage(img, 0, 0, getWidth() - 1, getHeight() - 1, null);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }


        if (this.status == GridStatus.Flag) {///(当格子状态为旗帜时，添加旗帜图标)
            if (MainFrame.skin == Skin.Day) {
                try {
                    Image img4 = ImageIO.read(new File("flag.png"));
                    g.drawImage(img4, 0, 0, getWidth() - 1, getHeight() - 1, null);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else if (MainFrame.skin == Skin.Night){
                try {
                    Image img4 = ImageIO.read(new File("FlagNight.png"));
                    g.drawImage(img4, 0, 0, getWidth() - 1, getHeight() - 1, null);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setContent(int content) {
        this.content = content;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.printComponents(g);
        draw(g);
    }
    /////（本方法不能直接调用，通过repaint（）调用）
}
