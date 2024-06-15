import javax.swing.*;
import java.awt.*;
public class Window extends JFrame {
    public static final int WIDTH = 500;
    public static final int HEIGHT = 800;
    private int FPS = 60;
    private GamePanel gamePanel;
    private MenuPanel menuPanel;

    public Window () {
        Image background = new ImageIcon("src\\gameImages\\basicGame\\bg-grid.png").getImage();

        menuPanel = new MenuPanel(100,100,background,WIDTH,HEIGHT,this);
        this.add(menuPanel);
        menuPanel.repaint();

        gamePanel = new GamePanel(0, 0, WIDTH, HEIGHT,FPS,background);
        this.add(gamePanel);

        WindowKeyListener windowKeyListener = new WindowKeyListener(this);
        this.addKeyListener(windowKeyListener);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        this.setSize(WIDTH, HEIGHT);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    public void switchPanels(){
        menuPanel.setFocusable(!menuPanel.isFocusable());
        menuPanel.setVisible(!menuPanel.isVisible());
        gamePanel.setFocusable(!gamePanel.isFocusable());
        gamePanel.setVisible(!gamePanel.isVisible());
        if (gamePanel.isVisible()){
            gamePanel.resume();
        }
        else {
            gamePanel.pause();
        }
    }
}