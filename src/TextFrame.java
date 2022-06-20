import javax.swing.*;
import java.applet.Applet;
import java.net.MalformedURLException;

public class TextFrame extends JFrame {
    JPanel imagePanel;
    ImageIcon Frame = new ImageIcon("Background2.png");
    JLabel BackGroud = new JLabel(Frame);
    JButton exit = new JButton("退出");

    TextFrame() {
        initInitialFrame();
        this.setTitle("扫雷");
        this.setResizable(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

    }

    void initInitialFrame() {                               ///（将窗口初始化的方法）
        this.setLayout(null);
        this.setLayout(null);
        this.add(BackGroud);
        BackGroud.setBounds(0, 0, Frame.getIconWidth(), Frame.getIconHeight());
        imagePanel = (JPanel) this.getContentPane();
        imagePanel.setOpaque(false);
        imagePanel.setLayout(null);
        imagePanel.add(exit);

        exit.setSize(100,30);
        exit.setLocation(Frame.getIconWidth()/2 - 50,Frame.getIconHeight()-40);


        this.getLayeredPane().setLayout(null);
        this.getLayeredPane().add(BackGroud, new Integer(Integer.MIN_VALUE));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(Frame.getIconWidth() + 18, Frame.getIconHeight() + 45);

        MusicAudioClip mac = new MusicAudioClip();
        try {
            mac.setAudioClip(Applet.newAudioClip((new java.io.File("Killer.wav")).toURL()));//填写你自己的文件路径
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        mac.loop();//循环播放


        exit.addActionListener(e -> {
            this.dispose();
            ModeChooseFrame modeChooseFrame = new ModeChooseFrame();
            mac.stop();
        });
    }
}
