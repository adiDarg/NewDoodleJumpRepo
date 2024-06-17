import javax.swing.*;

public class FallingPlatform extends Platform{
    public FallingPlatform(int width, int height) {
        super(width, height);
        setSprite(new ImageIcon("src\\gameImages\\brown\\p-brown-1.png").getImage());
    }
    public boolean checkCollision(Doodle doodle){
        if (doodle.checkCollisionWithPlatform(this)){
            for (int i = 2; i <= 6; i++){
                setSprite(new ImageIcon("src\\gameImages\\brown\\p-brown-" + i + ".png").getImage());
                try {
                    Thread.sleep(200/i);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            return true;
        }
        return false;
    }
}
