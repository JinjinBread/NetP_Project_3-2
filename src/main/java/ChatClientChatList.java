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
    private String name;
    private String ip;
    private String port;

    public ChatClientChatList(String name, String ip, String port) {
        this.name = name; this.ip = ip; this.port = port;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        setVisible(true);
        homeBtn.setContentAreaFilled(false); homeBtn.setFocusPainted(false);
        chatListBtn.setContentAreaFilled(false); chatListBtn.setFocusPainted(false);
        createRoomBtn.setContentAreaFilled(false); createRoomBtn.setFocusPainted(false);
    }

    private void mouseEntered(MouseEvent e) {
        // TODO add your code here
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void mouseExited(MouseEvent e) {
        // TODO add your code here
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    private void homeBtnMouseClicked(MouseEvent e) {
        new ChatClientHome(name, ip, port);
        setVisible(false);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        panel1 = new JPanel();
        homeBtn = new JButton();
        chatListBtn = new JButton();
        panel3 = new JPanel();
        label3 = new JLabel();
        createRoomBtn = new JButton();
        scrollPane1 = new JScrollPane();
        ListFrame = new JPanel();

        //======== this ========
        setResizable(false);
        var contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== panel1 ========
        {
            panel1.setBackground(new Color(0xececed));
            panel1.setLayout(null);

            //---- homeBtn ----
            homeBtn.setIcon(new ImageIcon(getClass().getResource("/clicked_home.png")));
            homeBtn.setBorder(null);
            homeBtn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    homeBtnMouseClicked(e);
                }
                @Override
                public void mouseEntered(MouseEvent e) {
                    ChatClientChatList.this.mouseEntered(e);
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    ChatClientChatList.this.mouseExited(e);
                }
            });
            panel1.add(homeBtn);
            homeBtn.setBounds(18, 40, 40, 40);

            //---- chatListBtn ----
            chatListBtn.setBorder(null);
            chatListBtn.setIcon(new ImageIcon(getClass().getResource("/clicked_chatList.png")));
            chatListBtn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    ChatClientChatList.this.mouseEntered(e);
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    ChatClientChatList.this.mouseExited(e);
                }
            });
            panel1.add(chatListBtn);
            chatListBtn.setBounds(18, 125, 40, 40);

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

            //---- createRoomBtn ----
            createRoomBtn.setIcon(new ImageIcon(getClass().getResource("/create_chatRoom.png")));
            createRoomBtn.setBackground(Color.white);
            createRoomBtn.setBorder(null);
            createRoomBtn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    ChatClientChatList.this.mouseEntered(e);
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    ChatClientChatList.this.mouseExited(e);
                }
            });
            panel3.add(createRoomBtn);
            createRoomBtn.setBounds(250, 20, 50, 50);

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

        //======== scrollPane1 ========
        {
            scrollPane1.setBackground(Color.white);
            scrollPane1.setBorder(null);

            //======== ListFrame ========
            {
                ListFrame.setBackground(Color.white);
                ListFrame.setLayout(new GridLayout());
            }
            scrollPane1.setViewportView(ListFrame);
        }
        contentPane.add(scrollPane1);
        scrollPane1.setBounds(75, 80, 308, 492);

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
    private JButton homeBtn;
    private JButton chatListBtn;
    private JPanel panel3;
    private JLabel label3;
    private JButton createRoomBtn;
    private JScrollPane scrollPane1;
    private JPanel ListFrame;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
