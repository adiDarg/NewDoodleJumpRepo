
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
        MINIMUM_X_DISTANCE = width * 3/2;
        MINIMUM_Y_DISTANCE = height * 3/2;
        this.width = width;
        this.height = height;
        sprite = new ImageIcon("C:\\Users\\Owner\\IdeaProjects\\DoodleJump\\src\\gameImages\\basicGame\\p-green.png").getImage();
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
            if (platformList.isEmpty()){
                validLocation = true;
            }
        }
        platformList.add(platformToAdd);
    }
    public static List<Platform> generatePlatforms(int amount, int minWidth,int maxWidth, int height,int minY, int maxY, int screenWidth){
        LinkedList<Platform> platformsGenerated = new LinkedList<>();
        Random random = new Random();
        for (int i = 1; i<= amount; i++){
            try {
                Platform platform = new Platform(random.nextInt(minWidth + i,maxWidth+1),height);
                Platform.addToList(platform,platformsGenerated,minY,maxY,screenWidth);
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
        y -= deltaY;
    }
    public void paint(Graphics graphics){
        graphics.drawImage(sprite,x,y,width,height,null);
    }
}
