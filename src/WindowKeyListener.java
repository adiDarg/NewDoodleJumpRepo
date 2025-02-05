import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class WindowKeyListener implements KeyListener {
    private final Window window;
    public WindowKeyListener(Window window){
        this.window = window;
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
            window.switchBetweenMenuAndGame();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
