import javax.swing.*;
import java.awt.*;

public class InstructionsPanel extends JPanel {
    public InstructionsPanel(MenuPanel menuPanel, int ScreenWidth, int ScreenHeight){
        JLabel head = new JLabel();
        head.setBounds(0, 10, ScreenWidth, 20);
        head.setText("INSTRUCTIONS");
    }
}
