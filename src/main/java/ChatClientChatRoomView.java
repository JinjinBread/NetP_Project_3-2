import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Vector;
/*
 * Created by JFormDesigner on Wed Nov 09 00:40:15 KST 2022
 */



/**
 * @author unknown
 */
public class ChatClientChatRoomView extends JFrame {
    @Serial
    private static final long serialVersionUID = 1L;
    private String UserName;
    private FileDialog fd;
    private ChatClientMainView mainview;
    public int Room_Id; // 이 ChatRoom의 room_id
    public String UserList;
    private SimpleDateFormat dateFormat = new SimpleDateFormat(" aa kk:mm");
    private Vector<LeftChatPanel> YourMsg = new Vector<>();

//    public ChatClientChatRoom(String user_name, String user_list, int room_id) {
//        this.UserName = user_name;
//        this.user_list = user_list;
//        this.room_id = room_id;
//
//    }

    public ChatClientChatRoomView(ChatClientMainView mainview, String userName, String user_list, int room_id) {
        //setVisible(true);
        initComponents();
        setLocationRelativeTo(mainview);
        menu.setIcon(new ImageIcon("resources/list.png"));
        fileBtn.setIcon(new ImageIcon("resources/file.png"));
        emoticonBtn.setIcon(new ImageIcon("resources/emoticon.png"));

        this.mainview = mainview;
        this.Room_Id = room_id;
        this.UserName = userName;
        this.UserList = user_list;

        menu.setContentAreaFilled(false); emoticonBtn.setContentAreaFilled(false); fileBtn.setContentAreaFilled(false);
        menu.setFocusPainted(false); emoticonBtn.setFocusPainted(false); fileBtn.setFocusPainted(false);

        menu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });

        exitItem.addActionListener(new ActionListener() { // 나가기 메뉴아이템
            @Override
            public void actionPerformed(ActionEvent e) {
                ChatObject obcm = new ChatObject(UserName, "400", "Bye");
                mainview.SendObject(obcm);
                System.exit(0);
            }
        });

        TextSendAction action = new TextSendAction();
        TextSendKeyAction keyAction = new TextSendKeyAction();
        sendBtn.addActionListener(action);
        txtInput.addKeyListener(keyAction);
        txtInput.requestFocus();
        ImageSendAction action2 = new ImageSendAction();
        fileBtn.addActionListener(action2);
    }

    // JTextArea는 ActionListener가 안되므로 마우스로 전송버튼을 누르는 것과 엔터치는 것을 쪼갬
    class TextSendAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Send button을 누르면
            if (e.getSource() == sendBtn) {
                String msg = null;
                // msg = String.format("[%s] %s\n", UserName, txtInput.getText());
                msg = txtInput.getText();
                ChatObject obcm = new ChatObject(UserName, "200", msg);
                obcm.userlist = UserList;
                obcm.room_id = Room_Id;
                mainview.SendObject(obcm);
                txtInput.setText(""); // 메세지를 보내고 나면 메세지 쓰는창을 비운다.
                txtInput.requestFocus(); // 메세지를 보내고 커서를 다시 텍스트 필드로 위치시킨다
            }
        }
    }

    // keyboard enter key 치면 서버로 전송
    class TextSendKeyAction extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            // Enter 키를 누르면
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                e.consume();
                String msg = null;
                // msg = String.format("[%s] %s\n", UserName, txtInput.getText());
                msg = txtInput.getText();
                ChatObject obcm = new ChatObject(UserName, "200", msg);
                obcm.userlist = UserList;
                obcm.room_id = Room_Id;
                mainview.SendObject(obcm);
                txtInput.setText(""); // 메세지를 보내고 나면 메세지 쓰는창을 비운다.
                txtInput.requestFocus(); // 메세지를 보내고 커서를 다시 텍스트 필드로 위치시킨다
            }
        }
    }

    class ImageSendAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // 액션 이벤트가 sendBtn일때 또는 textField 에세 Enter key 치면
            if (e.getSource() == fileBtn) {
                Frame frame = new Frame("이미지첨부");
                fd = new FileDialog(frame, "이미지 선택", FileDialog.LOAD);
                // frame.setVisible(true);
                // fd.setDirectory(".\\");
                fd.setVisible(true);
                // System.out.println(fd.getDirectory() + fd.getFile());
                if (fd.getDirectory().length() > 0 && fd.getFile().length() > 0) {
                    ChatObject obcm = new ChatObject(UserName, "300", "IMG");
                    obcm.userlist = UserList;
                    obcm.room_id = Room_Id;
                    ImageIcon img = new ImageIcon(fd.getDirectory() + fd.getFile());
                    obcm.imgData = img;
                    obcm.ori_imgData = img;
                    mainview.SendObject(obcm);
                }
            }
        }
    }

    public void ChangeFriendProfile(ChatObject cm) {
        // 프로필 변경을 위해 상대방의 메시지를 저장(YourMsg)
        for(LeftChatPanel yourMsg : YourMsg) {
            if (yourMsg.UserName.equals(cm.UserName)) {
                yourMsg.setProfile(cm);
            }
        }
        repaint();
    }

    // 화면에 출력
    public void AppendTextL(ChatObject cm) {
        LeftChatPanel chat = new LeftChatPanel(cm);
        YourMsg.add(chat);
        textArea.insertComponent(chat);
        textArea.replaceSelection("\n");
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }
    // 화면 우측에 출력
    public void AppendTextR(ChatObject cm) {
        RightChatPanel chat = new RightChatPanel(cm);
        textArea.insertComponent(chat);
        textArea.replaceSelection("\n");
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    public void AppendImageL(ChatObject cm) {
        //textArea.append(msg + "\n");
        //AppendIcon(icon1);

        Image ori_img = cm.imgData.getImage();
        Image new_img;
        ImageIcon new_icon;
        int width, height;
        double ratio;
        width = cm.imgData.getIconWidth();
        height = cm.imgData.getIconHeight();
        // Image가 너무 크면 최대 가로 또는 세로 200 기준으로 축소시킨다.
        if (width > 200 || height > 200) {
            if (width > height) { // 가로 사진
                ratio = (double) height / width;
                width = 200;
                height = (int) (width * ratio);
            } else { // 세로 사진
                ratio = (double) width / height;
                height = 200;
                width = (int) (height * ratio);
            }
            new_img = ori_img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            new_icon = new ImageIcon(new_img);
            cm.imgData = new_icon;
        }
        AppendTextL(cm);
    }

    public void AppendImageR(ChatObject cm) {
        //textArea.append(msg + "\n");
        //AppendIcon(icon1);

        Image ori_img = cm.ori_imgData.getImage();
        Image new_img;
        ImageIcon new_icon;
        int width, height;
        double ratio;
        width = cm.ori_imgData.getIconWidth();
        height = cm.ori_imgData.getIconHeight();
        // Image가 너무 크면 최대 가로 또는 세로 200 기준으로 축소시킨다.
        if (width > 200 || height > 200) {
            if (width > height) { // 가로 사진
                ratio = (double) height / width;
                width = 200;
                height = (int) (width * ratio);
            } else { // 세로 사진
                ratio = (double) width / height;
                height = 200;
                width = (int) (height * ratio);
            }
            new_img = ori_img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            new_icon = new ImageIcon(new_img);
            cm.imgData = new_icon;
        }
        AppendTextR(cm);
    }

    private void mouseEntered(MouseEvent e) {
        // TODO add your code here
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void mouseExited(MouseEvent e) {
        // TODO add your code here
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    private void emoticonBtnMouseClicked(MouseEvent e) {
        // TODO add your code here
        EmoticonDialog dialog = new EmoticonDialog(mainview, Room_Id, UserList);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        panel1 = new JPanel();
        scrollPane1 = new JScrollPane();
        textArea = new JTextPane();
        menuBar = new JMenuBar();
        menu = new JMenu();
        participantsItem = new JMenuItem();
        inviteItem = new JMenuItem();
        exitItem = new JMenuItem();
        panel2 = new JPanel();
        scrollPane2 = new JScrollPane();
        txtInput = new JTextArea();
        fileBtn = new JButton();
        emoticonBtn = new JButton();
        sendBtn = new JButton();

        //======== this ========
        setResizable(false);
        var contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== panel1 ========
        {
            panel1.setBackground(new Color(0xbacee0));
            panel1.setBorder(null);
            panel1.setLayout(null);

            //======== scrollPane1 ========
            {
                scrollPane1.setBorder(null);
                scrollPane1.setAutoscrolls(true);

                //---- textArea ----
                textArea.setBackground(new Color(0xbacee0));
                scrollPane1.setViewportView(textArea);
            }
            panel1.add(scrollPane1);
            scrollPane1.setBounds(9, 50, 365, 385);

            //======== menuBar ========
            {
                menuBar.setBorderPainted(false);
                menuBar.setBorder(null);
                menuBar.setBackground(new Color(0xbacee0));

                //======== menu ========
                {
                    menu.setIcon(null);
                    menu.setBorder(null);
                    menu.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseEntered(MouseEvent e) {
                            ChatClientChatRoomView.this.mouseEntered(e);
                        }
                        @Override
                        public void mouseExited(MouseEvent e) {
                            ChatClientChatRoomView.this.mouseExited(e);
                        }
                    });

                    //---- participantsItem ----
                    participantsItem.setText("Participants");
                    menu.add(participantsItem);

                    //---- inviteItem ----
                    inviteItem.setText("Invite");
                    menu.add(inviteItem);

                    //---- exitItem ----
                    exitItem.setText("Exit");
                    menu.add(exitItem);
                }
                menuBar.add(menu);
            }
            panel1.add(menuBar);
            menuBar.setBounds(341, 7, 30, 30);
        }
        contentPane.add(panel1);
        panel1.setBounds(0, 0, 383, 435);

        //======== panel2 ========
        {
            panel2.setBackground(Color.white);
            panel2.setBorder(null);
            panel2.setLayout(null);

            //======== scrollPane2 ========
            {
                scrollPane2.setBorder(null);

                //---- txtInput ----
                txtInput.setBackground(Color.white);
                txtInput.setLineWrap(true);
                scrollPane2.setViewportView(txtInput);
            }
            panel2.add(scrollPane2);
            scrollPane2.setBounds(11, 5, 360, 90);

            //---- fileBtn ----
            fileBtn.setIcon(null);
            fileBtn.setBorder(null);
            fileBtn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    ChatClientChatRoomView.this.mouseEntered(e);
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    ChatClientChatRoomView.this.mouseExited(e);
                }
            });
            panel2.add(fileBtn);
            fileBtn.setBounds(40, 103, 30, 30);

            //---- emoticonBtn ----
            emoticonBtn.setIcon(null);
            emoticonBtn.setBorder(null);
            emoticonBtn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    emoticonBtnMouseClicked(e);
                }
                @Override
                public void mouseEntered(MouseEvent e) {
                    ChatClientChatRoomView.this.mouseEntered(e);
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    ChatClientChatRoomView.this.mouseExited(e);
                }
            });
            panel2.add(emoticonBtn);
            emoticonBtn.setBounds(5, 103, 30, 30);

            //---- sendBtn ----
            sendBtn.setText("\uc804\uc1a1");
            sendBtn.setBackground(new Color(0xf2f2f2));
            panel2.add(sendBtn);
            sendBtn.setBounds(320, 103, 60, 30);
        }
        contentPane.add(panel2);
        panel2.setBounds(0, 435, 383, 138);

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
    public JPanel panel1;
    public JScrollPane scrollPane1;
    public JTextPane textArea;
    public JMenuBar menuBar;
    public JMenu menu;
    public JMenuItem participantsItem;
    public JMenuItem inviteItem;
    public JMenuItem exitItem;
    public JPanel panel2;
    public JScrollPane scrollPane2;
    public JTextArea txtInput;
    public JButton fileBtn;
    public JButton emoticonBtn;
    public JButton sendBtn;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
