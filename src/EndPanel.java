import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class EndPanel extends JPanel {
    private final Image BACKGROUND;
    private final int WIDTH;
    private final int HEIGHT;
    private final int PADDING = 10;
    private final int HEAD_LINE_LENGTH = 50;
    private final int BODY_LINE_LENGTH = 30;
    public EndPanel(int screenWidth, int screenHeight, Image background, Window window, int finalScore) {
        this.BACKGROUND = background;
        this.WIDTH = screenWidth;
        this.HEIGHT = screenHeight;
        this.setBounds(0, 0, WIDTH, HEIGHT);
        this.setFocusable(false);
        this.setLayout(null);

        JLabel head = new JLabel("GAME OVER", SwingConstants.CENTER);
        head.setBounds(0, 100, screenWidth, HEAD_LINE_LENGTH);
        JLabel body = new JLabel("Best Height Reached: " + finalScore, SwingConstants.CENTER);
        body.setBounds(0,100 + HEAD_LINE_LENGTH + PADDING, screenWidth, BODY_LINE_LENGTH);

        Font customFont;
        Font customFontTitle;
        try {
            customFontTitle = Font.createFont(Font.TRUETYPE_FONT, new File("src/al_seana/al-seana.ttf")).deriveFont(60f);
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/al_seana/al-seana.ttf")).deriveFont(24f);
            head.setFont(customFontTitle);
            body.setFont(customFont);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }

        this.add(head);
        this.add(body);

        ImageIconButton playAgainButton = createButton(new ImageIcon("src\\gameImages\\Doodle Jump\\play-again.png"),new ImageIcon("src\\gameImages\\Doodle Jump\\play-again-on.png"),WIDTH/4 ,100 + HEAD_LINE_LENGTH + BODY_LINE_LENGTH + 2 * PADDING, 1);
        playAgainButton.addActionListener((event)->{window.newGame();});
        ImageIconButton exitButton = createButton(new ImageIcon("src\\gameImages\\Doodle Jump\\exit-game.png"), new ImageIcon("src\\gameImages\\Doodle Jump\\exit-game-on.png"),3 * WIDTH / 4 - playAgainButton.getIconWidth(), 100 + HEAD_LINE_LENGTH + BODY_LINE_LENGTH + 2 * PADDING, 1);
        exitButton.addActionListener((event)->window.dispose());
        this.add(playAgainButton);
        this.add(exitButton);

        setVisible(false);
    }
    public ImageIconButton createButton(ImageIcon imageIcon, ImageIcon pressedImageIcon, int x, int y, int scale){
        ImageIconButton button = new ImageIconButton(imageIcon,pressedImageIcon);
        button.scaleIcons(button.getIconWidth() * scale,button.getIconHeight() * scale);
        button.setBounds(x,y,button.getIconWidth(),button.getIconHeight());
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        return button;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(BACKGROUND,0,0,WIDTH,HEIGHT,this);
    }
}
