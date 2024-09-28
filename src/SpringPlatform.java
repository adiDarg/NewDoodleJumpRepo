import javax.swing.*;
import java.util.Objects;

public class SpringPlatform extends Platform {
    public SpringPlatform(int width, int height) {
        super(width, height);
        setSprite(new ImageIcon(Objects.requireNonNull(getClass().getResource("/gameImages/basicGame/p-green-s0.png"))).getImage());
    }
    public boolean didHitSpring(Doodle doodle){
        if (!(doodle.getX() >= this.getX() && doodle.getX() < this.getX() + this.getWidth()/3)){
            new Thread(()->{
                setSprite(new ImageIcon(Objects.requireNonNull(getClass().getResource("/gameImages/basicGame/p-green-s1.png"))).getImage());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                setSprite(new ImageIcon(Objects.requireNonNull(getClass().getResource("/gameImages/basicGame/p-green-s0.png"))).getImage());
            }).start();
            return true;
        }
        return false;
    }
}
