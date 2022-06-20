import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Random;

public class Player {
    private static Random ran = new Random();
    private String userName;
    private int score = 0;
    private int mistake = 0;
    public int time = 20;
    private Timer timer = new Timer(1000, this::TimeDown);
    private Prop prop=Prop.No;
    private Charactor charactor = Charactor.No;
//    private PropGrid propGrid ;


    public Charactor getCharactor() {
        return charactor;
    }

    public void setCharactor(Charactor charactor) {
        this.charactor = charactor;
    }

    public void setProp(Prop prop) {
        this.prop = prop;
    }

    public Prop getProp() {
        return prop;
    }

    public void TimeDown(ActionEvent e) {
        this.time--;
        if (time <= 0) {
            time=0;
            timer.stop();
            MainFrame.controller.getOnTurnPlayer().costScore();
        }
        MainFrame.controller.getScoreBoard().update();
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public static Random getRan() {
        return ran;
    }

    public int getTime() {
        return time;
    }

    public Timer getTimer() {
        return timer;
    }


    public static void setRan(Random ran) {
        Player.ran = ran;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setMistake(int mistake) {
        this.mistake = mistake;
    }

    /**
     * 通过特定名字初始化一个玩家对象。
     *
     * @param userName 玩家的名字
     */
    public Player(String userName) {
        this.userName = userName;
    }

    /**
     * 通过默认名字初始化一个玩家对象。
     */
    public Player() {
        userName = "User#" + (ran.nextInt(9000) + 1000);
    }

    /**
     * 为玩家加一分。
     */
    public void addScore() {
        score++;
    }

    /**
     * 为玩家扣一分。
     */
    public void costScore() {
        score--;
    }

    /**
     * 为玩家增加一次失误数。
     */
    public void addMistake() {
        mistake++;
    }
public void costMistake(){
        mistake--;
}

    public int getScore() {
        return score;
    }

    public String getUserName() {
        return userName;
    }

    public int getMistake() {
        return mistake;
    }

}
