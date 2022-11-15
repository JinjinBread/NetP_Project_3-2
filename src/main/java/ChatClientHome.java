import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Vector;
import javax.swing.*;
/*
 * Created by JFormDesigner on Sat Nov 12 00:56:04 KST 2022
 */



/**
 * @author unknown
 */
public class ChatClientHome extends JFrame {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final int BUF_LEN = 128;
    //private ChatClientChatRoom chatRoom = ChatClientChatRoom();
    private String UserName;
    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private SimpleDateFormat dateFormat = new SimpleDateFormat(" aa kk:mm");

    private List RoomID = new List(); // ChatRoomID를 저장하는 자료구조
    private String name;
    private String ip;
    private String port;
    public ChatClientHome(String name, String ip, String port) {
        this.name = name; this.ip = ip; this.port = port;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        setVisible(true);
        homeBtn.setContentAreaFilled(false); homeBtn.setFocusPainted(false);
        chatListBtn.setContentAreaFilled(false); chatListBtn.setFocusPainted(false);

        UserName = name;

        try {
            socket = new Socket(ip, Integer.parseInt(port));

            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.flush();
            ois = new ObjectInputStream(socket.getInputStream());

            ChatObject obcm = new ChatObject(UserName, "100", "Hello");
            SendObject(obcm);

        } catch (NumberFormatException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "connect error", "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);
        }
    }

    class ListenNetwork extends Thread {
        public void run() {
            while (true) {
                try {

                    Object obcm = null;
                    String msg = null;
                    ChatObject cm;
                    try {
                        obcm = ois.readObject();
                    } catch (ClassNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        break;
                    }
                    if (obcm == null)
                        break;
                    if (obcm instanceof ChatObject) {
                        cm = (ChatObject) obcm;
                        scrollPane1.add(new friendPanel(cm));
                        validate();
                        // 출력 format
                        //msg = String.format(" [%s]\n%s", cm.UserName, cm.data);
                    } // else
                        continue;
//                    switch (cm.code) {
//                        case "200": // chat message
//                            if (cm.UserName.equals(UserName))
//                                AppendTextR(msg); // 내 메세지는 우측에
//                            else if (cm.UserName.equals("SERVER"))
//                                AppendTextC(msg);
//                            else
//                                AppendTextL(msg);
//                            break;
//                        case "300": // Image 첨부
//                            if (cm.UserName.equals(UserName))
//                                AppendTextR("[" + cm.UserName + "]");
//                            else if (cm.UserName.equals("SERVER"))
//                                AppendTextC(msg);
//                            else
//                                AppendTextL("[" + cm.UserName + "]");
//                            AppendImage(cm.img);
//                            break;
//                        case "500": // Mouse Event 수신
//                            DoMouseEvent(cm);
//                            break;
//                    }
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "ois.readObject() error", "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);
                    try {
                        ois.close();
                        oos.close();
                        socket.close();

                        break;
                    } catch (Exception ee) {
                        break;
                    } // catch문 끝
                } // 바깥 catch문끝

            }
        }
    }

    // Server에게 network으로 전송
    public void SendMessage(String msg) {
        try {
            // dos.writeUTF(msg);
//			byte[] bb;
//			bb = MakePacket(msg);
//			dos.write(bb, 0, bb.length);
            ChatObject obcm = new ChatObject(UserName, "200", msg);
            oos.writeObject(obcm);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "oos.writeObject() error", "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);
            try {
                ois.close();
                oos.close();
                socket.close();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                System.exit(0);
            }
        }
    }

    public void SendObject(Object ob) { // 서버로 메세지를 보내는 메소드
        try {
            oos.writeObject(ob);
        } catch (IOException e) {
            // textArea.append("메세지 송신 에러!!\n");
            JOptionPane.showMessageDialog(null, "SendObject Error", "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mouseEntered(MouseEvent e) {
        // TODO add your code here
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void mouseExited(MouseEvent e) {
        // TODO add your code here
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    private void chatListBtnMouseClicked(MouseEvent e) {
        // TODO add your code here
        new ChatClientChatList(name, ip, port);
        setVisible(false);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        panel1 = new JPanel();
        homeBtn = new JButton();
        chatListBtn = new JButton();
        panel3 = new JPanel();
        label3 = new JLabel();
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

            //---- homeBtn ----
            homeBtn.setIcon(new ImageIcon(getClass().getResource("/clicked_home.png")));
            homeBtn.setBorder(null);
            homeBtn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    ChatClientHome.this.mouseEntered(e);
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    ChatClientHome.this.mouseExited(e);
                }
            });
            panel1.add(homeBtn);
            homeBtn.setBounds(18, 40, 40, 40);

            //---- chatListBtn ----
            chatListBtn.setBorder(null);
            chatListBtn.setIcon(new ImageIcon(getClass().getResource("/clicked_chatList.png")));
            chatListBtn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    chatListBtnMouseClicked(e);
                }
                @Override
                public void mouseEntered(MouseEvent e) {
                    ChatClientHome.this.mouseEntered(e);
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    ChatClientHome.this.mouseExited(e);
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
            label3.setText("\uce5c\uad6c");
            label3.setForeground(Color.black);
            label3.setFont(label3.getFont().deriveFont(Font.BOLD, 14f));
            panel3.add(label3);
            label3.setBounds(15, 38, 30, 30);

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
            panel2.setForeground(Color.white);
            panel2.setLayout(null);

            //======== scrollPane1 ========
            {
                scrollPane1.setBorder(null);
                scrollPane1.setBackground(Color.white);
                scrollPane1.setForeground(Color.white);
            }
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
    private JButton homeBtn;
    private JButton chatListBtn;
    private JPanel panel3;
    private JLabel label3;
    private JPanel panel2;
    private JScrollPane scrollPane1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
