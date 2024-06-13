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
    private final int MIN_PLATFORM_LENGTH = 10;
    private final int MAX_PLATFORM_LENGTH = 20;
    private final int PLATFORM_HEIGHT = 20;
    private final int SCREEN_WIDTH;
    private final int SCREEN_HEIGHT;
    private final int FPS;
    private Thread gameThread;
    public GamePanel(int x, int y, int width, int height, int FPS){
        SCREEN_WIDTH = width;
        SCREEN_HEIGHT = height;
        this.FPS = FPS;
        setBounds(x,y,SCREEN_WIDTH,SCREEN_HEIGHT);
        setLayout(null);
        setFocusable(false);
        doodle = new Doodle();
        platforms = new ArrayList<>();
        platforms = Platform.generatePlatforms(SCREEN_HEIGHT*SCREEN_WIDTH/PLATFORM_HEIGHT/MAX_PLATFORM_LENGTH,
                MIN_PLATFORM_LENGTH,MAX_PLATFORM_LENGTH,PLATFORM_HEIGHT,
                0, SCREEN_HEIGHT,SCREEN_WIDTH);
        DoodleKeyListener keyListener = new DoodleKeyListener(doodle, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, SCREEN_WIDTH);
        addKeyListener(keyListener);
        setVisible(false);
    }
    private void adjustScreen(){
        int yDifference = doodle.getHEIGHT() - doodle.getY();
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
    public void startGameThread(){
        gameThread = new Thread(() -> {
            while (true) {
                double deltaSeconds = (double) FPS / 1000;
                doodle.checkCollision(deltaSeconds,platforms);
                doodle.moveVertically(deltaSeconds);
                if (doodle.getY() <= 0)
                    new Thread(this::adjustScreen).start();
                if (doodle.hasLost()){
                    break;
                }
                repaint();
                try {
                    Thread.sleep(1000/FPS);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        gameThread.start();
        //ADD LOST SCREEN
    }
    public void pause(){
        try {
            gameThread.wait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void resume(){
        startGameThread();
    }
    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        doodle.paint(graphics);
        for (Platform platform: platforms){
            platform.paint(graphics);
        }
    }
}
