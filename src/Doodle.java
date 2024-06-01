import java.util.List;
import java.util.Random;

public class Doodle {
    private int x,y;
    private int maxHeightReached;
    private int currentHeight;
    private final int WIDTH = 10;
    private final int HEIGHT = 10;
    private double speed;
    private final int GRAVITY = -10;
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
    public void moveRight(){
        x++;
    }
    public void moveLeft(){
        x--;
    }
    public void moveVertically(double deltaSeconds, List<Platform> platformList, int screenHeight, int screenWidth){
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
        if (y > screenHeight){
            adjustScreen(screenHeight,screenWidth,platformList);
        }
    }
    private void adjustScreen(int screenHeight, int screenWidth, List<Platform> platformList){
        int yDifference = y - screenHeight;
        y = screenHeight-this.HEIGHT;
        Random random = new Random();
        for (Platform platform: platformList){
            platform.lower(yDifference);
        }
        for (int i = 1; i <= random.nextInt(1, yDifference); i++){
            Platform newPlatform = new Platform(random.nextInt(3,10),2); //HARD-CODED
            newPlatform.addToList(platformList,y-yDifference,screenHeight,screenWidth);
        }
    }
    public boolean hasLost(){
        return y < 0;
    }
    private boolean doodleAlignedWithPlatform(Platform platform, double deltaSeconds){
        return (this.x >= platform.getX() && this.x <= platform.getX() + platform.width) &&
                (this.y - platform.height < Math.abs(speed*deltaSeconds) && speed < 0);
    }
    public void paint(){
        //paint doodle character
    }
}
