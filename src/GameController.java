import java.io.*;
import java.util.Random;
import java.util.Timer;

public class GameController {
    private int X;
    private int Y;
    private int MineNume;
    public static int x;
    public static int y;
    public static int minenum;

    public GameController() {
        this.onTurn = p1;
    }

    private Player p1;
    private Player p2;
    private Player None;
    private Player Winner;
    private Player Loser;
    private Player onTurn; ////当前操作的玩家
    private Player lastTurn;//上一次操作的玩家
    private final Random random = new Random();
    private GamePanel gamePanel;
    private ScoreBoard scoreBoard;
    public static int number = 1;   /////玩家设置的活动次数
    public static int counter = 1; /////用于统计玩家行动的次数；


    public Player getLastTurn() {
        return lastTurn;
    }

    public void setLastTurn(Player lastTurn) {
        this.lastTurn = lastTurn;
    }

    public Player getNone() {
        return None;
    }

    public Player getWinner() {
        return Winner;
    }

    public Player getLoser() {
        return Loser;
    }

    public Player getOnTurn() {
        return onTurn;
    }

    public static int getCounter() {
        return counter;
    }

    public void setP1(Player p1) {
        this.p1 = p1;
    }

    public void setP2(Player p2) {
        this.p2 = p2;
    }

    public void setNone(Player none) {
        None = none;
    }

    public void setWinner(Player winner) {
        Winner = winner;
    }

    public void setLoser(Player loser) {
        Loser = loser;
    }

    public void setOnTurn(Player onTurn) {
        this.onTurn = onTurn;
    }

    public static void setCounter(int counter) {
        GameController.counter = counter;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        GameController.number = number;
    }

    public void ClickAllGrid(){
        for (int i = 0; i <gamePanel.getChessboard().length; i++) {
            for (int j = 0; j <gamePanel.getChessboard()[0].length ; j++) {
                gamePanel.getMineField()[i][j].setStatus(GridStatus.Clicked);
            }
        }
        gamePanel.repaint();
    }

    public GameController(Player p1, Player p2) {
        this.init(p1, p2);
        this.onTurn = p1;
    }

    /**
     * 初始化游戏。在开始游戏前，应先调用此方法，给予游戏必要的参数。
     *
     * @param p1 玩家1
     * @param p2 玩家2
     */
    public void init(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;
        this.onTurn = p1;

        //TODO: 在初始化游戏的时候，还需要做什么？
    }

    /**
     * 进行下一个回合时应调用本方法。
     * 在这里执行每个回合结束时需要进行的操作。
     * <p>
     * (目前这里没有每个玩家进行n回合的计数机制的，请自行修改完成哦~）
     */
    public GridComponent getRandomCoveredGrid() {
        int x = random.nextInt(getGamePanel().getMineField().length);
        int y = random.nextInt(getGamePanel().getMineField()[0].length);
        GridComponent gridComponent = getGamePanel().getMineField()[x][y];
        if (gridComponent.getStatus() != GridStatus.Covered) {
            gridComponent = getRandomCoveredGrid();
        }
        return gridComponent;
    }

