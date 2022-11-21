import java.awt.*;
import javax.swing.*;
/*
 * Created by JFormDesigner on Mon Nov 21 22:32:36 KST 2022
 */



/**
 * @author unknown
 */
public class ChatPanel extends JPanel {
    public ChatPanel() {
        initComponents();
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
    private JLabel profile;
    private JLabel name;
    private JLabel chat;
    private JLabel time;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}