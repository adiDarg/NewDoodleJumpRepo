
import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Platform {
    private int x,y;
    private final int width;
    private final int height;
    private final int MINIMUM_Y_DISTANCE = 5;
    private final int MINIMUM_X_DISTANCE = 15;
    private Image sprite;
    public Platform(int width, int height){
        this.width = width;
        this.height = height;
    }
    public static void addToList(Platform platformToAdd,List<Platform> platformList, int lowerBound, int upperBound, int screenWidth){
        Random random = new Random();
        boolean validLocation = false;
        while (!validLocation){
            platformToAdd.x = random.nextInt(0,screenWidth);
            platformToAdd.y = random.nextInt(lowerBound,upperBound);
            for (Platform platform : platformList) {
                if (platform.y < lowerBound || platform.y > upperBound) {
                    continue;
                }
                if (Math.abs(platformToAdd.y - platform.y) < platformToAdd.MINIMUM_Y_DISTANCE &&
                        Math.abs(platformToAdd.x - platform.x) < platformToAdd.MINIMUM_X_DISTANCE) {
                    validLocation = true;
                    break;
                }
            }
        }
        platformList.add(platformToAdd);
    }
    public static List<Platform> generatePlatforms(int amount, int minWidth,int maxWidth, int height,int minY, int maxY, int screenWidth){
        LinkedList<Platform> platformsGenerated = new LinkedList<>();
        Random random = new Random();
        for (int i = 1; i<= amount; i++){
            Platform platform = new Platform(random.nextInt(minWidth,maxWidth+1),height);
            Platform.addToList(platform,platformsGenerated,minY,maxY,screenWidth);
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
        y -= deltaY;
    }
    public void paint(Graphics graphics){
        graphics.drawImage(sprite,x,y,width,height,null);
    }
}
