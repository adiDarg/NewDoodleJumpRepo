
import java.awt.*;
import java.util.List;

public class Doodle {
    private int x,y;
    private int maxHeightReached;
    private int currentHeight;
    private final int WIDTH = 10;
    private final int HEIGHT = 10;
    private double speed;
    private final int GRAVITY = -10;
    private Image sprite;
    public Doodle(){
        x = 10;
        y = 10;
        speed = 0;
        maxHeightReached = y;
        currentHeight = y;
    }
    private void jump(){
        speed = 15;
    }
    public void moveRight(int screenWidth){
        x++;
        if (x == screenWidth){
            x = 0;
        }
    }
    public void moveLeft(int screenWidth){
        x--;
        if (x == 0){
            x = screenWidth;
        }
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
    public void moveVertically(double deltaSeconds, List<Platform> platformList){
        for (Platform platform: platformList){
            if (doodleAlignedWithPlatform(platform,deltaSeconds)){
                jump();
                break;
            }
        }
        y += (int) (speed*deltaSeconds);
        currentHeight += (int) (speed*deltaSeconds);
        if (currentHeight > maxHeightReached){
            maxHeightReached = currentHeight;
        }
        speed -= GRAVITY*deltaSeconds;
    }
    public boolean hasLost(){
        return y < 0;
    }
    private boolean doodleAlignedWithPlatform(Platform platform, double deltaSeconds){
        return (this.x >= platform.getX() && this.x <= platform.getX() + platform.getWidth()) &&
                (this.y - platform.getHeight() < Math.abs(speed*deltaSeconds) && speed < 0);
    }
    public void paint(Graphics graphics){
        graphics.drawImage(sprite,x,y,WIDTH,HEIGHT,null);
    }
}
