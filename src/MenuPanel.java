import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {
    private final int SCREEN_WIDTH;
    private final int SCREEN_HEIGHT;

    public MenuPanel(int x, int y, int width, int height, Window window) {
        super();
        SCREEN_WIDTH = width;
        SCREEN_HEIGHT = height;
        setBounds(x, y, SCREEN_WIDTH, SCREEN_HEIGHT);
        setLayout(null);
        setFocusable(true);
        setVisible(true);
        JButton start = new JButton();
        start.setText("START GAME");
        start.setBounds(width/10 + x,height * 6/10 + y, width/10,height/10);
        start.addActionListener((event)->{
            this.remove(start);
            window.switchPanels();
        });
        JButton instructions = new JButton();
        instructions.setText("INSTRUCTIONS");
        instructions.setBounds(width * 4/5, height * 6/10, width/10, height/10);
        instructions.addActionListener((event)->{
            showInstructions();
        });
        this.add(start);
        this.add(instructions);
    }
    public void showInstructions(){
    }
}