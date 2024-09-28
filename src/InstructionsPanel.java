import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class InstructionsPanel extends JPanel {
    private final Image BACKGROUND;
    private final LinkedList<JLabel> body;
    private final int WIDTH;
    private final int HEIGHT;
    private final int HEAD_LINE_LENGTH = 50;

    public InstructionsPanel(int screenWidth, int screenHeight, Image background, Window window){
        this.WIDTH = screenWidth;
        this.HEIGHT = screenHeight;
        this.setBounds(0,0, WIDTH, HEIGHT);
        this.setFocusable(false);
        this.setLayout(null);
        this.BACKGROUND = background;

        JLabel head = new JLabel("INSTRUCTIONS", SwingConstants.CENTER);
        head.setBounds(0, 0, screenWidth, HEAD_LINE_LENGTH);

        File instructionsBody = new File("src/Instructions");
        Scanner reader = null;
        try {
            reader = new Scanner(instructionsBody);
        } catch (Exception ignored){}
        body = new LinkedList<>();
        int lineNumber = 1;

        while (reader != null && reader.hasNext()){
            addLineToBody(reader.nextLine(),lineNumber);
            lineNumber++;
        }

        Font customFont;
        Font customFontTitle;
        try {
           customFontTitle = Font.createFont(Font.TRUETYPE_FONT, new File("al_seana/al-seana.ttf")).deriveFont(60f);
           customFont = Font.createFont(Font.TRUETYPE_FONT, new File("al_seana/al-seana.ttf")).deriveFont(24f);
        } catch (FontFormatException | IOException e) {
            customFont = new Font("Serif", Font.PLAIN, 12);
            customFontTitle = new Font("Serif", Font.PLAIN, 50);
        }
        head.setFont(customFontTitle);
        for (JLabel label: body){
            label.setFont(customFont);
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
        returnToMenu.setBounds(screenWidth/2 - returnToMenu.getIconWidth()/2,HEIGHT - 2 * returnToMenu.getIconHeight(),
                returnToMenu.getIconWidth(),returnToMenu.getIconHeight());
        returnToMenu.setContentAreaFilled(false);
        returnToMenu.setBorderPainted(false);
        returnToMenu.setFocusPainted(false);
        add(returnToMenu);
        setVisible(false);
    }
    private void addLineToBody(String text, int lineNumber){
        JLabel label = new JLabel(text);
        int BODY_LINE_LENGTH = 30;
        int PADDING = 10;
        label.setBounds(0,HEAD_LINE_LENGTH + PADDING + BODY_LINE_LENGTH * (lineNumber - 1),WIDTH, BODY_LINE_LENGTH);
        body.add(label);
    }
    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        graphics.drawImage(BACKGROUND,0,0,WIDTH,HEIGHT,this);
    }
}
