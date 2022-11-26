import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
/*
 * Created by JFormDesigner on Sun Nov 20 23:50:31 KST 2022
 */



/**
 * @author unknown
 */
public class SelectFriendDialogPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    public ImageIcon icon;
    public String UserName;
    public SelectFriendDialogPanel(ImageIcon icon, String name) {
        initComponents();
        this.icon = icon;
        UserName = name;
        Image img = icon.getImage().getScaledInstance(lblUserIcon.getWidth(), lblUserIcon.getHeight(), Image.SCALE_SMOOTH);
        this.lblUserIcon.setIcon(new ImageIcon(img));
        this.lblUserName.setText(name);
        setVisible(true);
    }

    public String getName() {
        return this.checkBox.getText();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        checkBox = new JCheckBox();
        lblUserName = new JLabel();
        lblUserIcon = new JLabel();

        //======== this ========
        setBackground(Color.white);
        setLayout(null);

        //---- checkBox ----
        checkBox.setFont(new Font("\ub9d1\uc740 \uace0\ub515", Font.PLAIN, 14));
        checkBox.setBackground(Color.white);
        add(checkBox);
        checkBox.setBounds(330, 19, 21, 30);
        add(lblUserName);
        lblUserName.setBounds(70, 19, 190, 30);
        add(lblUserIcon);
        lblUserIcon.setBounds(20, 13, 40, 40);

        setPreferredSize(new Dimension(370, 65));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    public JCheckBox checkBox;
    public JLabel lblUserName;
    public JLabel lblUserIcon;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
