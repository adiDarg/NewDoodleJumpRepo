import java.awt.event.KeyEvent;

public class DoodleKeyListener implements java.awt.event.KeyListener {
    private final Doodle doodle;
    private final int leftKey;
    private final int rightKey;
    private final int screenWidth;
    public DoodleKeyListener(Doodle doodle, int leftKey, int rightKey, int screenWidth){
        this.doodle = doodle;
        this.leftKey = leftKey;
        this.rightKey = rightKey;
        this.screenWidth = screenWidth;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("Key Pressed");
        if (e.getKeyCode() == leftKey){
            doodle.moveLeft(screenWidth);
        }
        else if (e.getKeyCode() == rightKey){
            doodle.moveRight(screenWidth);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
