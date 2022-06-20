import javax.swing.*;
import java.awt.*;

public class CustomizeFrame extends JFrame {
    private Container container = this.getContentPane();
    private JLabel XCount = new JLabel("行 数"); ///文字
    private JTextField RowNumber = new JTextField();       ///输入文本框
    private JLabel YCount = new JLabel("列 数");
    private JTextField ColumnNumber = new JTextField();
    private JLabel MineCount = new JLabel("雷 数");
    private JTextField MineNumber = new JTextField();
    private JButton OK = new JButton("确定");
    private JButton cancel = new JButton("取消");
    private int xCount;
    private int yCount;
    private int mineCount;

    public JFrame getjFrame() {
        return this;
    }

    public int getxCount() {
        return xCount;
    }

    public int getyCount() {
        return yCount;
    }

    public int getmineCount() {
        return mineCount;
    }

    public CustomizeFrame() {
        MainFrame.InitGlobalFont(new Font("alias", Font.PLAIN, 15));
        ///对JFrame的主要设置
        this.setSize(275, 250);
        this.setLocationRelativeTo(null);
        this.setTitle("自定义");
        container.setLayout(new BorderLayout());
        ///JFrame采用Border布局
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        InitCustomizeFrame();
        this.setVisible(true);

        OK.addActionListener(e -> {
            try {
                if (Integer.parseInt(RowNumber.getText()) > 0 && Integer.parseInt(RowNumber.getText()) < 31) {
                    xCount = Integer.parseInt(RowNumber.getText());
                } else {
                    this.dispose();
                }
            } catch (Exception mistake) {
                this.dispose();
            }

            if (Integer.parseInt(ColumnNumber.getText()) > 0 && Integer.parseInt(ColumnNumber.getText()) < 25) {
                yCount = Integer.parseInt(ColumnNumber.getText());
            } else {
                this.setVisible(false);
            }
            if (Integer.parseInt(MineNumber.getText()) <= (xCount * yCount) / 2) {
                mineCount = Integer.parseInt(MineNumber.getText());
            } else {
                this.setVisible(false);
            }


            if (xCount * yCount <= 100 && xCount * yCount > 0 && xCount > yCount) {
                GridComponent.setGridSize(900 / xCount);
            } else if (xCount * yCount > 225 && xCount <= yCount) {
                double size = (double) (1452000 / (xCount * yCount));
                GridComponent.setGridSize((int) Math.sqrt((1452000 / (xCount * yCount))));
            }


            if (xCount * yCount <= 100 && xCount * yCount > 0 && xCount <= yCount) {
                double size = (double) (656100 / (xCount * yCount));
                GridComponent.setGridSize((int) Math.sqrt((656100 / (xCount * yCount))));
            } else if (xCount * yCount > 225 && xCount <= yCount) {
                double size = (double) (1452000 / (xCount * yCount));
                GridComponent.setGridSize((int) Math.sqrt((1452000 / (xCount * yCount))));
            }
            MainFrame mainFrame = new MainFrame(xCount
                    , yCount
                    , mineCount);
            mainFrame.setVisible(true);
            this.dispose();
        });

        cancel.addActionListener(e -> {
            this.dispose();
        });

    }

    public void InitCustomizeFrame() {
        ///设置窗口的副标题（北部）
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout());
        //使得文字居中
        titlePanel.add(new JLabel(("雷数不能超过总格子数目的一半！")));
        container.add(titlePanel, "North");

        ///输入部分（中部）
        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(null);
        XCount.setBounds(30, 20, 50, 20);
        YCount.setBounds(30, 50, 50, 20);
        MineCount.setBounds(30, 80, 50, 20);
        fieldPanel.add(XCount);
        fieldPanel.add(YCount);
        fieldPanel.add(MineCount);
        RowNumber.setBounds(110, 20, 120, 20);
        ColumnNumber.setBounds(110, 50, 120, 20);
        MineNumber.setBounds(110, 80, 120, 20);
        fieldPanel.add(RowNumber);
        fieldPanel.add(ColumnNumber);
        fieldPanel.add(MineNumber);
        container.add(fieldPanel, "Center");

        ///按钮部分（南部）

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(OK);
        buttonPanel.add(cancel);
        container.add(buttonPanel, "South");

    }


}