import javax.swing.*;
import java.awt.*;

public class Main {              /////将字体SIZE设置与格子的SIZE成关联
    public static void main(String[] args) {
        MainFrame.InitGlobalFont(new Font("alias", Font.PLAIN, 15));
        //（在加载Frame前于主方法处统一设置字体）
        SwingUtilities.invokeLater(() -> {
            ModeChooseFrame modeChooseFrame = new ModeChooseFrame();
            modeChooseFrame.setVisible(true);
        });
    }
}
