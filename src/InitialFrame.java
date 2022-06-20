import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;

public class InitialFrame extends JFrame {
    ///（用于开始游戏前的窗口的方法）
    JButton level = new JButton("选择难度");
    JButton btn1 = new JButton("自定义");
    JLabel jLabel = new JLabel(" 扫雷 ", SwingConstants.CENTER);
    JButton Read = new JButton("读取存档");


    InitialFrame() {
        initInitialFrame();
        this.setTitle("扫雷");
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
        this.setLayout(new GridLayout(3, 1, 6, 6));

        this.add(jLabel);
        this.add(level);
        this.add(btn1);
//        this.add(Read);
        MusicAudioClip mac = new MusicAudioClip();
        try {
            mac.setAudioClip(Applet.newAudioClip((new java.io.File("InitialFrameBGM.wav")).toURL()));//填写你自己的文件路径
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        mac.loop();//循环播放


        btn1.addActionListener(e -> {
            CustomizeFrame customizeFrame = new CustomizeFrame();
            this.dispose();
            mac.stop();
        });

        level.addActionListener(e -> {
            LevelChoose levelChoose = new LevelChoose();
            this.dispose();
            mac.stop();
        });


//       Read.addActionListener(e -> {
//            try {
//
//
//                GameController controller1 = new GameController();
//
//                String fileName = JOptionPane.showInputDialog(this, "input here");
//                controller1.readxym(fileName);
//                if (GameController.x*GameController.y<= 81){
//                    GridComponent.setGridSize(90);
//                }
//                MainFrame mainFrame = new MainFrame(GameController.x,GameController.y,GameController.minenum);
//
//                MainFrame.controller.readFileData(fileName+".txt");
//                MainFrame.controller.getScoreBoard().update();
//                repaint();
//                MainFrame.controller.getGamePanel().repaint();
//                mainFrame.setVisible(true);
//                this.dispose();
//            } catch (IOException ioException) {
//                ioException.printStackTrace();
//            }
//        });

    }


}
