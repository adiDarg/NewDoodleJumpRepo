import java.util.List;
import java.util.Random;

public class Platform {
    private int x,y;
    public final int width; //Made public since final makes it readonly anyway
    public final int height;
    private final int MINIMUM_Y_DISTANCE = 5;
    private final int MINIMUM_X_DISTANCE = 15;
    public Platform(int width, int height){
        this.width = width;
        this.height = height;
    }
    public void addToList(List<Platform> platformList, int lowerBound, int upperBound, int screenWidth){
        Random random = new Random();
        boolean validLocation = false;
        while (!validLocation){
            x = random.nextInt(0,screenWidth);
            y = random.nextInt(lowerBound,upperBound);
            for (Platform platform : platformList) {
                if (platform.y < lowerBound || platform.y > upperBound) {
                    continue;
                }
                if (Math.abs(this.y - platform.y) < MINIMUM_Y_DISTANCE &&
                        Math.abs(this.x - platform.x) < MINIMUM_X_DISTANCE) {
                    validLocation = true;
                }
            }
        }
        platformList.add(this);
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public void lower(int deltaY){
        y -= deltaY;
    }
}
