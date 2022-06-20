import javax.swing.*;
import java.awt.*;

/**
 * 此类的对象是一个计分板容器，通过传入玩家对象，
 * 可以用update()方法实时更新玩家的分数以及失误数。
 */
public class ScoreBoard extends JPanel {

    Player p1;
    Player p2;

    JLabel score1 = new JLabel();
    JLabel score2 = new JLabel();
    JLabel mistake1 = new JLabel();
    JLabel mistake2 = new JLabel();
    JLabel Time1 = new JLabel();
    JLabel onTurn = new JLabel();



    /**
     * 通过进行游戏的玩家来初始化计分板。这里只考虑了两个玩家的情况。
     * 如果想要2-4人游戏甚至更多，请自行修改(建议把所有玩家存在ArrayList)~
     *
     * @param p1 玩家1
     * @param p2 玩家2
     */
    public ScoreBoard(Player p1, Player p2, int xCount, int yCount) {

        GridBagConstraints c1 = new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0);
        this.add(score1, c1);
        score1.setOpaque(true);
        score1.setBackground(Color.GRAY);
        score1.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        /////（调整得分1Label的位置，颜色）


        GridBagConstraints c2 = new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0);
        this.add(score2, c2);
        score2.setOpaque(true);
        score2.setBackground(Color.GRAY);
        score2.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        /////（调整得分2Label的位置，颜色）


        GridBagConstraints c3 = new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0);
        this.add(mistake1, c3);
        mistake1.setOpaque(true);
        mistake1.setBackground(Color.GRAY);
        mistake1.setBorder(BorderFactory.createLineBorder(Color.RED));
        /////（调整失误1Label的位置，颜色）


        GridBagConstraints c4 = new GridBagConstraints(1, 2, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0);
        this.add(mistake2, c4);
        mistake2.setOpaque(true);
        mistake2.setBackground(Color.GRAY);
        mistake2.setBorder(BorderFactory.createLineBorder(Color.RED));
        /////（调整失误2Label的位置，颜色）

        GridBagConstraints c5 = new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0);
        this.add(Time1, c5);
        Time1.setOpaque(true);
        Time1.setBackground(Color.GRAY);
        Time1.setBorder(BorderFactory.createLineBorder(Color.BLUE));//调整player1计时器的位置颜色
        GridBagConstraints c6 = new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0);
        this.add(onTurn, c6);
        onTurn.setOpaque(true);
        onTurn.setBackground(Color.GRAY);
        onTurn.setBorder(BorderFactory.createLineBorder(Color.BLUE));//调整当前玩家信息的位置颜色

        this.setSize(yCount * GridComponent.gridSize, 40);
        /////(设置ScoreBoard这个JPanel的在Mainframe中的大小尺寸)
        this.setLocation(0, xCount * GridComponent.gridSize);
        /////（设置ScoreBoard这个JPanel在MainFrame中的具体位置）

        this.p1 = p1;
        this.p2 = p2;
        this.add(onTurn);
        this.add(score1);
        this.add(score2);
        this.add(Time1);
        this.add(mistake1);
        this.add(mistake2);
        /////（将Label添加到ScoreBoard这个JPanel里）

        this.setLayout(new GridLayout(2, 2));
        ////（将JPanel设置为Grid型布局）
        update();
        ////（利用update方法更新得分与失误表里的数值）
    }

    /**
     * 刷新计分板的数据。
     * 计分板会自动重新获取玩家的分数，并更新显示。
     */
    public void update() {
        score1.setText(String.format("%s : 得分数： %d  ", p1.getUserName(), p1.getScore()));
        score2.setText(String.format("%s : 得分数： %d  ", p2.getUserName(), p2.getScore()));
        mistake1.setText(String.format("%s : 失误数： %d ", p1.getUserName(), p1.getMistake()));
        mistake2.setText(String.format("%s : 失误数： %d ", p2.getUserName(), p2.getMistake()));
        Time1.setText(String.format("本回合剩余 : %ds", MainFrame.controller.getOnTurnPlayer().time));
//        if (GameController.number-GameController.counter!=0) {
            onTurn.setText(String.format("玩家: %s还能行动 %d 次", MainFrame.controller.getOnTurnPlayer().getUserName(), (GameController.number - GameController.counter+1)));
//        }else {onTurn.setText(String.format("玩家： %s还能行动%d次",MainFrame.controller.getOnTurnPlayer(),GameController.number));}
    }

}
