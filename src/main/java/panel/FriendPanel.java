package panel;

import java.awt.*;
import javax.swing.*;
/*
 * Created by JFormDesigner on Tue Nov 15 16:14:16 KST 2022
 */



/**
 * @author unknown
 */
public class FriendPanel extends JPanel {
    public FriendPanel(String userName) {
        initComponents();
        resizeIcon();
        setVisible(true);
        //this.profile.setIcon(cm.img);
        this.name.setText(userName);
        //this.status.setText(cm.status);
    }

    private void resizeIcon() {
        ImageIcon icon = (ImageIcon) this.profile.getIcon();
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImg);
        this.profile.setIcon(resizedIcon);
    }

    public void setStatus(String status) {
        this.status.setText(status);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        name = new JLabel();
        status = new JLabel();
        profile = new JLabel();

        //======== this ========
        setBackground(Color.white);
        setLayout(null);

        //---- name ----
        name.setFont(name.getFont().deriveFont(name.getFont().getStyle() | Font.BOLD, 14f));
        add(name);
        name.setBounds(90, 23, 60, 20);

        //---- status ----
        status.setFont(new Font("\ub9d1\uc740 \uace0\ub515", Font.PLAIN, 9));
        status.setText("HSU \ucef4\ud4e8\ud130\uacf5\ud559\ubd80");
        add(status);
        status.setBounds(90, 40, 202, 20);

        //---- profile ----
        profile.setIcon(new ImageIcon(getClass().getResource("/default_profile.jpg")));
        add(profile);
        profile.setBounds(25, 15, 50, 50);

        setPreferredSize(new Dimension(309, 80));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JLabel name;
    private JLabel status;
    private JLabel profile;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
