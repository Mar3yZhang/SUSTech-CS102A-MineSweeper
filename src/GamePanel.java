import javax.swing.*;
import java.awt.*;
import java.util.Random;

/////根据JPanel制作了一个由Label和底下的数字组成的棋盘

public class GamePanel extends JPanel {
    private GridComponent[][] mineField;
    private int[][] chessboard;
    private final Random random = new Random();
    public static int counter = 0;
    private int propNumforEach = 0;
    private PropGrid propGrid1;
    private PropGrid propGrid2;

    public static void setCounter(int counter) {
        GamePanel.counter = counter;
    }

    public void setPropNumforEach(int propNumforEach) {
        this.propNumforEach = propNumforEach;
    }

    public void setPropGrid1(PropGrid propGrid1) {
        this.propGrid1 = propGrid1;
    }

    public void setPropGrid2(PropGrid propGrid2) {
        this.propGrid2 = propGrid2;
    }

    public static int getCounter() {
        return counter;
    }

    public int getPropNumforEach() {
        return propNumforEach;
    }

    public PropGrid getPropGrid1() {
        return propGrid1;
    }

    public PropGrid getPropGrid2() {
        return propGrid2;
    }

    public void setMineField(GridComponent[][] mineField) {
        this.mineField = mineField;
    }

    public void setChessboard(int[][] chessboard) {
        this.chessboard = chessboard;
    }

    public GridComponent[][] getMineField() {
        return mineField;
    }

    public int[][] getChessboard() {
        return chessboard;
    }

    public Random getRandom() {
        return random;
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


    public GamePanel(int xCount, int yCount, int mineCount) {  ////（定义棋盘）
        this.setVisible(true);
        this.setFocusable(true);
        this.setLayout(null);
        this.setBackground(Color.WHITE);
        this.setSize(yCount * GridComponent.gridSize + (xCount * GridComponent.gridSize + 125) / 6 + 10, GridComponent.gridSize * xCount);
        initialGame(xCount, yCount, mineCount);
        setprop(Prop.KillerQueen);
        setprop(Prop.KingCrisom);
        setprop(Prop.TheWorld);//三种替身埋入雷区设置完毕
//        for(GridComponent[] a:mineField){
//            for(GridComponent b:a){
//                System.out.println(b.getProp());
//            }
//        }
        repaint();
    }

    public void initialGame(int xCount, int yCount, int mineCount) {

        mineField = new GridComponent[xCount][yCount];
        propGrid1 = new PropGrid();
        propGrid2 = new PropGrid();
        propGrid1.setLocation((yCount-1)*GridComponent.gridSize,0);
        propGrid2.setLocation(yCount*GridComponent.gridSize,(xCount-1)*GridComponent.gridSize);
        this.add(propGrid1);
        this.add(propGrid2);
        generateChessBoard(xCount, yCount, mineCount);
        for (int i = 0; i < xCount; i++) {
            for (int j = 0; j < yCount; j++) {
                GridComponent gridComponent = new GridComponent(i, j);
                gridComponent.setContent(chessboard[i][j]);//将chessboard的每个内容赋值给每个格子
                gridComponent.setLocation(j * GridComponent.gridSize, i * GridComponent.gridSize);
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
            if (chessboard[x][y] == -1) {
                i--;
            }
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
        for (GridComponent[] a : mineField
        ) {
            for (GridComponent b :
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
        for (GridComponent[] gridComponents : mineField) {
            for (GridComponent gridComponent : gridComponents) {
                if (gridComponent.getStatus() != GridStatus.Covered
                        && gridComponent.getStatus() != GridStatus.Entered) {
                    check = false;
                    break;//如果所有格子中有一个不是covered就不是第一次了
                }
            }
        }
        return check;
    }

    public void setPropNumforEach() {//合理设置道具数量
        if (getMineNum() > 10) {
            propNumforEach = getMineNum() / 10;
        } else {
            propNumforEach = 1;
        }
    }

    public void setprop(Prop prop) {//设置道具
        int x;
        int y;
        setPropNumforEach();
        for (int i = 0; i < propNumforEach; i++) {
            x = random.nextInt(getMineField().length);
            y = random.nextInt(getMineField()[0].length);
            if (getMineField()[x][y].getProp() != Prop.KillerQueen
                    && getMineField()[x][y].getProp() != Prop.KingCrisom
                    && getMineField()[x][y].getProp() != Prop.TheWorld){
                getMineField()[x][y].setProp(prop);
            }
        }
    }


    /**
     * 获取一个指定坐标的格子。
     * 注意请不要给一个棋盘之外的坐标哦~
     *
     * @param x 第x列
     * @param y 第y行
     * @return 该坐标的格子
     */
    public GridComponent getGrid(int x, int y) {
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
