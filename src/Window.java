import javax.swing.*;
import java.awt.*;
public class Window extends JFrame {
    public static final int WIDTH = 500;
    public static final int HEIGHT = 800;
    private final GamePanel gamePanel;
    private final MenuPanel menuPanel;
    private final InstructionsPanel instructionsPanel;
    private final Image BACKGROUND;
    public Window () {
        BACKGROUND = new ImageIcon("src\\gameImages\\basicGame\\bg-grid.png").getImage();

        menuPanel = new MenuPanel(BACKGROUND,WIDTH,HEIGHT,this);
        this.add(menuPanel);
        menuPanel.repaint();

        instructionsPanel = new InstructionsPanel(WIDTH,HEIGHT,BACKGROUND,this);
        this.add(instructionsPanel);

        WindowKeyListener windowKeyListener = new WindowKeyListener(this);
        this.addKeyListener(windowKeyListener);

        int FPS = 60;
        gamePanel = new GamePanel(0, 0, WIDTH, HEIGHT, FPS,BACKGROUND,windowKeyListener,this);
        this.add(gamePanel);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        this.setSize(WIDTH, HEIGHT);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setFocusable(true);
    }
    public void switchToGame(){
        menuPanel.setFocusable(!menuPanel.isFocusable());
        menuPanel.setVisible(!menuPanel.isVisible());
        gamePanel.setFocusable(!gamePanel.isFocusable());
        gamePanel.setVisible(!gamePanel.isVisible());
        gamePanel.resume();
    }
    public void switchBetweenMenuAndGame(){
        gamePanel.setFocusable(!gamePanel.isFocusable());
        gamePanel.setVisible(!gamePanel.isVisible());
        if (gamePanel.isVisible()){
            gamePanel.resume();
        }
        else {
            gamePanel.pause();
        }
        menuPanel.setFocusable(!menuPanel.isFocusable());
        menuPanel.setVisible(!menuPanel.isVisible());
    }
    public void switchBetweenMenuAndInstructions(){
        menuPanel.setFocusable(!menuPanel.isFocusable());
        menuPanel.setVisible(!menuPanel.isVisible());
        instructionsPanel.setFocusable(!instructionsPanel.isFocusable());
        instructionsPanel.setVisible(!instructionsPanel.isVisible());
        if (instructionsPanel.isVisible()){
            instructionsPanel.repaint();
        }
        else {
            menuPanel.repaint();
        }
    }
    public void endGame(int finalScore){
        EndPanel endPanel = new EndPanel(WIDTH, HEIGHT, BACKGROUND, this, finalScore);
        this.add(endPanel);
        gamePanel.setVisible(false);
        endPanel.setVisible(true);
    }
    public void newGame(){
        new Window();
        this.dispose();
    }
}