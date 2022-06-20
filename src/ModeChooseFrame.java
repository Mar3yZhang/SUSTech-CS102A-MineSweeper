import javax.swing.*;
import java.applet.Applet;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class ModeChooseFrame extends JFrame {
    JPanel imagePanel;
    ImageIcon Frame = new ImageIcon("Frame.png");
    JLabel BackGroud = new JLabel(Frame);
    ImageIcon Button1 = new ImageIcon("Button1.png");
    ImageIcon Button2 = new ImageIcon("Button2.png");
    ImageIcon Button3 = new ImageIcon("Button3.png");
    ImageIcon Button4 = new ImageIcon("Button4.png");
    ImageIcon Button5 = new ImageIcon("Button5.png");
    ImageIcon text = new ImageIcon("TEXT.png");

    //    ImageIcon Button3 = new ImageIcon()
    JButton Single = new JButton("单人模式", Button1);
    JButton Double = new JButton("双人模式",Button2);
    JButton Read = new JButton("读取存档",Button3);
    JButton VsComupter = new JButton("人机对战",Button5);//背景图片没加
    JButton Skin = new JButton("选择棋盘皮肤",Button4);
    JButton Text = new JButton("游戏说明",text);


    public static Player comeputer = new Player();


    public void setComputer() {
        comeputer.setProp(Prop.No);
        comeputer.setCharactor(Charactor.No);
        comeputer.setUserName("Computer");
    }


    ModeChooseFrame() {
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
        imagePanel.add(Single);
        imagePanel.add(Double);
        imagePanel.add(Read);
        imagePanel.add(VsComupter);
        imagePanel.add(Text);
        imagePanel.add(Skin);


        this.getLayeredPane().setLayout(null);
        this.getLayeredPane().add(BackGroud, new Integer(Integer.MIN_VALUE));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(Frame.getIconWidth() + 18, Frame.getIconHeight() + 45);

        Single.setLocation(500, 200);
        Double.setLocation(500, 100);
        Read.setLocation(500, 300);
        VsComupter.setLocation(500, 400);
        Skin.setLocation(500, 500);
        Text.setLocation(10, 15);
        Single.setSize(170, 50);
        Double.setSize(170, 50);
        Read.setSize(170, 50);
        VsComupter.setSize(170, 50);
        Skin.setSize(170, 50);
        Text.setSize(50, 50);


        MusicAudioClip mac = new MusicAudioClip();
        try {
            mac.setAudioClip(Applet.newAudioClip((new File("InitialFrameBGM.wav")).toURL()));//填写你自己的文件路径
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        mac.loop();//循环播放


        Single.addActionListener(e -> {
            SingleLevelChoose SinglelevelChoose = new SingleLevelChoose();
            this.dispose();
            mac.stop();
        });

        Double.addActionListener(e -> {
            CharactorChooseFrameForP1 charactorChooseFrameForP1 = new CharactorChooseFrameForP1();
            this.dispose();
            mac.stop();
        });

        Read.addActionListener(e -> {
            try {
                GameController controller1 = new GameController();

                String fileName = JOptionPane.showInputDialog(this, "input here");
                controller1.readxymAndCharacter(fileName);

                if (GameController.x * GameController.y <= 81) {
                    GridComponent.setGridSize(90);
                }
                MainFrame mainFrame = new MainFrame(GameController.x, GameController.y, GameController.minenum);

                MainFrame.controller.readFileData(fileName + ".txt");
                MainFrame.controller.getScoreBoard().update();
                repaint();
                MainFrame.controller.getGamePanel().repaint();
                mainFrame.setVisible(true);
                this.dispose();
                mac.stop();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });


        VsComupter.addActionListener(e -> {
            ComputerLevelChoose computerLevelChoose = new ComputerLevelChoose();
            setComputer();
            MainFrame.P1 = Charactor.No;
            CharactorChooseFrameForP2 charactorChooseFrameForP2 = new CharactorChooseFrameForP2();
            this.dispose();
            mac.stop();

        });

        Skin.addActionListener(e -> {
            this.dispose();
            SkinChooseFrame skinChooseFrame = new SkinChooseFrame();
            mac.stop();
        });


        Text.addActionListener(e -> {
            TextFrame textFrame = new TextFrame();
            this.dispose();
            mac.stop();
        });

    }

}




