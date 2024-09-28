import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class MenuPanel extends JPanel {
    private final ImageIconButton switchToGame;
    private final Image BACKGROUND;
    private final int WIDTH;
    private final int HEIGHT;
    private final int BUTTON_WIDTH = 150;
    private final int BUTTON_HEIGHT = 50;

    public MenuPanel(Image background,int width, int height, Window window){
        this.BACKGROUND = background;
        this.WIDTH = width;
        this.HEIGHT = height;

        this.setBounds(0,0, WIDTH, HEIGHT);
        this.setLayout(null);
        this.setFocusable(true);

        ImageIconButton howToPlay = new ImageIconButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("/gameImages/InstructionsWhite.png"))),
                new ImageIcon( Objects.requireNonNull(getClass().getResource("/gameImages/InstructionsRed.png"))));
        howToPlay.scaleIcons(BUTTON_WIDTH,BUTTON_HEIGHT);
        howToPlay.setBounds((Window.WIDTH- howToPlay.getIconWidth())/2, Window.HEIGHT/2  - howToPlay.getIconHeight(),
                howToPlay.getIconWidth(), howToPlay.getIconHeight());

        howToPlay.setContentAreaFilled(false);
        howToPlay.setBorderPainted(false);
        howToPlay.setFocusPainted(false);

        howToPlay.addActionListener((event) ->{
            window.switchBetweenMenuAndInstructions();
        });
        this.add(howToPlay);

        switchToGame = new ImageIconButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("/gameImages/Doodle Jump/play.png"))),
                new ImageIcon(Objects.requireNonNull(getClass().getResource("/gameImages/Doodle Jump/play-on.png"))));
        switchToGame.scaleIcons(BUTTON_WIDTH,BUTTON_HEIGHT);
        switchToGame.setBounds((Window.WIDTH-switchToGame.getIconWidth())/2, Window.HEIGHT/2 + switchToGame.getIconHeight(),
                switchToGame.getIconWidth(),switchToGame.getIconHeight());

        switchToGame.setContentAreaFilled(false);
        switchToGame.setBorderPainted(false);
        switchToGame.setFocusPainted(false);
        this.add(switchToGame);

        switchToGame.addActionListener((event) ->{
            ImageIcon resumeButton = new ImageIcon(Objects.requireNonNull(getClass().getResource("/gameImages/Doodle Jump/resume.png")));
            switchToGame.setIcon(resumeButton);

            ImageIcon resumeInRed = new ImageIcon(Objects.requireNonNull(getClass().getResource("/gameImages/Doodle Jump/resume-on.png")));
            switchToGame.setPressedIcon(resumeInRed);

            switchToGame.scaleIcons(BUTTON_WIDTH,BUTTON_HEIGHT);

            window.switchToGame();
        } );

        this.setVisible(true);
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        g.drawImage(BACKGROUND,0,0,WIDTH,HEIGHT,this);
        g.drawImage(new ImageIcon(Objects.requireNonNull(getClass().getResource("/gameImages/Doodle Jump/hole@2x.png"))).getImage(),0,0,null);
    }
}