import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import javax.swing.*;
/*
 * Created by JFormDesigner on Sat Nov 26 02:33:45 KST 2022
 */



/**
 * @author unknown
 */
public class LeftChatPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    public LeftChatPanel(ImageIcon img, String username, String msg) {
        initComponents();
        resizedProfile(img);
        this.name.setText(username);
        this.chat.setText(msg);
    }

    private void resizedProfile(ImageIcon icon) {
        int width = 40;
        BufferedImage circleBuffer = new BufferedImage(width, width, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = circleBuffer.createGraphics();
        g2.setClip(new Ellipse2D.Float(0, 0, width, width));
        g2.drawImage(icon.getImage(), 0, 0, width, width, null);

        profile.setIcon(new ImageIcon(circleBuffer));
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        profile = new JLabel();
        name = new JLabel();
        chat = new JLabel();
        time = new JLabel();

        //======== this ========
        setLayout(null);
        add(profile);
        profile.setBounds(15, 10, 45, 45);
        add(name);
        name.setBounds(70, 10, 200, 20);
        add(chat);
        chat.setBounds(70, 40, 195, 30);
        add(time);
        time.setBounds(280, 50, 60, 20);

        setPreferredSize(new Dimension(365, 85));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JLabel profile;
    private JLabel name;
    private JLabel chat;
    private JLabel time;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
