import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel {
    private Doodle doodle;
    private List<Platform> platforms;
    private boolean arePlatformsAdded;
    private final int MIN_PLATFORM_LENGTH = 80;
    private final int MAX_PLATFORM_LENGTH = 100;
    private final int PLATFORM_HEIGHT = 20;
    public static final int DISTANCE_BETWEEN_LEVELS = 1000;
    private final int SCREEN_WIDTH;
    private final int SCREEN_HEIGHT;
    private final int FPS;
    private Thread gameThread;
    private DoodleKeyListener keyListener;
    private WindowKeyListener windowKeyListener;
    private final Image BACKGROUND;
    private int level;
    private boolean isPaused;
    private Window window;
    public GamePanel(int x, int y, int width, int height, int FPS, Image background, WindowKeyListener windowKeyListener, Window window){
        this.level = 1;
        this.BACKGROUND = background;
        SCREEN_WIDTH = width;
        SCREEN_HEIGHT = height;
        this.FPS = FPS;
        this.window = window;
        this.arePlatformsAdded = false;
        this.isPaused = false;

        setBounds(x,y,SCREEN_WIDTH,SCREEN_HEIGHT);
        setLayout(null);
        setFocusable(false);

        platforms = new ArrayList<>();
        Platform.generatePlatforms(MIN_PLATFORM_LENGTH,MAX_PLATFORM_LENGTH,PLATFORM_HEIGHT, 0, SCREEN_HEIGHT,SCREEN_WIDTH, (ArrayList<Platform>) platforms,level);

        doodle = new Doodle(SCREEN_HEIGHT,SCREEN_WIDTH/2,2*SCREEN_HEIGHT/3);

        Platform starter = new Platform(MAX_PLATFORM_LENGTH,PLATFORM_HEIGHT,SCREEN_WIDTH/2 - MAX_PLATFORM_LENGTH/2,2*SCREEN_HEIGHT/3 + doodle.getHEIGHT());
        platforms.add(starter);

        keyListener = new DoodleKeyListener(doodle, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT);
        addKeyListener(keyListener);
        this.windowKeyListener = windowKeyListener;
        addKeyListener(windowKeyListener);

        setVisible(false);
    }
    private void adjustScreen(){
        for (int i = 1; i <= FPS; i++){
            int yDifference = (int) ((Math.pow(doodle.getMAX_SPEED(),2)/ (2 * doodle.getCurrentGravity()) + doodle.getHEIGHT())/FPS);

            if (i % 30 == 0){
                arePlatformsAdded = true;
                Platform.generatePlatforms(MIN_PLATFORM_LENGTH,MAX_PLATFORM_LENGTH,PLATFORM_HEIGHT, -yDifference,0,SCREEN_WIDTH, (ArrayList<Platform>) platforms, level);
                arePlatformsAdded = false;
            }
            doodle.setY(doodle.getY() + yDifference);

            for (Platform platform: platforms){
                platform.lower(yDifference);
            }
            try {
                Thread.sleep(500/FPS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void startGameThread(){
        requestFocus();
        gameThread = new Thread(() -> {
            while (!isPaused) {
                double deltaSeconds = (double) FPS / 1000;
                doodle.checkCollision(deltaSeconds,platforms);
                new Thread(()->doodle.moveHorizontal(SCREEN_WIDTH)).start();
                doodle.moveVertically(deltaSeconds,this);
                if (doodle.getSpeed() <= doodle.getMAX_SPEED() + (double) (doodle.getCurrentGravity() * FPS) / 1000 && doodle.getY() <= SCREEN_HEIGHT/2 && doodle.getMaxHeight() > (double) SCREEN_HEIGHT / 2 + doodle.getMAX_SPEED())
                    new Thread(this::adjustScreen).start();
                if (doodle.hasLost(SCREEN_HEIGHT)){
                    window.endGame((int)doodle.getMaxHeight());
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
    }
    public void pause(){
        isPaused = true;
    }
    public void resume(){
        isPaused = false;
        startGameThread();
    }
    public void increaseLevel(){
        level++;
    }
    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        graphics.drawImage(BACKGROUND,0,0,SCREEN_WIDTH,SCREEN_HEIGHT,null);
        doodle.paint(graphics);
        if (arePlatformsAdded)
            return;
        try {
            for (Platform platform: platforms){
                platform.paint(graphics);
            }
        } catch (Exception ignored){}
    }
}
