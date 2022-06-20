import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;

public class ComputerLevelChoose extends JFrame{
    JButton level1 = new JButton("简单的电脑");
    JButton level2 = new JButton("中等难度的电脑");
    JButton level3 = new JButton("令人发狂的电脑");
    public static int level;

    ComputerLevelChoose() {
        initInitialFrame();
        this.setTitle("选择难度");
        this.setResizable(true);
        this.setSize(500, 700);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

    }

    void initInitialFrame() {                               ///（将窗口初始化的方法）
        this.setLayout(new GridLayout(3, 1, 6, 6));

        this.add(level1);
        this.add(level2);
        this.add(level3);

        MusicAudioClip mac = new MusicAudioClip();
        try {
            mac.setAudioClip(Applet.newAudioClip((new File("InitialFrameBGM.wav")).toURL()));//填写你自己的文件路径
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        mac.loop();//循环播放

        level1.addActionListener(e -> {
            GridComponent.setGridSize(90);
            this.dispose();
            MainFrame mainFrame = new MainFrame(9, 9, 10,ModeChooseFrame.comeputer);
            level=1;
            mainFrame.setVisible(true);
            mac.stop();
        });

        level2.addActionListener(e -> {
            this.dispose();
            MainFrame mainFrame = new MainFrame(16, 16, 40,ModeChooseFrame.comeputer);
            mainFrame.setVisible(true);
            level=2;
            mac.stop();
        });

        level3.addActionListener(e -> {
            this.dispose();
            MainFrame mainFrame = new MainFrame(16, 30, 99,ModeChooseFrame.comeputer);
            mainFrame.setVisible(true);
            level=3;
            mac.stop();
        });


    }
}
