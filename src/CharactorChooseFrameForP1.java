import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.net.MalformedURLException;

/////可能需要添加角色选择的音乐
public class CharactorChooseFrameForP1 extends JFrame {

    ImageIcon imageIcon1 = new ImageIcon("Charactor1.png");
    ImageIcon imageIcon2 = new ImageIcon("Charactor2.png");
    ImageIcon imageIcon3 = new ImageIcon("Charactor3.png");
    ImageIcon imageIcon4 = new ImageIcon("Charactor4.png");


    JButton random = new JButton(imageIcon3);
    JButton KillerQueen = new JButton(imageIcon2);
    JButton TheWorld = new JButton(imageIcon1);
    JButton KingCrisom = new JButton(imageIcon4);


    public CharactorChooseFrameForP1() {
        init();
        this.setLayout(new GridLayout(2, 2, 0, 0));
        this.setTitle("请P1选择角色");
        this.setSize(360, 350);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);  //Resizable:可调整大小的
        this.setLocationRelativeTo(null);
    }

    public void init() {
        this.add(random);
        this.add(KillerQueen);
        this.add(TheWorld);
        this.add(KingCrisom);

        random.addActionListener(e -> {
            MainFrame.P1 = Charactor.Rondom;
            MusicAudioClip mac = new MusicAudioClip();
            try {
                mac.setAudioClip(Applet.newAudioClip((new java.io.File("Random.wav")).toURL()));//填写你自己的文件路径
            } catch (MalformedURLException E) {
                E.printStackTrace();
            }
            mac.play();//循环播放
            this.dispose();
           MainFrame.P1Name = JOptionPane.showInputDialog(null,"请输入：\n","请P1输入自己的姓名",JOptionPane.PLAIN_MESSAGE);
            CharactorChooseFrameForP2 charactorChooseFrameForP2 = new CharactorChooseFrameForP2();
        });

        KillerQueen.addActionListener(e -> {
            MainFrame.P1  = Charactor.KillerQueen;
            MusicAudioClip mac = new MusicAudioClip();
            try {
                mac.setAudioClip(Applet.newAudioClip((new java.io.File("CharactorChooseKQ.wav")).toURL()));//填写你自己的文件路径
            } catch (MalformedURLException E) {
                E.printStackTrace();
            }
            mac.play();//循环播放
            this.dispose();
            MainFrame.P1Name = JOptionPane.showInputDialog(null,"请输入：\n","请P1输入自己的姓名",JOptionPane.PLAIN_MESSAGE);
            CharactorChooseFrameForP2 charactorChooseFrameForP2 = new CharactorChooseFrameForP2();
        });

        TheWorld.addActionListener(e -> {
            MainFrame.P1 = Charactor.TheWorld;
            MusicAudioClip mac = new MusicAudioClip();
            try {
                mac.setAudioClip(Applet.newAudioClip((new java.io.File("CharactorChooseTW.wav")).toURL()));//填写你自己的文件路径
            } catch (MalformedURLException E) {
                E.printStackTrace();
            }
            mac.play();//循环播放
            this.dispose();
            MainFrame.P1Name = JOptionPane.showInputDialog(null,"请输入：\n","请P1输入自己的姓名",JOptionPane.PLAIN_MESSAGE);
            CharactorChooseFrameForP2 charactorChooseFrameForP2 = new CharactorChooseFrameForP2();
        });

        KingCrisom.addActionListener(e -> {
            MainFrame.P1  = Charactor.KingCrisom;
            MusicAudioClip mac = new MusicAudioClip();
            try {
                mac.setAudioClip(Applet.newAudioClip((new java.io.File("CharactorChooseCK.wav")).toURL()));//填写你自己的文件路径
            } catch (MalformedURLException E) {
                E.printStackTrace();
            }
            mac.play();//循环播放
            this.dispose();
            MainFrame.P1Name = JOptionPane.showInputDialog(null,"请输入：\n","请P1输入自己的姓名",JOptionPane.PLAIN_MESSAGE);
            CharactorChooseFrameForP2 charactorChooseFrameForP2 = new CharactorChooseFrameForP2();
        });
    }

    public static void main(String[] args) {
        CharactorChooseFrameForP1 charactorChooseFrameForP1 = new CharactorChooseFrameForP1();
    }
}



