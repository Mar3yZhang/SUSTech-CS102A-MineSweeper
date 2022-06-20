import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.net.MalformedURLException;

public class SingleLevelChoose extends JFrame {
    JButton level1 = new JButton("初级");
    JButton level2 = new JButton("中级");
    JButton level3 = new JButton("高级");
    JLabel jLabel = new JLabel(" 扫雷 ", SwingConstants.CENTER);

    SingleLevelChoose() {
        initInitialFrame();
        this.setTitle("选择难度");
        this.setResizable(true);
        this.setSize(535, 1000);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        Image image = Toolkit.getDefaultToolkit().createImage("logo.jpg");
        Icon logo = new ImageIcon(image);
        jLabel.setIcon(logo);
    }

    void initInitialFrame() {                               ///（将窗口初始化的方法）
        this.setLayout(new GridLayout(4, 1, 6, 6));
        this.add(jLabel);
        this.add(level1);
        this.add(level2);
        this.add(level3);
        MusicAudioClip mac = new MusicAudioClip();
        try {
            mac.setAudioClip(Applet.newAudioClip((new java.io.File("InitialFrameBGM.wav")).toURL()));//填写你自己的文件路径
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        mac.loop();//循环播放

        level1.addActionListener(e -> {
            SingleGridComponent.setGridSize(90);
            SingleMainFrame mainFrame = new SingleMainFrame(9, 9, 10);
            mainFrame.setVisible(true);
            this.setVisible(false);
            mac.stop();
        });

        level2.addActionListener(e -> {
            SingleMainFrame mainFrame = new SingleMainFrame(16, 16, 40);
            mainFrame.setVisible(true);
            this.setVisible(false);
            mac.stop();

        });

        level3.addActionListener(e -> {
            SingleMainFrame mainFrame = new SingleMainFrame(16, 30, 99);
            mainFrame.setVisible(true);
            this.setVisible(false);
            mac.stop();
        });


    }
}
