import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {
    public String gameName;
    private JButton howToPlay;
    private JButton startGame;
    private final Image BACKGROUND;
    private final int WIDTH;
    private final int HEIGHT;

    public MenuPanel(int buttonWidth, int buttonHeight, Image background,int width, int height, Window window){
        this.BACKGROUND = background;
        this.WIDTH = width;
        this.HEIGHT = height;

        gameName ="Doodle Jump";
        this.setBounds(0,0, Window.WIDTH, Window.HEIGHT);
        this.setLayout(null);
        this.setFocusable(true);

        howToPlay = new JButton();
        howToPlay.setBounds((Window.WIDTH-buttonWidth)/3, Window.HEIGHT/2,buttonWidth,buttonHeight);
        this.add(howToPlay);

        startGame = new JButton();
        startGame.setBounds(2*(Window.WIDTH-buttonWidth)/3, Window.HEIGHT/2,buttonWidth,buttonHeight);
        startGame.addActionListener((event) ->{
            window.switchPanels();
        } );
        this.add(startGame);

        this.setVisible(true);
    }
    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);
        g.drawImage(BACKGROUND,0,0,this);
    }
}