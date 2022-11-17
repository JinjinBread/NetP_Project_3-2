import java.awt.*;
import javax.swing.*;
/*
 * Created by JFormDesigner on Tue Nov 15 16:14:16 KST 2022
 */



/**
 * @author unknown
 */
public class FriendPanel extends JPanel {
    public FriendPanel(ChatObject cm) {
        initComponents();
        setVisible(true);
        this.name.setText(cm.UserName);
        this.status.setText(cm.status);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        name = new JLabel();
        status = new JLabel();

        //======== this ========
        setLayout(null);

        //---- name ----
        name.setFont(name.getFont().deriveFont(name.getFont().getStyle() | Font.BOLD, 14f));
        add(name);
        name.setBounds(50, 35, 60, 25);

        //---- status ----
        status.setFont(new Font("\ub9d1\uc740 \uace0\ub515", Font.PLAIN, 10));
        add(status);
        status.setBounds(50, 65, 60, 25);

        {
            // compute preferred size
            Dimension preferredSize = new Dimension();
            for(int i = 0; i < getComponentCount(); i++) {
                Rectangle bounds = getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            setMinimumSize(preferredSize);
            setPreferredSize(preferredSize);
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JLabel name;
    private JLabel status;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
