import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class GamePanel extends JPanel {
    private final Doodle doodle;
    private List<Platform> platforms;
    private final int MIN_PLATFORM_LENGTH = 3;
    private final int MAX_PLATFORM_LENGTH = 10;
    private final int PLATFORM_HEIGHT = 2;
    private final int SCREEN_WIDTH;
    private final int SCREEN_HEIGHT;
    private final int FPS;
    public GamePanel(int x, int y, int width, int height, int FPS){
        super();
        SCREEN_WIDTH = width;
        SCREEN_HEIGHT = height;
        this.FPS = FPS;
        setBounds(x,y,SCREEN_WIDTH,SCREEN_HEIGHT);
        setLayout(null);
        setFocusable(false);
        doodle = new Doodle();
        platforms = new ArrayList<>();
        platforms = Platform.generatePlatforms(SCREEN_HEIGHT*SCREEN_WIDTH/PLATFORM_HEIGHT/MAX_PLATFORM_LENGTH/2,
                MIN_PLATFORM_LENGTH,MAX_PLATFORM_LENGTH,PLATFORM_HEIGHT,
                0, SCREEN_HEIGHT,SCREEN_WIDTH);
        KeyListener keyListener = new KeyListener(doodle, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, SCREEN_WIDTH);
        addKeyListener(keyListener);
        setVisible(false);
    }
    private void adjustScreen(){
        int yDifference = doodle.getHEIGHT() - doodle.getY();
        if (yDifference <= 0)
            return;
        doodle.setY(doodle.getHEIGHT());
        Random random = new Random();
        for (Platform platform: platforms){
            platform.lower(yDifference);
        }
        LinkedList<Platform> newPlatforms = (LinkedList<Platform>) Platform.generatePlatforms(random.nextInt(1,yDifference),
                MIN_PLATFORM_LENGTH,MAX_PLATFORM_LENGTH,PLATFORM_HEIGHT,
                0,yDifference,SCREEN_WIDTH);
        platforms.addAll(newPlatforms);
    }
    public void runGame(){
        new Thread(() -> {
            while (true) {
                double deltaSeconds = (double) 1000 /FPS;
                doodle.checkCollision(deltaSeconds,platforms);
                doodle.moveVertically(deltaSeconds);
                new Thread(this::adjustScreen).start();
                if (doodle.hasLost()){
                    //Oh, no you lost
                }
                repaint();
                try {
                    Thread.sleep(1000/FPS);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        doodle.paint(graphics);
        for (Platform platform: platforms){
            platform.paint(graphics);
        }
    }
}
