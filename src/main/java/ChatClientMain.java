import javax.swing.*;
import java.awt.*;
/*
 * Created by JFormDesigner on Wed Nov 09 00:12:57 KST 2022
 */



/**
 * @author unknown
 */
public class ChatClientMain extends JFrame {
    public ChatClientMain() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        this2 = new JFrame();
        textField4 = new JTextField();
        textField5 = new JTextField();
        textField6 = new JTextField();
        connect2 = new JButton();

        //======== this2 ========
        {
            var this2ContentPane = this2.getContentPane();
            this2ContentPane.setLayout(null);

            //---- textField4 ----
            textField4.setText("UserName");
            this2ContentPane.add(textField4);
            textField4.setBounds(90, 285, 185, 30);

            //---- textField5 ----
            textField5.setText("IP Address");
            this2ContentPane.add(textField5);
            textField5.setBounds(90, 325, 185, 30);

            //---- textField6 ----
            textField6.setText("Port Number");
            this2ContentPane.add(textField6);
            textField6.setBounds(90, 365, 185, 30);

            //---- connect2 ----
            connect2.setText("Connect");
            this2ContentPane.add(connect2);
            connect2.setBounds(140, 435, 79, 30);

            {
                // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < this2ContentPane.getComponentCount(); i++) {
                    Rectangle bounds = this2ContentPane.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = this2ContentPane.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                this2ContentPane.setMinimumSize(preferredSize);
                this2ContentPane.setPreferredSize(preferredSize);
            }
            this2.pack();
            this2.setLocationRelativeTo(this2.getOwner());
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JFrame this2;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JButton connect2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
