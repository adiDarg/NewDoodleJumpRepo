import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BluePlatform extends Platform{
    public BluePlatform(int width, int height, int x, int y){
        super(width, height, x, y,new ImageIcon("gameImages\\basicGame\\p-blue.png").getImage());
        this.move();
    }
    public BluePlatform(int width, int height){
        super(width, height);
        this.setSprite(new ImageIcon("gameImages\\basicGame\\p-blue.png").getImage());
        this.move();
    }
    public void move(){
        int[] s = {this.getX()};
       new Thread(() -> {
           while (s[0] < Window.WIDTH){
               s[0]++;
               this.setX(s[0]);
               try {
                   Thread.sleep(20);
               } catch (InterruptedException e) {
                   throw new RuntimeException(e);
               }
           }
           while (s[0] > 0){
               s[0]--;
               this.setX(s[0]);
               try {
                   Thread.sleep(20);
               } catch (InterruptedException e) {
                   throw new RuntimeException(e);
               }
           }
       }).start();
        }
    }

