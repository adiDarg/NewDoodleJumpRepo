import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class InstructionsPanel extends JPanel {
    private Image background;
    private JLabel head;
    private LinkedList<JLabel> body;
    private final int WIDTH;
    private final int HEIGHT;
    private final int HEAD_LINE_LENGTH = 50;
    private final int BODY_LINE_LENGTH = 30;
    public InstructionsPanel(int screenWidth, int screenHeight, Image background, Window window){
        this.WIDTH = screenWidth;
        this.HEIGHT = screenHeight;
        this.setBounds(0,0, WIDTH, HEIGHT);
        this.setFocusable(false);
        this.setLayout(null);
        this.background = background;

        head = new JLabel("INSTRUCTIONS",SwingConstants.CENTER);
        head.setBounds(0, 0, screenWidth, HEAD_LINE_LENGTH);

        body = new LinkedList<>();
        JLabel label1 = new JLabel("Your character is set in a world of platforms.");
        label1.setBounds(0,HEAD_LINE_LENGTH + 10,screenWidth,BODY_LINE_LENGTH);
        JLabel label2 = new JLabel("Your goal is to get as far as possible.");
        label2.setBounds(0,HEAD_LINE_LENGTH + 10 + BODY_LINE_LENGTH ,screenWidth,BODY_LINE_LENGTH);
        JLabel label3 = new JLabel("If you fall under the bottom of the screen you lose.");
        label3.setBounds(0,HEAD_LINE_LENGTH + 10 + BODY_LINE_LENGTH * 2 ,screenWidth,BODY_LINE_LENGTH);
        JLabel label4 = new JLabel("Use the arrow keys on your keyboard to move between");
        label4.setBounds(0,HEAD_LINE_LENGTH + 10 + BODY_LINE_LENGTH * 3 ,screenWidth,BODY_LINE_LENGTH);
        JLabel label5 = new JLabel("left and right. Touching either of the walls will teleport");
        label5.setBounds(0,HEAD_LINE_LENGTH + 10 + BODY_LINE_LENGTH * 4 ,screenWidth,BODY_LINE_LENGTH);
        JLabel label6 = new JLabel("you to the other side. As your height increases, ,the platforms");
        label6.setBounds(0,HEAD_LINE_LENGTH + 10 + BODY_LINE_LENGTH * 5 ,screenWidth,BODY_LINE_LENGTH);
        JLabel label7 = new JLabel("will decrease in size until reaching a minimal height.");
        label7.setBounds(0,HEAD_LINE_LENGTH + 10 + BODY_LINE_LENGTH * 6 ,screenWidth,BODY_LINE_LENGTH);
        JLabel label8 = new JLabel("You can pause at any point by pressing the ESCAPE button.");
        label8.setBounds(0,HEAD_LINE_LENGTH + 10 + BODY_LINE_LENGTH * 7 ,screenWidth,BODY_LINE_LENGTH);

        body.add(label1);
        body.add(label2);
        body.add(label3);
        body.add(label4);
        body.add(label5);
        body.add(label6);
        body.add(label7);
        body.add(label8);
        /*
                Your character is set in a world of platforms. Your goal is to get as far as possible.
                If you fall under the bottom of the screen you lose.
                Use the arrow keys on your keyboard to move between left and right.
                Touching either of the walls will teleport you to the other side.
                As your height increases the platforms will decrease in size until reaching a minimal height.
                You can pause at any point by pressing the ESCAPE button.*/

        Font customFont;
        Font customFontTitle;
        try {
           customFontTitle = Font.createFont(Font.TRUETYPE_FONT, new File("src/al_seana/al-seana.ttf")).deriveFont(60f);
           customFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/al_seana/al-seana.ttf")).deriveFont(24f);
           head.setFont(customFontTitle);
           for (JLabel label: body){
               label.setFont(customFont);
           }
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
        this.add(head);
        for (JLabel label: body){
            this.add(label);
        }

        ImageIconButton returnToMenu = new ImageIconButton(new ImageIcon("src\\gameImages\\Doodle Jump\\menu.png"), new ImageIcon("src/gameImages/Doodle Jump/menu-on.png"));
        returnToMenu.scaleIcons(120,50);
        returnToMenu.addActionListener((event)->{
            window.switchBetweenMenuAndInstructions();
        });
        returnToMenu.setBounds(screenWidth/2 - returnToMenu.getIconWidth()/2,HEAD_LINE_LENGTH + 10 + BODY_LINE_LENGTH * 9,
                returnToMenu.getIconWidth(),returnToMenu.getIconHeight());
        returnToMenu.setContentAreaFilled(false);
        returnToMenu.setBorderPainted(false);
        returnToMenu.setFocusPainted(false);
        add(returnToMenu);
        setVisible(false);
    }
    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        graphics.drawImage(background,0,0,WIDTH,HEIGHT,this);
    }
}
