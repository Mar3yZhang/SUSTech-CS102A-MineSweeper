import javax.swing.*;
import java.awt.*;
import java.util.Random;

/////根据JPanel制作了一个由Label和底下的数字组成的棋盘

public class SingleGamePanel extends JPanel {
    private SingleGridComponent[][] mineField;
    private int[][] chessboard;
    private final Random random = new Random();
    public static int counter = 0;


    public void setMineField(SingleGridComponent[][] mineField) {
        this.mineField = mineField;
    }

    public void setChessboard(int[][] chessboard) {
        this.chessboard = chessboard;
    }

    public SingleGridComponent[][] getMineField() {
        return mineField;
    }

    public int[][] getChessboard() {
        return chessboard;
    }

    public Random getRandom() {
        return random;
    }

    public SingleGamePanel(int xCount, int yCount, int mineCount) {  ////（定义棋盘）
        this.setVisible(true);
        this.setFocusable(true);
        this.setLayout(null);
        this.setBackground(Color.WHITE);
        this.setSize(SingleGridComponent.gridSize * yCount, SingleGridComponent.gridSize * xCount);
        initialGame(xCount, yCount, mineCount);
        repaint();
    }
    /**
     * 初始化一个具有指定行列数格子、并埋放了指定雷数的雷区。
     *
     * @param xCount    count of grid in column
     *                  ////雷的列数
     * @param yCount    count of grid in row
     *                  ////雷的行数
     * @param mineCount mine count
     *                  ////雷的总数
     */

    public void initialGame(int xCount, int yCount, int mineCount) {

        mineField = new SingleGridComponent[xCount][yCount];
        generateChessBoard(xCount, yCount, mineCount);
        for (int i = 0; i < xCount; i++) {
            for (int j = 0; j < yCount; j++) {
                SingleGridComponent gridComponent = new SingleGridComponent(i, j);
                gridComponent.setContent(chessboard[i][j]);//将chessboard的每个内容赋值给每个格子
                gridComponent.setLocation(j * SingleGridComponent.gridSize, i * SingleGridComponent.gridSize);
                mineField[i][j] = gridComponent;
                this.add(mineField[i][j]);
            }
        }
    }


    public int searchMineAround(int x, int y, int[][] chessboard) {//搜索指定格子以及周围八个格子的雷数
        int counter = 0;
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (i >= 0 && i < chessboard.length
                        && j >= 0 && j < chessboard[0].length) {
                    if (chessboard[i][j] == -1) {
                        counter++;
                    }
                }
            }
        }
        return counter;
    }

    public void generateChessBoard(int xCount, int yCount, int mineCount) {
        //todo: generate chessboard by your own algorithm
        chessboard = new int[xCount][yCount];
        int x;
        int y;
        for (int i = 0; i < mineCount; i++) {//随机选择格子埋雷
            x = random.nextInt(xCount);
            y = random.nextInt(yCount);
            chessboard[x][y] = -1;
        }
        for (int i = 0; i < xCount; i++) {
            for (int j = 0; j < yCount; j++) {
                if (chessboard[i][j] != -1) {
                    chessboard[i][j] = searchMineAround(i, j, chessboard);
                }
            }
        }
        regenerate(chessboard, mineCount);
    }

    public void regenerate(int[][] chessboard, int mineCount) {
        for (int i = 0; i < chessboard.length; i++) {
            for (int j = 0; j < chessboard[0].length; j++) {
                if (chessboard[i][j] == -1 && searchMineAround(i, j, chessboard) == 9) {
                    generateChessBoard(chessboard.length, chessboard[0].length, mineCount);
                }
            }
        }
    }

    public void reStart(int xCount, int yCount, int MineNum) {
        generateChessBoard(xCount, yCount, MineNum);
        for (int i = 0; i < xCount; i++) {
            for (int j = 0; j < yCount; j++) {
                mineField[i][j].setContent(chessboard[i][j]);
            }
        }
    }

    public int getRestMineNum() {//计算剩余雷数
        int counter = 0;
        for (SingleGridComponent[] a : mineField
        ) {
            for (SingleGridComponent b :
                    a) {
                if (b.getStatus() == GridStatus.Covered && b.getContent() == -1) {
                    counter++;
                }
            }

        }
        return counter;
    }

    public int getMineNum() {//获得雷数
        int counter = 0;
        for (int[] a : chessboard
        ) {
            for (int b :
                    a) {
                if (b == -1) {
                    counter++;
                }
            }

        }
        return counter;
    }

    public boolean isFirstMine() {
        boolean check = true;
        for (SingleGridComponent[] gridComponents : mineField) {
            for (SingleGridComponent gridComponent : gridComponents) {
                if (gridComponent.getStatus() != GridStatus.Covered) {
                    check = false;
                    break;//如果所有格子中有一个不是covered就不是第一次了
                }
            }
        }
        return check;
    }



    /**
     * 获取一个指定坐标的格子。
     * 注意请不要给一个棋盘之外的坐标哦~
     *
     * @param x 第x列
     * @param y 第y行
     * @return 该坐标的格子
     */
    public SingleGridComponent getGrid(int x, int y) {
        try {
            return mineField[x][y];
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