    public void nextTurn() {
        if (onTurn == p1) {
            if (counter < number) {
                counter++;
                if (counter > 1) {
                    lastTurn = p1;
                } else {
                    lastTurn = p2;
                }
            } else if (counter == number) {
                onTurn = p2;
                counter = 1;
                lastTurn = p1;
            }
        } else if (onTurn == p2) {
            if (counter < number) {
                counter++;
                if (counter > 1) {
                    lastTurn = p2;
                } else {
                    lastTurn = p1;
                }
            } else if (counter == number) {
                onTurn = p1;
                counter = 1;
                lastTurn = p2;
            }
        }
        lastTurn.getTimer().stop();
        lastTurn.time = 20;
        onTurn.getTimer().start();
        System.out.println("Now it is " + onTurn.getUserName() + "'s turn.");
        scoreBoard.update();
        //TODO: 在每个回合结束的时候，还需要做什么 (例如...检查游戏是否结束？)
        if (Math.abs(p1.getScore() - p2.getScore()) > MainFrame.controller.getGamePanel().getRestMineNum()) {
            if (p1.getScore() > p2.getScore()) {
                Winner = p1;
                Loser = p2;
                System.out.printf("The Winner is:%s  The Loser is:%s", Winner, Loser);
                ClickAllGrid();
                EndFrame endFrame = new EndFrame();
                for (int i = 0; i < gamePanel.getX(); i++) {
                    for (int j = 0; j < gamePanel.getY(); j++) {
                        gamePanel.getMineField()[i][j].setStatus(GridStatus.Clicked);
                        gamePanel.repaint();
                    }
                }
            } else {
                Winner = p2;
                Loser = p1;
                System.out.printf("The Winner is:%s  The Loser is:%s", Winner, Loser);
                ClickAllGrid();
                EndFrame endFrame = new EndFrame();
                for (int i = 0; i < gamePanel.getX(); i++) {
                    for (int j = 0; j < gamePanel.getY(); j++) {
                        gamePanel.getMineField()[i][j].setStatus(GridStatus.Clicked);
                        gamePanel.repaint();
                    }
                }
            }
//相差分大于剩余雷数,可能还需要结束操作
        } else if (p1.getScore() == p2.getScore() && MainFrame.controller.getGamePanel().getRestMineNum() == 0) {
            if (p1.getMistake() == p2.getMistake()) {//平局
                Winner = Loser = None;
                System.out.println("None is a Winner");
                ClickAllGrid();
                EndFrame endFrame = new EndFrame();
                for (int i = 0; i < gamePanel.getX(); i++) {
                    for (int j = 0; j < gamePanel.getY(); j++) {
                        gamePanel.getMineField()[i][j].setStatus(GridStatus.Clicked);
                        gamePanel.repaint();
                    }
                }
                gamePanel.repaint();
            } else {//根据失误判断输赢
                if (p1.getMistake() < p2.getMistake()) {
                    Winner = p1;
                    Loser = p2;
                    System.out.printf("The Winner is:%s  The Loser is:%s", Winner, Loser);
                    ClickAllGrid();
                    EndFrame endFrame = new EndFrame();
                    for (int i = 0; i < gamePanel.getX(); i++) {
                        for (int j = 0; j < gamePanel.getY(); j++) {
                            gamePanel.getMineField()[i][j].setStatus(GridStatus.Clicked);
                            gamePanel.repaint();
                        }
                    }
                    gamePanel.repaint();
                } else if (p1.getMistake() > p2.getMistake()) {
                    Winner = p2;
                    Loser = p1;
                    System.out.printf("The Winner is:%s  The Loser is:%s", Winner, Loser);
                    ClickAllGrid();
                    EndFrame endFrame = new EndFrame();
                    for (int i = 0; i < gamePanel.getX(); i++) {
                        for (int j = 0; j < gamePanel.getY(); j++) {
                            gamePanel.getMineField()[i][j].setStatus(GridStatus.Clicked);
                            gamePanel.repaint();
                        }
                    }
                }
            }
        }
        if (onTurn == ModeChooseFrame.comeputer) {
            GridComponent gridComponent = getRandomCoveredGrid();
            switch (ComputerLevelChoose.level) {
                case 1: {
                    gridComponent.onMouseLeftClicked();
                    break;
                }
                case 2: {
                    gridComponent.computerOperateMiddle();
                    break;
                }
                case 3: {
                    if (gridComponent.getContent() == -1) {
                        gridComponent.onMouseRightClicked();
                    } else {
                        gridComponent.onMouseLeftClicked();
                    }
                    break;
                }
            }

        }
    }

    public void killerQueen() {
        getGamePanel().getMineField()[GridComponent.x][GridComponent.y].setStatus(GridStatus.Covered);
        if (getOnTurnPlayer().getTime() <= 0) {
            getOnTurnPlayer().addScore();
        }
        if (getGamePanel().getMineField()[GridComponent.x][GridComponent.y].getContent() == -1
                && getGamePanel().getMineField()[GridComponent.x][GridComponent.y].isFlaged()) {//这个格子是雷而且被flag了
            getGamePanel().getMineField()[GridComponent.x][GridComponent.y].setFlaged(false);//让格子变回不flag
            getLastTurn().costScore();//flag过就把加的分扣了

        } else if (getGamePanel().getMineField()[GridComponent.x][GridComponent.y].getContent() == -1
                && getGamePanel().getMineField()[GridComponent.x][GridComponent.y].isBombed()) {//这个格子是雷而且炸过了
            getGamePanel().getMineField()[GridComponent.x][GridComponent.y].setBombed(false);//让格子没炸
            getLastTurn().addScore();//炸过就把扣的分加回来
        } else if (getGamePanel().getMineField()[GridComponent.x][GridComponent.y].getContent() != -1) {
            if (getGamePanel().getMineField()[GridComponent.x][GridComponent.y].isFlaged()) {
                getLastTurn().costMistake();//如果是失误插旗，就把失误数减了
            }
        }
        counter--;
        getGamePanel().getMineField()[GridComponent.x][GridComponent.y].setStatus(GridStatus.Covered);
        setOnTurn(getLastTurn());//把回合返回给上一回合
        getGamePanel().getMineField()[GridComponent.x][GridComponent.y].repaint();
        getScoreBoard().repaint();
    }

