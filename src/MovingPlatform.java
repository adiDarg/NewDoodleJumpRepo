import javax.swing.*;
import java.util.Objects;

public class MovingPlatform extends Platform{
    private final int SPEED;
    private double deltaX;
    private int direction;
    private int minX;

    public MovingPlatform(int width, int height, int speed) {
        super(width, height);
        this.SPEED = speed;
        this.direction = 1;
        setSprite(new ImageIcon(Objects.requireNonNull(getClass().getResource("/gameImages/basicGame/p-blue.png"))).getImage());
        minX = -1;
    }
    private void defineMinX(){
        minX = this.getX();
    }
    public void move(double deltaSeconds){
        if (minX == -1)
            defineMinX();
        deltaX += deltaSeconds * SPEED * direction;
        if (Math.abs(deltaX) >= 1){
            this.setX(this.getX() + (int)deltaX);
            deltaX -= (int) deltaX;
        }
        int range = 150;
        if (this.getX() > this.minX + range || this.getX() < this.minX){
            direction *= -1;
        }
        if (this.getX() > this.minX + range){
            this.setX(this.minX + range - 1);
        }
        if (this.getX() < this.minX){
            this.setX(this.minX + 1);
        }
    }
}
