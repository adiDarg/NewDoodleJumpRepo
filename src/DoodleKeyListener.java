import java.awt.event.KeyEvent;

public class DoodleKeyListener implements java.awt.event.KeyListener {
    private final Doodle doodle;
    private final int leftKey;
    private final int rightKey;
    public DoodleKeyListener(Doodle doodle, int leftKey, int rightKey){
        this.doodle = doodle;
        this.leftKey = leftKey;
        this.rightKey = rightKey;
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == leftKey){
            doodle.setHorizontalMoveDirection(-1);
        }
        else if (e.getKeyCode() == rightKey){
            doodle.setHorizontalMoveDirection(1);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == leftKey || e.getKeyCode() == rightKey){
            doodle.setHorizontalMoveDirection(0);
        }

    }
}
