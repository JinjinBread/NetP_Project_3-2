import javax.swing.*;
import java.awt.*;
/*
 * Created by JFormDesigner on Wed Nov 09 00:40:15 KST 2022
 */



/**
 * @author unknown
 */
public class ChatClientView extends JFrame {
    public ChatClientView() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        panel2 = new JPanel();
        button1 = new JButton();
        button2 = new JButton();
        button3 = new JButton();
        textPane1 = new JTextPane();
        scrollPane1 = new JScrollPane();
        textArea1 = new JTextArea();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== panel2 ========
        {
            panel2.setLayout(null);

            //---- button1 ----
            button1.setText("\uc804\uc1a1");
            panel2.add(button1);
            button1.setBounds(new Rectangle(new Point(285, 80), button1.getPreferredSize()));

            //---- button2 ----
            button2.setIcon(new ImageIcon(getClass().getResource("/emoticon.png")));
            panel2.add(button2);
            button2.setBounds(5, 80, 30, button2.getPreferredSize().height);

            //---- button3 ----
            button3.setIcon(new ImageIcon(getClass().getResource("/file.png")));
            panel2.add(button3);
            button3.setBounds(45, 80, 30, 30);
            panel2.add(textPane1);
            textPane1.setBounds(0, 0, 370, 115);

            {
                // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < panel2.getComponentCount(); i++) {
                    Rectangle bounds = panel2.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = panel2.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                panel2.setMinimumSize(preferredSize);
                panel2.setPreferredSize(preferredSize);
            }
        }
        contentPane.add(panel2);
        panel2.setBounds(0, 435, 370, 120);

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(textArea1);
        }
        contentPane.add(scrollPane1);
        scrollPane1.setBounds(0, 0, 370, 435);

        {
            // compute preferred size
            Dimension preferredSize = new Dimension();
            for(int i = 0; i < contentPane.getComponentCount(); i++) {
                Rectangle bounds = contentPane.getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = contentPane.getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            contentPane.setMinimumSize(preferredSize);
            contentPane.setPreferredSize(preferredSize);
        }
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel panel2;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JTextPane textPane1;
    private JScrollPane scrollPane1;
    private JTextArea textArea1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
