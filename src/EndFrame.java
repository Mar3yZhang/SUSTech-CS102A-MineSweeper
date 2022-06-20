import javax.swing.*;
import java.awt.*;

public class EndFrame extends JFrame {
    String end;
    JLabel End = new JLabel();
    JButton Exit = new JButton("好的");

    public EndFrame() {
        this.setTitle("结算面板");
        this.setResizable(true);
        this.setSize(300, 300);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        initFrame();
        Exit.setSize(50,25);
    }

    public void initFrame() {
        this.setLayout(new GridLayout(2, 1, 6, 6));
        Font f = new Font("alias", Font.PLAIN, 25);
        this.setFont(f);
        if (MainFrame.controller.getWinner() == MainFrame.controller.getNone() ) {
            end = "本局为平局";
            End.setText(end);
        } else {
            end = MainFrame.controller.getWinner().getUserName();
            String stringBuilder = "本次获胜者为： \n" +
                    end;
            End.setText(stringBuilder);
        }
        this.add(End);
        this.add(Exit);

        Exit.addActionListener(e -> {
            this.dispose();
        });
    }
}
