import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel {
    private final Doodle doodle;
    private final List<Platform> platforms;
    private boolean arePlatformsAdded;
    private final int MIN_PLATFORM_LENGTH = 80;
    private final int MAX_PLATFORM_LENGTH = 100;
    private final int PLATFORM_HEIGHT = 20;
    public static final int DISTANCE_BETWEEN_LEVELS = 1500;
    private final int SCREEN_WIDTH;
    private final int SCREEN_HEIGHT;
    private final int FPS;
    private final Image background;
    private int level;
    private boolean isPaused;
    private final Window window;
    private final JLabel displayScore;
    public GamePanel(int x, int y, int width, int height, int FPS, Image background, WindowKeyListener windowKeyListener, Window window){
        this.level = 1;
        this.background = background;
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

        doodle = new Doodle(SCREEN_WIDTH/2,2*SCREEN_HEIGHT/3);

        Platform starter = new Platform(MAX_PLATFORM_LENGTH,PLATFORM_HEIGHT,SCREEN_WIDTH/2 - MAX_PLATFORM_LENGTH/2,2*SCREEN_HEIGHT/3 + doodle.getHEIGHT(), new ImageIcon("gameImages\\basicGame\\p-green.png").getImage());
        platforms.add(starter);

        DoodleKeyListener keyListener = new DoodleKeyListener(doodle, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT);
        addKeyListener(keyListener);
        addKeyListener(windowKeyListener);

        displayScore = new JLabel();
        displayScore.setBounds(0,0,300,40);
        Font customFont;
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/al_seana/al-seana.ttf")).deriveFont(30f);
            displayScore.setFont(customFont);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
        this.add(displayScore);

        setVisible(false);
    }
    private synchronized void adjustScreen(){
        for (int i = 1; i <= FPS; i++){
            if (doodle.getSpeed() > 0)
                break;
            int yDifference = (int) ((Math.pow(doodle.getMAX_SPEED(),2)/ (3 * doodle.getGRAVITY()) + doodle.getHEIGHT())/FPS);

            if (i % 30 == 0){
                arePlatformsAdded = true;
                Platform.generatePlatforms(MIN_PLATFORM_LENGTH,MAX_PLATFORM_LENGTH,PLATFORM_HEIGHT, -yDifference,0,SCREEN_WIDTH, (ArrayList<Platform>) platforms, level);
                arePlatformsAdded = false;
            }
            doodle.setY(doodle.getY() + yDifference);
            List<Platform> platformsToRemove = new ArrayList<>();
            for (Platform platform: platforms){
                platform.lower(yDifference);

                if (platform.getY() + platform.getHeight() >= SCREEN_HEIGHT){
                    platformsToRemove.add(platform);
                }
            }
            platforms.removeAll(platformsToRemove);

            try {
                Thread.sleep(450/FPS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void startGameThread(){
        requestFocus();
        Thread gameThread = new Thread(() -> {
            while (!isPaused) {
                double deltaSeconds = (double) FPS / 1000;

                switch (doodle.checkCollisionWithPlatforms(platforms)) {
                    case 1 -> {
                        doodle.jump();
                    }
                    case 2 -> {
                        doodle.superJump();
                    }
                }

                new Thread(() -> {
                    try {
                        for (Platform platform : platforms) {
                            if (platform instanceof MovingPlatform) {
                                ((MovingPlatform) platform).move(deltaSeconds);
                            } else if (platform instanceof FallingPlatform && ((FallingPlatform) platform).checkCollision(doodle)) {
                                platform.lower(SCREEN_HEIGHT);
                            }
                        }
                    } catch (Exception ignored) {
                    }
                }).start();

                new Thread(() -> doodle.moveHorizontal(SCREEN_WIDTH)).start();
                doodle.moveVertically(deltaSeconds, this);

                if (doodle.getSpeed() <= doodle.getMAX_SPEED() + (double) (doodle.getGRAVITY() * FPS) / 1000 && doodle.getY() <= SCREEN_HEIGHT / 2) {
                    new Thread(this::adjustScreen).start();
                }

                if (doodle.hasLost(SCREEN_HEIGHT)) {
                    window.endGame((int) doodle.getMaxHeight());
                    break;
                }

                repaint();
                displayScore.setText("Height Reached: " + (int) doodle.getMaxHeight());
                try {
                    Thread.sleep(1000 / FPS);
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
        graphics.drawImage(background,0,0,SCREEN_WIDTH,SCREEN_HEIGHT,null);
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
