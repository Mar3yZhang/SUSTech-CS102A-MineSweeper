import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;

public class SkinChooseFrame extends JFrame {
    JButton Day = new JButton("白天");
    JButton Night = new JButton("黑夜");
    JLabel jLabel = new JLabel(" 扫雷 ", SwingConstants.CENTER);
//    JButton level3 = new JButton("高级");

    SkinChooseFrame() {
        initInitialFrame();
        this.setTitle("选择棋盘皮肤");
        this.setResizable(true);
        this.setSize(500, 700);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        Image image = Toolkit.getDefaultToolkit().createImage("logo.jpg");
        Icon logo = new ImageIcon(image);
        jLabel.setIcon(logo);
    }

    void initInitialFrame() {                               ///（将窗口初始化的方法）
        this.setLayout(new GridLayout(3, 1, 6, 6));

        this.add(jLabel);
        this.add(Day);
        this.add(Night);
//        this.add(level3);

        MusicAudioClip mac = new MusicAudioClip();
        try {
            mac.setAudioClip(Applet.newAudioClip((new File("InitialFrameBGM.wav")).toURL()));//填写你自己的文件路径
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        mac.loop();//循环播放

        Day.addActionListener(e -> {
           MainFrame.skin = Skin.Day;
           SingleMainFrame.skin = Skin.Day;
            this.dispose();
            mac.stop();
            ModeChooseFrame modeChooseFrame = new ModeChooseFrame();
        });

        Night.addActionListener(e -> {
            MainFrame.skin = Skin.Night;
            SingleMainFrame.skin = Skin.Night;
            this.dispose();
            mac.stop();
            ModeChooseFrame modeChooseFrame = new ModeChooseFrame();
        });

//        level3.addActionListener(e -> {
//            this.dispose();
//            MainFrame mainFrame = new MainFrame(16, 30, 99);
//            mainFrame.setVisible(true);
//            mac.stop();
//        });


    }
}