
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Doodle {
    private int x,y;
    private double maxHeightReached;
    private double currentHeight;
    private final int WIDTH = 50;
    private final int HEIGHT = 50;
    private double speed;
    private final double HORIZONTAL_SPEED = 5;
    private final int GRAVITY = 10;
    private Image sprite;
    private final Image FACE_LEFT =  new ImageIcon("C:\\Users\\Owner\\IdeaProjects\\DoodleJump\\src\\gameImages\\basicGame\\doodleL.png").getImage();
    private final Image FACE_RIGHT = new ImageIcon("C:\\Users\\Owner\\IdeaProjects\\DoodleJump\\src\\gameImages\\basicGame\\doodleR.png").getImage();
    private int horizontalMoveDirection;
    public Doodle(int screenWidth, int screenHeight){
        x = screenWidth/2;
        y = screenHeight - 250;
        speed = 0;
        maxHeightReached = y;
        currentHeight = y;
        sprite = FACE_LEFT;
    }
    private void jump(){
        speed = -50;
    }
    public void moveHorizontal(int screenWidth){
        x += (int) (horizontalMoveDirection * HORIZONTAL_SPEED);
        if (x <= 0){
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
    public void setHorizontalMoveDirection(int horizontalMoveDirection){
        this.horizontalMoveDirection = horizontalMoveDirection;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public void setY(int y){
        this.y = y;
    }
    public int getWIDTH(){
        return WIDTH;
    }
    public int getHEIGHT(){
        return HEIGHT;
    }
    public void moveVertically(double deltaSeconds){
        currentHeight +=  speed*deltaSeconds;
        y = (int) (currentHeight);
        if (currentHeight > maxHeightReached){
            maxHeightReached = currentHeight;
        }
        speed += GRAVITY*deltaSeconds;
    }
    public void checkCollision(double deltaSeconds,List<Platform> platformList){
        for (Platform platform: platformList){
            if (doodleAlignedWithPlatform(platform,deltaSeconds)){
                jump();
                break;
            }
        }
    }
    public boolean hasLost(int screenHeight){
        return y > screenHeight;
    }
    private boolean doodleAlignedWithPlatform(Platform platform, double deltaSeconds){
        return (this.x + this.WIDTH >= platform.getX() && this.x <= platform.getX() + platform.getWidth()) &&
                (Math.abs(this.y - platform.getY() + platform.getHeight()) < platform.getHeight() && speed > 0);
    }
    public void paint(Graphics graphics){
        graphics.drawImage(sprite,x,y,WIDTH,HEIGHT,null);
    }
}
