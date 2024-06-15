
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Doodle {
    private int x,y;
    private double deltaY;
    private double maxHeightReached;
    private double currentHeight;
    private final int WIDTH = 70;
    private final int HEIGHT = 70;
    private double speed;
    private final double HORIZONTAL_SPEED = 4.5;
    private final int NORMAL_GRAVITY = 30;
    private int currentGravity;
    private final int MAX_SPEED = -110;
    private Image sprite;
    private final Image FACE_LEFT =  new ImageIcon("src\\gameImages\\basicGame\\doodleL.png").getImage();
    private final Image FACE_RIGHT = new ImageIcon("src\\gameImages\\basicGame\\doodleR.png").getImage();
    private int horizontalMoveDirection;
    public Doodle(int screenHeight, int x, int y){
        currentGravity = NORMAL_GRAVITY;
        this.x = x;
        this.y = y;
        deltaY = 0;
        speed = 0;
        maxHeightReached = screenHeight - y;
        currentHeight = screenHeight - y;
        sprite = FACE_LEFT;
    }
    private void jump(){
        speed = MAX_SPEED;
    }
    public double getSpeed(){
        return speed;
    }
    public int getMAX_SPEED(){
        return MAX_SPEED;
    }
    public int getY(){
        return y;
    }
    public void setY(int y){
        this.y = y;
    }
    public int getHEIGHT(){
        return HEIGHT;
    }
    public int getCurrentGravity(){
        return currentGravity;
    }
    public void setSpeed(double speed){
        this.speed = speed;
    }
    public void setHorizontalMoveDirection(int horizontalMoveDirection){
        this.horizontalMoveDirection = horizontalMoveDirection;
    }
    public void moveHorizontal(int screenWidth){
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
        speed += currentGravity *deltaSeconds;
    }
    public void checkCollision(double deltaSeconds,List<Platform> platformList){
        try {
            for (Platform platform: platformList){
                if (doodleAlignedWithPlatform(platform,deltaSeconds)){
                    jump();
                    break;
                }
            }
        } catch (Exception ignored){}
    }
    public boolean hasLost(int screenHeight){
        return y > screenHeight;
    }
    public double getMaxHeight(){
        return maxHeightReached;
    }
    public void fly(){
        new Thread(()->{
            currentGravity = 0;
            speed = MAX_SPEED;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            currentGravity = NORMAL_GRAVITY;
        }).start();
    }
    private boolean doodleAlignedWithPlatform(Platform platform, double deltaSeconds){
        return (this.x + this.WIDTH >= platform.getX() && this.x <= platform.getX() + platform.getWidth()) &&
                (platform.getY() - this.y <= this.HEIGHT && platform.getY() - this.y >= this.HEIGHT * 7 / 8 && speed > 0);
    }
    public void paint(Graphics graphics){
        graphics.drawImage(sprite,x,y,WIDTH,HEIGHT,null);
    }
}
