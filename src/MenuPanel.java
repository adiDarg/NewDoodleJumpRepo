import javax.swing.*;

public class MenuPanel extends JPanel {
    private final int SCREEN_WIDTH;
    private final int SCREEN_HEIGHT;

    public MenuPanel(int x, int y, int width, int height) {
        super();
        SCREEN_WIDTH = width;
        SCREEN_HEIGHT = height;
        setBounds(x, y, SCREEN_WIDTH, SCREEN_HEIGHT);
        setLayout(null);
        setFocusable(true);
        setVisible(true);
    }
}