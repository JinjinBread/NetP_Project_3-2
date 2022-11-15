import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/*
 * Created by JFormDesigner on Tue Nov 15 12:13:01 KST 2022
 */



/**
 * @author unknown
 */
public class ChatClientChatList extends JFrame {
    public ChatClientChatList() {
        initComponents();
    }

    private void mouseEntered(MouseEvent e) {
        // TODO add your code here
    }

    private void mouseExited(MouseEvent e) {
        // TODO add your code here
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        panel1 = new JPanel();
        label1 = new JLabel();
        label2 = new JLabel();
        panel3 = new JPanel();
        label3 = new JLabel();
        label4 = new JLabel();
        panel2 = new JPanel();
        scrollPane1 = new JScrollPane();

        //======== this ========
        setResizable(false);
        var contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== panel1 ========
        {
            panel1.setBackground(new Color(0xececed));
            panel1.setLayout(null);

            //---- label1 ----
            label1.setIcon(new ImageIcon(getClass().getResource("/clicked_home.png")));
            panel1.add(label1);
            label1.setBounds(26, 38, 28, 28);

            //---- label2 ----
            label2.setIcon(new ImageIcon(getClass().getResource("/clicked_chatList.png")));
            panel1.add(label2);
            label2.setBounds(26, 95, 24, 24);

            {
                // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < panel1.getComponentCount(); i++) {
                    Rectangle bounds = panel1.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = panel1.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                panel1.setMinimumSize(preferredSize);
                panel1.setPreferredSize(preferredSize);
            }
        }
        contentPane.add(panel1);
        panel1.setBounds(-1, 0, 76, 573);

        //======== panel3 ========
        {
            panel3.setBackground(Color.white);
            panel3.setLayout(null);

            //---- label3 ----
            label3.setText("\ucc44\ud305");
            label3.setForeground(Color.black);
            label3.setFont(label3.getFont().deriveFont(Font.BOLD, 14f));
            panel3.add(label3);
            label3.setBounds(15, 38, 30, 30);

            //---- label4 ----
            label4.setIcon(new ImageIcon(getClass().getResource("/create_chatRoom.png")));
            panel3.add(label4);
            label4.setBounds(new Rectangle(new Point(275, 41), label4.getPreferredSize()));

            {
                // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < panel3.getComponentCount(); i++) {
                    Rectangle bounds = panel3.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = panel3.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                panel3.setMinimumSize(preferredSize);
                panel3.setPreferredSize(preferredSize);
            }
        }
        contentPane.add(panel3);
        panel3.setBounds(75, 0, 309, 80);

        //======== panel2 ========
        {
            panel2.setBackground(Color.white);
            panel2.setLayout(null);
            panel2.add(scrollPane1);
            scrollPane1.setBounds(0, 0, 309, 493);

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
        panel2.setBounds(75, 80, 309, 493);

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
    private JPanel panel1;
    private JLabel label1;
    private JLabel label2;
    private JPanel panel3;
    private JLabel label3;
    private JLabel label4;
    private JPanel panel2;
    private JScrollPane scrollPane1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
