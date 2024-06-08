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
    private final KeyListener keyListener;
    private final int MIN_PLATFORM_LENGTH = 3;
    private final int MAX_PLATFORM_LENGTH = 10;
    private final int PLATFORM_HEIGHT = 2;
    private final int SCREEN_WIDTH = 400;
    private final int SCREEN_HEIGHT = 200;
    public GamePanel(){
        super();
        setSize(SCREEN_WIDTH,SCREEN_HEIGHT);
        setLayout(null);
        setFocusable(true);
        doodle = new Doodle();
        platforms = new ArrayList<>();
        platforms = Platform.generatePlatforms(SCREEN_HEIGHT*SCREEN_WIDTH/PLATFORM_HEIGHT/MAX_PLATFORM_LENGTH/2,
                MIN_PLATFORM_LENGTH,MAX_PLATFORM_LENGTH,PLATFORM_HEIGHT,
                0, SCREEN_HEIGHT,SCREEN_WIDTH);
        keyListener = new KeyListener(doodle, KeyEvent.VK_LEFT,KeyEvent.VK_RIGHT,SCREEN_WIDTH);
        addKeyListener(keyListener);
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
    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        doodle.paint(graphics);
        for (Platform platform: platforms){
            platform.paint(graphics);
        }
    }
}
