import javax.imageio.ImageIO;
import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class SingleGridComponent extends BasicComponent {

    public static int gridSize = 55;
    private int row;
    private int col;
    private GridStatus status = GridStatus.Covered;
    private int content = 0;
    private boolean bombed = false;
    private boolean flaged = false;
    private static int FlagCount = 0;

    public void setFlaged(boolean flaged) {
        this.flaged = flaged;
    }

    public static void setFlagCount(int flagCount) {
        FlagCount = flagCount;
    }

    public boolean isFlaged() {
        return flaged;
    }

    public static int getFlagCount() {
        return FlagCount;
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
        SingleGridComponent.gridSize = gridSize;
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


    public SingleGridComponent(int x, int y) {
        this.setSize(gridSize, gridSize);   //////（设置棋盘下画布的尺寸）
        this.row = x;        ////////（某一个格子的横坐标）
        this.col = y;        ////////（某一个格子的纵坐标）
    }

    @Override
    public void onMouseLeftClicked() {
        System.out.printf("格子 (%d,%d) 被鼠标左键点击.\n", row, col);
        if (this.status == GridStatus.Covered || this.status == GridStatus.Entered) {
            while (SingleMainFrame.controller.getGamePanel().isFirstMine() && this.content == -1) {//防止首发触雷
                SingleMainFrame.controller.getGamePanel().reStart(SingleMainFrame.controller.getGamePanel().getChessboard().length,
                        SingleMainFrame.controller.getGamePanel().getChessboard()[0].length,
                        SingleMainFrame.controller.getGamePanel().getMineNum());
            }
            if (this.content == -1 && !SingleMainFrame.controller.getGamePanel().isFirstMine()) {
                this.setBombed(true);
                ////打开全部格子
                JOptionPane.showMessageDialog(null, "游戏结束，你失败了", "失败", JOptionPane.ERROR_MESSAGE);
                SingleMainFrame.controller.ClickAllGrid();
                repaint();
            }

        }
        if (this.content == 0 && isOkToOpen(row, col)) {
            openZero(row, col);
            MusicAudioClip mac = new MusicAudioClip();
            try {
                mac.setAudioClip(Applet.newAudioClip((new File("Click.wav")).toURL()));//填写你自己的文件路径
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            mac.play();//播放
        }
        this.status = GridStatus.Clicked;
        MusicAudioClip mac = new MusicAudioClip();
        try {
            mac.setAudioClip(Applet.newAudioClip((new File("Click.wav")).toURL()));//填写你自己的文件路径
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        mac.play();//播放
        repaint();

        if (SingleMainFrame.controller.getGamePanel().getRestMineNum() == 0
                && FlagCount == SingleMainFrame.controller.getGamePanel().getMineNum()) {
            JOptionPane.showMessageDialog(null, "恭喜你，你赢得了单人模式的胜利！", "成功", JOptionPane.PLAIN_MESSAGE);
        }


        //////单人模式存在问题

        //TODO: 在左键点击一个格子的时候，还需要做什么？
        ////（若左键点击格子时触雷，格子下的所有数组随机数立即重置，实现第一步不会触雷）
    }


    private boolean isOkToOpen(int x, int y) {
        boolean check = false;
        if (x >= 0 && y >= 0
                && x < SingleMainFrame.controller.getGamePanel().getMineField().length
                && y < SingleMainFrame.controller.getGamePanel().getMineField()[0].length
        ) {
            if (SingleMainFrame.controller.getGamePanel().getMineField()[x][y].status == GridStatus.Covered
                    || SingleMainFrame.controller.getGamePanel().getMineField()[x][y].status == GridStatus.Entered
            ) {
                check = true;
            }
        }
        return check;
    }

    private void openZero(int X, int Y) {
        if (SingleMainFrame.controller.getGamePanel().getMineField()[X][Y].status == GridStatus.Clicked) {
            return;
        }
        if (SingleMainFrame.controller.getGamePanel().getMineField()[X][Y].content != 0
                && SingleMainFrame.controller.getGamePanel().getMineField()[X][Y].content != -1) {
            SingleMainFrame.controller.getGamePanel().getMineField()[X][Y].setStatus(GridStatus.Clicked);
            SingleMainFrame.controller.getGamePanel().getMineField()[X][Y].repaint();
            return;
        }
        SingleMainFrame.controller.getGamePanel().getMineField()[X][Y].status = GridStatus.Clicked;
        SingleMainFrame.controller.getGamePanel().getMineField()[X][Y].repaint();
        if (isOkToOpen(X, Y)) {
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
        System.out.printf("格子 (%d,%d) 被鼠标右键点击.\n", row, col);
        if (this.status == GridStatus.Covered || this.status == GridStatus.Entered) {
            if (this.content == -1) {
                this.setFlaged(true);
                this.setBombed(true);
                this.status = GridStatus.Flag;
                FlagCount++;
                repaint();
                MusicAudioClip mac = new MusicAudioClip();
                try {
                    mac.setAudioClip(Applet.newAudioClip((new File("Flag.wav")).toURL()));//填写你自己的文件路径
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                mac.play();//播放
            }

        }
        if (this.content != -1) {
            this.setFlaged(true);
            JOptionPane.showMessageDialog(null, "游戏结束，你失败了", "失败", JOptionPane.ERROR_MESSAGE);
            SingleMainFrame.controller.ClickAllGrid();
            repaint();

            MusicAudioClip mac = new MusicAudioClip();
            try {
                mac.setAudioClip(Applet.newAudioClip((new File("mistake.wav")).toURL()));//填写你自己的文件路径
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            mac.play();//播放
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
            if (SingleMainFrame.skin == Skin.Day) {
                try {
                    Image img1 = ImageIO.read(new File("Enter.png"));
                    g.drawImage(img1, 0, 0, getWidth() - 1, getHeight() - 1, null);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else if (SingleMainFrame.skin == Skin.Night){
                try {
                    Image img1 = ImageIO.read(new File("EnterNight.png"));
                    g.drawImage(img1, 0, 0, getWidth() - 1, getHeight() - 1, null);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        if (this.status == GridStatus.Covered) {
            if (SingleMainFrame.skin == Skin.Day) {
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
            }else if (SingleMainFrame.skin == Skin.Night){
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


        if (this.status == GridStatus.Clicked) {    ///(当格子状态为被点击时，添加雷与数量)
            if (SingleMainFrame.skin == Skin.Day) {
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
            }else if (SingleMainFrame.skin == Skin.Night){

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


        if (this.status == GridStatus.Flag) {  ///(当格子状态为旗帜时，添加旗帜图标)
            if (SingleMainFrame.skin == Skin.Day) {
                try {
                    Image img4 = ImageIO.read(new File("flag.png"));
                    g.drawImage(img4, 0, 0, getWidth() - 1, getHeight() - 1, null);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else if (SingleMainFrame.skin == Skin.Night){
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
