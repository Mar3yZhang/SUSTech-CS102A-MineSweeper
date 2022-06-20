import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class BasicComponent extends JComponent {
    public BasicComponent() {
        initial();
    }

    private void initial(){
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(e.getButton()==1){
                    onMouseLeftClicked();
                }
                if(e.getButton()==3){
                   onMouseRightClicked();
                }

            }
        });
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                OnMouseEntered();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                OnMouseExited();
            }
        });
    }

    /**
     * 当左键点击时执行这个方法
     */
    public abstract void onMouseLeftClicked();

    /**
     * 当右键点击时执行这个方法
     */
    public abstract void onMouseRightClicked();

    public abstract void OnMouseEntered();

    public abstract void OnMouseExited();
}
