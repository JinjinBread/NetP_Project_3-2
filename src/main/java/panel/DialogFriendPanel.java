package panel;

import java.awt.*;
import javax.swing.*;
/*
 * Created by JFormDesigner on Sun Nov 20 23:50:31 KST 2022
 */



/**
 * @author unknown
 */
public class DialogFriendPanel extends JPanel {
    public DialogFriendPanel(String name) {
        initComponents();
        this.friend.setText(name);

    }

    public boolean isSelected() {
        return friend.isSelected();
    }

    public String getName() {
        return this.friend.getText();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        friend = new JCheckBox();

        //======== this ========
        setBackground(Color.white);
        setLayout(null);

        //---- friend ----
        friend.setFont(new Font("\ub9d1\uc740 \uace0\ub515", Font.PLAIN, 14));
        friend.setBackground(Color.white);
        add(friend);
        friend.setBounds(20, 10, 330, 30);

        setPreferredSize(new Dimension(370, 50));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JCheckBox friend;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
