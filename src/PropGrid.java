import javax.imageio.ImageIO;
import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class PropGrid extends BasicComponent {
    public static int gridSize = 140;
    private Prop gridStatus;

    public static int getGridSize() {
        return gridSize;
    }

    public Prop getGridStatus() {
        return gridStatus;
    }

    public static void setGridSize(int gridSize) {
        PropGrid.gridSize = gridSize;
    }

    public void setGridStatus(Prop gridStatus) {
        this.gridStatus = gridStatus;
    }

    public PropGrid() {
        setSize(gridSize, gridSize);
        this.gridStatus = Prop.No;
    }

    public void draw(Graphics g) {

        if (this.gridStatus == Prop.KillerQueen) {

            try {
                Image img1 = ImageIO.read(new File("killerQueen.png"));
                g.drawImage(img1, 0, 0, getWidth() - 1, getHeight() - 1, null);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        if (this.gridStatus == Prop.KingCrisom) {    ///(当格子状态为被点击时，添加雷与数量)

            try {
                Image img3 = ImageIO.read(new File("kingCrisom.png"));
                g.drawImage(img3, 0, 0, getWidth() - 1, getHeight() - 1, null);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        if (this.gridStatus == Prop.No) {    ///(当格子状态为被点击时，添加雷与数量)

            try {
                Image img3 = ImageIO.read(new File("noProp.png"));
                g.drawImage(img3, 0, 0, getWidth() - 1, getHeight() - 1, null);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        if (this.gridStatus == Prop.TheWorld) {  ///(当格子状态为旗帜时，添加旗帜图标)
            try {
                Image img4 = ImageIO.read(new File("theWorld.png"));
                g.drawImage(img4, 0, 0, getWidth() - 1, getHeight() - 1, null);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.printComponents(g);
        draw(g);
    }

    @Override
    public void onMouseLeftClicked() {
        if (this.gridStatus == Prop.TheWorld) {
            MainFrame.controller.theWorld();
            JOptionPane.showMessageDialog(null, "当前时间已经停止", "提示",JOptionPane.PLAIN_MESSAGE);
            MusicAudioClip mac = new MusicAudioClip();
            try {
                mac.setAudioClip(Applet.newAudioClip((new java.io.File("DIO.wav")).toURL()));//填写你自己的文件路径
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            mac.play();
        }
        if (this.gridStatus == Prop.KillerQueen) {
            try {
                MainFrame.controller.killerQueen();
                JOptionPane.showMessageDialog(null, "已经回到上一回合", "提示",JOptionPane.PLAIN_MESSAGE);
                MusicAudioClip mac = new MusicAudioClip();
                try {
                    mac.setAudioClip(Applet.newAudioClip((new java.io.File("KQ.wav")).toURL()));//填写你自己的文件路径
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                mac.play();
                MainFrame.controller.nextTurn();
            } catch (Exception E) {
                JOptionPane.showMessageDialog(null, "您无法在初始回合使用杀手皇后\n本次道具将消失", "操作错误", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (this.gridStatus == Prop.KingCrisom) {
            MainFrame.controller.kingCrisom();
            JOptionPane.showMessageDialog(null, "本回合被削去10s", "提示",JOptionPane.PLAIN_MESSAGE);
            MusicAudioClip mac = new MusicAudioClip();
            try {
                mac.setAudioClip(Applet.newAudioClip((new java.io.File("KC.wav")).toURL()));//填写你自己的文件路径
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            mac.play();
        }
        repaint();
        this.gridStatus = Prop.No;

    }

    @Override
    public void onMouseRightClicked() {
        this.gridStatus = Prop.No;
        repaint();
    }

    @Override
    public void OnMouseEntered() {

    }

    @Override
    public void OnMouseExited() {

    }
}
