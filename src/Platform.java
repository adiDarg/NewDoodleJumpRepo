
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Platform {
    private int x,y;
    private final int width;
    private final int height;
    private static final int MINIMUM_Y_DISTANCE = 70;
    private static final int MINIMUM_X_DISTANCE = 100;
    private static final int MINIMUM_PLATFORM_SIZE = 50;
    private Image sprite;
    public Platform(int width, int height, int x, int y){
        this.width = width;
        this.height = height;
        sprite = new ImageIcon("src\\gameImages\\basicGame\\p-green.png").getImage();
        this.x = x;
        this.y = y;
    }
    public Platform(int width, int height){
        this.width = width;
        this.height = height;
        sprite = new ImageIcon("src\\gameImages\\basicGame\\p-green.png").getImage();
    }

    protected void setSprite(Image sprite){
        this.sprite = sprite;
    }
    protected void setX(int x){
        this.x = x;
    }
    public static void addToList(Platform platformToAdd,List<Platform> platformList, int lowerBound, int upperBound, int screenWidth){
        Random random = new Random();
        boolean validLocation = true;
        int failCount = 0;
       do{
            platformToAdd.x = random.nextInt(0,screenWidth);
            platformToAdd.y = random.nextInt(lowerBound,upperBound);
            for (Platform platform : platformList) {
                if (Math.abs(platformToAdd.y - platform.y) < MINIMUM_Y_DISTANCE &&
                        Math.abs(platformToAdd.x - platform.x) < MINIMUM_X_DISTANCE) {
                    validLocation = false;
                    break;
                }
            }
            failCount++;
            if (failCount == 1000)
                return;
        } while (!validLocation);
        platformList.add(platformToAdd);
    }
    public static void generatePlatforms(int minWidth,int maxWidth, int height,int minY, int maxY, int screenWidth, ArrayList<Platform> existingPlatforms, int level){
        Random random = new Random();
        for (int i = minY; i<= maxY; i += MINIMUM_Y_DISTANCE){
            try {
                if (minWidth * Math.pow(0.9,level) > MINIMUM_PLATFORM_SIZE){
                    minWidth = (int)((double)minWidth * Math.pow(0.95,level - 1));
                    maxWidth = (int)((double)maxWidth * Math.pow(0.95,level - 1));
                }
                else {
                    minWidth = MINIMUM_PLATFORM_SIZE;
                    maxWidth = MINIMUM_PLATFORM_SIZE + 20;
                }
                Platform platform;
                if (random.nextInt(1,15) == 1){
                    platform = new SpringPlatform(random.nextInt(minWidth,maxWidth),height);
                }
                else if ((level <= 50 && random.nextInt(1,15 - level/5) == 1)||
                        (level > 50 && random.nextInt(1,5) == 1)){
                    int speed = random.nextInt(20,30);
                    platform = new MovingPlatform(random.nextInt(minWidth,maxWidth),height,speed);
                }
                else if ((level <= 50 && random.nextInt(1,15 - level/5) == 1)||
                        (level > 50 && random.nextInt(1,5) == 1)){
                    platform = new FallingPlatform(random.nextInt(minWidth,maxWidth),height);
                }
                else {
                    platform = new Platform(random.nextInt(minWidth,maxWidth),height);
                }
                Platform.addToList(platform,existingPlatforms,i,i + MINIMUM_Y_DISTANCE, screenWidth - maxWidth);
            }
            catch (Exception ignored){
                i-= MINIMUM_Y_DISTANCE;
            }
        }
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public int getWidth(){
        return width;
    }
    protected int getHeight(){return height;}
    public void lower(int deltaY){
        y += deltaY;
    }
    public void paint(Graphics graphics){
        graphics.drawImage(sprite,x,y,width,height,null);
    }
}
