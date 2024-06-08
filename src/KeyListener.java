import java.awt.event.KeyEvent;

public class KeyListener implements java.awt.event.KeyListener {
    private final Doodle doodle;
    private final int leftKey;
    private final int rightKey;
    private final int screenWidth;
    public KeyListener(Doodle doodle, int leftKey, int rightKey, int screenWidth){
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
