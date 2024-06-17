
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Random;

public class Doodle {
    private int x,y;
    private double deltaY;
    private double maxHeightReached;
    private double currentHeight;
    private final int WIDTH = 70;
    private final int HEIGHT = 70;
    private double speed;
    private final int GRAVITY = 35;
    private final int MAX_SPEED = -130;
    private Image sprite;
    private final Image FACE_LEFT =  new ImageIcon("src\\gameImages\\basicGame\\doodleL.png").getImage();
    private final Image FACE_RIGHT = new ImageIcon("src\\gameImages\\basicGame\\doodleR.png").getImage();
    private int horizontalMoveDirection;
    public Doodle(int x, int y){
        this.x = x;
        this.y = y;
        deltaY = 0;
        speed = 0;
        maxHeightReached = 0;
        currentHeight = 0;
        sprite = FACE_LEFT;
    }
    public void jump(){
        speed = MAX_SPEED;
    }
    public void superJump(){
        Random random = new Random();
        speed = random.nextDouble(1.1,2) * MAX_SPEED;
    }
    public double getSpeed(){
        return speed;
    }
    public int getMAX_SPEED(){
        return MAX_SPEED;
    }
    public int getX(){return x;}
    public int getY(){
        return y;
    }
    public void setY(int y){
        this.y = y;
    }
    public int getHEIGHT(){
        return HEIGHT;
    }
    public int getGRAVITY(){
        return GRAVITY;
    }
    public void setHorizontalMoveDirection(int horizontalMoveDirection){
        this.horizontalMoveDirection = horizontalMoveDirection;
    }
    public void moveHorizontal(int screenWidth){
        double HORIZONTAL_SPEED = 4.5;
        x += (int) (horizontalMoveDirection * HORIZONTAL_SPEED);
        if (x <= -this.WIDTH){
            x = screenWidth;
        }
        else if (x >= screenWidth){
            x = 0;
        }
        if (horizontalMoveDirection == -1){
            sprite = FACE_LEFT;
        }
        else if (horizontalMoveDirection == 1){
            sprite = FACE_RIGHT;
        }
    }
    public void moveVertically(double deltaSeconds, GamePanel gamePanel){
        currentHeight -= speed*deltaSeconds;
        deltaY += speed*deltaSeconds;
        if (Math.abs(deltaY) >= 1){
            y += (int)deltaY;
            deltaY -= (int)deltaY;
        }
        if (currentHeight > maxHeightReached){
            if ((int)maxHeightReached / GamePanel.DISTANCE_BETWEEN_LEVELS < (int)currentHeight / GamePanel.DISTANCE_BETWEEN_LEVELS){
                new Thread(()->{
                    gamePanel.increaseLevel();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
            }
            maxHeightReached = currentHeight;
        }
        speed += GRAVITY *deltaSeconds;
    }
    public int checkCollisionWithPlatforms(List<Platform> platformList){
        try {
            for (Platform platform: platformList) {
                if (!checkCollisionWithPlatform(platform)) {
                    continue;
                }
                if (!(platform instanceof SpringPlatform)) {
                    return 1;
                }
                if (!(((SpringPlatform) platform).didHitSpring(this))) {
                    return 1;
                }
                return 2;
            }
        } catch (Exception ignored){}
        return 0;
    }
    public boolean hasLost(int screenHeight){
        return y > screenHeight;
    }
    public double getMaxHeight(){
        return maxHeightReached;
    }
    public boolean checkCollisionWithPlatform(Platform platform){
        return (this.x + this.WIDTH >= platform.getX() && this.x <= platform.getX() + platform.getWidth()) &&
                (platform.getY() - this.y <= this.HEIGHT && platform.getY() - this.y >= this.HEIGHT * 7 / 8 && speed > 0);
    }
    public void paint(Graphics graphics){
        graphics.drawImage(sprite,x,y,WIDTH,HEIGHT,null);
    }
}
