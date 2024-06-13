import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GamePanel extends JPanel {
    private final Doodle doodle;
    private List<Platform> platforms;
    private final int MIN_PLATFORM_LENGTH = 20;
    private final int MAX_PLATFORM_LENGTH = 40;
    private final int PLATFORM_HEIGHT = 20;
    private final int SCREEN_WIDTH;
    private final int SCREEN_HEIGHT;
    private final int FPS;
    private Thread gameThread;
    private DoodleKeyListener keyListener;
    private int highestPlatformY;
    private boolean firstAdjustment;
    public GamePanel(int x, int y, int width, int height, int FPS){
        SCREEN_WIDTH = width;
        SCREEN_HEIGHT = height;
        this.FPS = FPS;
        setBounds(x,y,SCREEN_WIDTH,SCREEN_HEIGHT);
        setLayout(null);
        setFocusable(false);
        doodle = new Doodle(SCREEN_WIDTH,SCREEN_HEIGHT);
        platforms = new ArrayList<>();
        platforms = Platform.generatePlatforms(MIN_PLATFORM_LENGTH,MAX_PLATFORM_LENGTH,PLATFORM_HEIGHT, 0, SCREEN_HEIGHT,SCREEN_WIDTH, (ArrayList<Platform>) platforms);
        keyListener = new DoodleKeyListener(doodle, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, SCREEN_WIDTH);
        addKeyListener(keyListener);
        setVisible(false);
        highestPlatformY = SCREEN_HEIGHT;
        firstAdjustment = true;
    }
    private void adjustScreen(){
        int frames = 30;
        for (int i = 1; i <= frames; i++){
            int yDifference = (int) ((Math.pow(doodle.getMAX_SPEED(),2)/ (3 * doodle.getCurrentGravity()) + doodle.getHEIGHT())/frames);
            doodle.setY(doodle.getY() + yDifference);
            Random random = new Random();
            for (Platform platform: platforms){
                platform.lower(yDifference);
            }
            highestPlatformY += yDifference;
            if (i == frames && !firstAdjustment){
                platforms = Platform.generatePlatforms(MIN_PLATFORM_LENGTH,MAX_PLATFORM_LENGTH,PLATFORM_HEIGHT, 0,highestPlatformY,SCREEN_WIDTH, (ArrayList<Platform>) platforms);
                highestPlatformY = 0;
            }
            firstAdjustment = false;
            try {
                Thread.sleep(250/frames);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void startGameThread(){
        requestFocus();
        gameThread = new Thread(() -> {
            while (true) {
                double deltaSeconds = (double) FPS / 1000;
                doodle.checkCollision(deltaSeconds,platforms);
                new Thread(()->doodle.moveHorizontal(SCREEN_WIDTH)).start();
                doodle.moveVertically(deltaSeconds);
                if (doodle.getSpeed() <= doodle.getMAX_SPEED() + (double) (doodle.getCurrentGravity() * FPS) / 1000 && doodle.getY() <= SCREEN_HEIGHT/2 && doodle.getMaxHeight() > (double) SCREEN_HEIGHT / 2 + doodle.getMAX_SPEED())
                    new Thread(this::adjustScreen).start();
                if (doodle.hasLost(SCREEN_HEIGHT)){
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
    public int getSCREEN_WIDTH(){
        return SCREEN_WIDTH;
    }
    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        doodle.paint(graphics);
        for (Platform platform: platforms){
            platform.paint(graphics);
        }
    }
}
