import javax.swing.*;
import java.awt.*;
public class Window extends JFrame {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 1200;
    private int FPS = 60;
    private GamePanel gamePanel;
    private MenuPanel menuPanel;

    public Window () {
        gamePanel = new GamePanel(0, 0, WIDTH, HEIGHT,FPS);
        this.add(gamePanel);
        menuPanel = new MenuPanel(0,0,WIDTH,HEIGHT);
        this.add(menuPanel);
        ImageIcon imageIcon = new ImageIcon("Background/ggg.jpg");
        Image scaledImage = imageIcon.getImage().getScaledInstance(WIDTH,HEIGHT,100);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        /*JLabel label = new JLabel();
        label.setIcon(scaledIcon);
        gamePanel.add(label);*/

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        this.setSize(WIDTH, HEIGHT);
        this.setVisible(true);
    }
    public void switchPanels(){
        gamePanel.setFocusable(!gamePanel.isFocusable());
        gamePanel.setVisible(!gamePanel.isVisible());
        menuPanel.setFocusable(!gamePanel.isFocusable());
        menuPanel.setVisible(!gamePanel.isVisible());
    }
}