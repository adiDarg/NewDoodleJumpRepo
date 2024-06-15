import javax.swing.*;
import java.awt.*;

public class ImageIconButton extends JButton {
    private ImageIcon imageIcon;
    private ImageIcon pressedImageIcon;
    private ImageIcon rollOverImageIcon;
    public ImageIconButton(){
        super();
    }
    public ImageIconButton(ImageIcon imageIcon, ImageIcon pressedImageIcon, ImageIcon rollOverImageIcon){
        super();
        this.imageIcon = imageIcon;
        this.setIcon(imageIcon);
        this.pressedImageIcon = pressedImageIcon;
        this.setPressedIcon(pressedImageIcon);
        this.rollOverImageIcon = rollOverImageIcon;
        this.setRolloverIcon(rollOverImageIcon);
    }
    public ImageIconButton(ImageIcon imageIcon, ImageIcon pressedImageIcon){
        super();
        this.imageIcon = imageIcon;
        this.setIcon(imageIcon);
        this.pressedImageIcon = pressedImageIcon;
        this.setPressedIcon(pressedImageIcon);
    }
    public ImageIconButton(ImageIcon imageIcon){
        super();
        this.imageIcon = imageIcon;
        this.setIcon(imageIcon);
    }
    public void scaleIcons(int width, int height){
        if (imageIcon != null){
            imageIcon = new ImageIcon(imageIcon.getImage().getScaledInstance(width,height,Image.SCALE_SMOOTH));
            this.setIcon(imageIcon);
        }
        if (pressedImageIcon != null){
            pressedImageIcon = new ImageIcon(pressedImageIcon.getImage().getScaledInstance(width,height,Image.SCALE_SMOOTH));
            this.setPressedIcon(pressedImageIcon);
        }
        if (rollOverImageIcon != null){
            rollOverImageIcon = new ImageIcon(rollOverImageIcon.getImage().getScaledInstance(width,height,Image.SCALE_SMOOTH));
            this.setRolloverIcon(rollOverImageIcon);
        }
    }
    public void setIcon(ImageIcon imageIcon){
        super.setIcon(imageIcon);
        this.imageIcon = imageIcon;
    }
    public void setPressedIcon(ImageIcon pressedImageIcon){
        super.setPressedIcon(pressedImageIcon);
        this.pressedImageIcon = pressedImageIcon;
    }
    public void setRolloverIcon(ImageIcon rollOverImageIcon){
        super.setRolloverIcon(rollOverImageIcon);
        this.rollOverImageIcon = rollOverImageIcon;
    }
    public int getIconWidth(){
        return imageIcon.getIconWidth();
    }
    public int getIconHeight(){
        return imageIcon.getIconHeight();
    }
}