    public void theWorld() {
        getOnTurnPlayer().getTimer().stop();
    }

    public void kingCrisom() {
        getOnTurnPlayer().time -= 10;
    }

    /**
     * 获取正在进行当前回合的玩家。
     *
     * @return 正在进行当前回合的玩家
     */
    public Player getOnTurnPlayer() {
        return onTurn;
    }


    public Player getP1() {
        return p1;
    }

    public Player getP2() {
        return p2;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public ScoreBoard getScoreBoard() {
        return scoreBoard;
    }

    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setScoreBoard(ScoreBoard scoreBoard) {
        this.scoreBoard = scoreBoard;
    }


    public void writeDataToFile(String fileName) throws IOException {
        //todo: write data into file\
        /////一个文件用来存储4种数据

        ////第1行： 储存雷盘的行数与列数与雷数                   !
        ////第2行： 储存整个雷盘的底层数                    !
        ////第3行： 当前玩家的得分与失误数    按照 分 错 名 具 角 的顺序   !
        ////第4行： 当前棋盘所有打开且不为雷的格子
        ////第5行： 当前棋盘所有打开且为雷的格子
        ////第6行； 当前状态为旗的格子


        File file = new File(fileName);
        FileWriter fw = new FileWriter(file);

        System.out.println("----------");

        try {
            fw.write(Integer.toString(gamePanel.getChessboard().length)); ////行数
            fw.flush();
            fw.write(",");
            fw.flush();
            fw.write(Integer.toString(gamePanel.getChessboard()[0].length)); ////列数
            fw.flush();
            fw.write(",");
            fw.flush();
            fw.write(Integer.toString(gamePanel.getMineNum()));             ////雷数
            fw.flush();
            fw.write(",");
            fw.flush();
            fw.write(Integer.toString(number));
            fw.flush();
            fw.write(",");
            fw.flush();
            fw.write(Integer.toString(counter));
            fw.flush();
            fw.write(",");
            fw.flush();
            fw.write(String.valueOf(MainFrame.P1));
            fw.flush();
            fw.write(",");
            fw.flush();
            fw.write(String.valueOf(MainFrame.P2));
            fw.flush();

            System.out.printf("%d行，%d列,%d雷数", gamePanel.getChessboard().length,
                    gamePanel.getChessboard()[0].length,
                    gamePanel.getMineNum());
            System.out.println();
        } catch (Exception e) {
            System.out.println("错误操作");
        }

        fw.write("\r\n");


        System.out.println("----------");
        for (int i = 0; i < gamePanel.getChessboard().length; i++) {
            for (int j = 0; j < gamePanel.getChessboard()[0].length; j++) {
                try {
                    fw.write(Integer.toString(gamePanel.getChessboard()[i][j]));
                    fw.flush();
                    fw.write(",");
                    fw.flush();
                    System.out.print(gamePanel.getChessboard()[i][j]);
                } catch (Exception e) {
                    System.out.println("错误操作");
                }
            }
            System.out.println();
        }

        fw.write("\r\n");
        System.out.println("----------");


        fw.write(Integer.toString(p1.getScore()));
        fw.flush();
        fw.write(",");
        fw.flush();
        fw.write(Integer.toString(p1.getMistake()));
        fw.flush();
        fw.write(",");
        fw.flush();
        fw.write(p1.getUserName());
        fw.flush();
        fw.write(",");
        fw.write(String.valueOf(getGamePanel().getPropGrid1().getGridStatus()));
        fw.flush();
        fw.write(",");
        fw.flush();
        fw.write(Integer.toString(p2.getScore()));
        fw.flush();
        fw.write(",");
        fw.flush();
        fw.write(Integer.toString(p2.getMistake()));
        fw.flush();
        fw.write(",");
        fw.flush();
        fw.write(p2.getUserName());
        fw.flush();
        fw.write(",");
        fw.flush();
        fw.write(String.valueOf(getGamePanel().getPropGrid2().getGridStatus()));
        fw.flush();
        fw.write(",");
        fw.flush();
        System.out.println(p1.getScore());
        System.out.println(p1.getMistake());
        System.out.println(p1.getUserName());
        System.out.println(p1.getProp());
        System.out.println(p2.getScore());
        System.out.println(p2.getMistake());
        System.out.println(p2.getUserName());
        System.out.println(p2.getProp());

        fw.write("\r\n");
        System.out.println("----------");


        for (int i = 0; i < gamePanel.getChessboard().length; i++) {
            for (int j = 0; j < gamePanel.getChessboard()[0].length; j++) {
                try {
                    if (gamePanel.getMineField()[i][j].getStatus() == GridStatus.Clicked
                            && gamePanel.getChessboard()[i][j] != -1) {
                        fw.write(Integer.toString(i));
                        fw.flush();
                        fw.write(",");
                        fw.flush();
                        fw.write(Integer.toString(j));
                        fw.flush();
                        fw.write(",");
                        fw.flush();
                        System.out.printf("%d,%d", i, j);
                        System.out.println();
                    }
                } catch (Exception e) {
                    System.out.println("错误操作");
                }
            }
        }
        fw.write("\r\n");


        System.out.println("----------");


        for (int i = 0; i < gamePanel.getChessboard().length; i++) {
            for (int j = 0; j < gamePanel.getChessboard()[0].length; j++) {
                try {
                    if (gamePanel.getMineField()[i][j].getStatus() == GridStatus.Clicked
                            && gamePanel.getChessboard()[i][j] == -1) {
                        fw.write(Integer.toString(i));
                        fw.flush();
                        fw.write(",");
                        fw.flush();
                        fw.write(Integer.toString(j));
                        fw.flush();
                        fw.write(",");
                        fw.flush();
                        System.out.printf("%d,%d", i, j);
                        System.out.println();
                    }
                } catch (Exception e) {
                    System.out.println("错误操作");
                }
            }
        }
        fw.write("\r\n");


        System.out.println("----------");


        for (int i = 0; i < gamePanel.getChessboard().length; i++) {
            for (int j = 0; j < gamePanel.getChessboard()[0].length; j++) {
                try {
                    if (gamePanel.getMineField()[i][j].getStatus() == GridStatus.Flag) {
                        fw.write(Integer.toString(i));
                        fw.flush();
                        fw.write(",");
                        fw.flush();
                        fw.write(Integer.toString(j));
                        fw.flush();
                        fw.write(",");
                        fw.flush();
                        System.out.printf("%d,%d", i, j);
                        System.out.println();
                    }
                } catch (Exception e) {
                    System.out.println("错误操作");
                }
            }
        }


        fw.close();
    }

////第1行： 储存雷盘的行数与列数与雷数 及行动次数                  !
    ////第2行： 储存整个雷盘的底层数                    !
    ////第3行： 当前玩家的得分与失误数        !
    ////第4行： 当前棋盘所有打开且不为雷的格子
    ////第5行： 当前棋盘所有打开且为雷的格子
    ////第6行； 当前状态为旗的格子
    ////可以尝试返回一个GamePanel和ScoreBoard

    public void readxymAndCharacter(String fileName) throws FileNotFoundException {
        String[] Line1 = new String[0];
        FileReader fr = new FileReader(fileName + ".txt");
        BufferedReader bf = new BufferedReader(fr);
        String Tmp;
        try {
            Tmp = bf.readLine();
            Line1 = Tmp.split(",");
            this.X = Integer.parseInt(Line1[0]);
            this.Y = Integer.parseInt(Line1[1]);
            x = X;
            y = Y;
            MineNume = Integer.parseInt(Line1[2]);
            minenum = MineNume;
            MainFrame.P1=Charactor.valueOf(Line1[5]);
            MainFrame.P2=Charactor.valueOf(Line1[6]);
//            System.out.printf("%d,%d,%d", Integer.parseInt(Line1[0]), Integer.parseInt(Line1[1]), Integer.parseInt(Line1[2]));
            System.out.println();
        } catch (Exception e) {
            System.out.println("mistake");
        }
    }

    public void readFileData(String fileName) throws FileNotFoundException {
        //todo: read date from file

        String[] Line1 = new String[0];
        String[] Line2;
        String[] Line3 = new String[0];
        String[] Line4;
        String[] Line5;
        String[] Line6;
        String[] Line7;
        String Tmp;
        FileReader fr = new FileReader(fileName);
        BufferedReader bf = new BufferedReader(fr);


        System.out.println("---------------------");

        try {
            Tmp = bf.readLine();
            Line1 = Tmp.split(",");
            this.X = Integer.parseInt(Line1[0]);
            this.Y = Integer.parseInt(Line1[1]);
            x = X;
            y = Y;
            MineNume = Integer.parseInt(Line1[2]);
            number = Integer.parseInt(Line1[3]);
            counter = Integer.parseInt(Line1[4]);
            minenum = MineNume;
//            System.out.printf("%d,%d,%d", Integer.parseInt(Line1[0]), Integer.parseInt(Line1[1]), Integer.parseInt(Line1[2]));
            System.out.println();
        } catch (Exception e) {
            System.out.println("mistake");
        }

        System.out.println("++++从TXT中读入棋盘++++++");
        try {
            Tmp = bf.readLine();
            Line2 = Tmp.split(",");


            for (int i = 0; i < Line2.length; ) {
                for (int j = 0; j < Integer.parseInt(Line1[0]); j++) {
                    for (int k = 0; k < Integer.parseInt(Line1[1]); k++) {
                        this.getGamePanel().getChessboard()[j][k] = Integer.parseInt(Line2[i]);
                        this.getGamePanel().getMineField()[j][k].setContent(Integer.parseInt(Line2[i]));
                        i++;
                    }
                }
            }

            for (int[] ints : this.getGamePanel().getChessboard()) {
                for (int anInt : ints) {
                    System.out.print(anInt);
                }
//                System.out.println();
            }
        } catch (Exception e) {
            System.out.println("mistake");
        }
        System.out.println("++++++++++++++++");


        try {
            Tmp = bf.readLine();
            Line3 = Tmp.split(",");
        } catch (Exception e) {
            System.out.println("mistake");
        }
        this.p1.setScore(Integer.parseInt(Line3[0]));
        this.p1.setMistake(Integer.parseInt(Line3[1]));
        this.p1.setUserName(Line3[2]);
        this.p1.setProp(Prop.valueOf(Line3[3]));
        this.getGamePanel().getPropGrid1().setGridStatus(Prop.valueOf(Line3[3]));
        this.p2.setScore(Integer.parseInt(Line3[4]));
        this.p2.setMistake(Integer.parseInt(Line3[5]));
        this.p2.setUserName(Line3[6]);
        this.p2.setProp(Prop.valueOf(Line3[7]));
        this.getGamePanel().getPropGrid2().setGridStatus(Prop.valueOf(Line3[7]));


        /////////////////////////////////////////////////////////////////////////////
        try {
            Tmp = bf.readLine();
            if (Tmp != null && !Tmp.equals("")) {
                Line4 = Tmp.split(",");
                for (int i = 0; i < Line4.length; i = i + 2) {
                    int X = Integer.parseInt(Line4[i]);
                    int Y = Integer.parseInt(Line4[i + 1]);
                    this.getGamePanel().getMineField()[X][Y].setStatus(GridStatus.Clicked);
                }
            }
            this.getGamePanel().repaint();
        } catch (Exception e) {
            System.out.println("mistake");
        }
        ///////////////////第五行存雷格///////////////////////////////////


        try {
            Tmp = bf.readLine();
            if (Tmp != null && !Tmp.equals("")) {
                Line5 = Tmp.split(",");
                for (int i = 0; i < Line5.length; i = i + 2) {
                    int X = Integer.parseInt(Line5[i]);
                    int Y = Integer.parseInt(Line5[i + 1]);
                    this.getGamePanel().getMineField()[X][Y].setStatus(GridStatus.Clicked);
                    this.getGamePanel().getMineField()[X][Y].setBombed(true);
                }
            }
            this.getGamePanel().repaint();
        } catch (Exception e) {
            System.out.println("mistake");
        }

        ///////////////////第六行存棋格//////////////////////////////////

        try {
            Tmp = bf.readLine();
            if (Tmp != null && !Tmp.equals("")) {
                Line6 = Tmp.split(",");
                for (int i = 0; i < Line6.length; i = i + 2) {
                    int X = Integer.parseInt(Line6[i]);
                    int Y = Integer.parseInt(Line6[i + 1]);
                    this.getGamePanel().getMineField()[X][Y].setStatus(GridStatus.Flag);
                    this.getGamePanel().getMineField()[X][Y].setFlaged(true);
                }
            }
            this.getGamePanel().repaint();
        } catch (Exception e) {
            System.out.println("mistake");
        }


    }
}
