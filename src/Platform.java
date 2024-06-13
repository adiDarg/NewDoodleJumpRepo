
import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Platform {
    private int x,y;
    private final int width;
    private final int height;
    private final int MINIMUM_Y_DISTANCE;
    private final int MINIMUM_X_DISTANCE;
    private Image sprite;
    public Platform(int width, int height){
        MINIMUM_X_DISTANCE = width * 2;
        MINIMUM_Y_DISTANCE = height * 6/5;
        this.width = width;
        this.height = height;
        sprite = new ImageIcon("C:\\Users\\Owner\\IdeaProjects\\DoodleJump\\src\\gameImages\\basicGame\\p-green.png").getImage();
    }
    public static void addToList(Platform platformToAdd,List<Platform> platformList, int lowerBound, int upperBound, int screenWidth){
        Random random = new Random();
        boolean validLocation = true;
        int failCount = 0;
       do{
            platformToAdd.x = random.nextInt(0,screenWidth);
            platformToAdd.y = random.nextInt(lowerBound,upperBound);
            for (Platform platform : platformList) {
                if (Math.abs(platformToAdd.y - platform.y) < platformToAdd.MINIMUM_Y_DISTANCE &&
                        Math.abs(platformToAdd.x - platform.x) < platformToAdd.MINIMUM_X_DISTANCE) {
                    validLocation = false;
                    break;
                }
            }
            failCount++;
            if (failCount == 100)
                return;
        } while (!validLocation);
        platformList.add(platformToAdd);
    }
    public static List<Platform> generatePlatforms(int minWidth,int maxWidth, int height,int minY, int maxY, int screenWidth){
        LinkedList<Platform> platformsGenerated = new LinkedList<>();
        Random random = new Random();
        for (int i = minY + 1; i<= maxY - 100; i += 30){
            try {
                Platform platform = new Platform(random.nextInt(minWidth,maxWidth),height);
                Platform.addToList(platform,platformsGenerated,i,i + platform.MINIMUM_Y_DISTANCE + 10,screenWidth);
            }
            catch (Exception e){
                break;
            }
        }
        return platformsGenerated;
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
    public int getHeight(){
        return height;
    }
    public void lower(int deltaY){
        y += deltaY;
    }
    public void paint(Graphics graphics){
        graphics.drawImage(sprite,x,y,width,height,null);
    }
}
