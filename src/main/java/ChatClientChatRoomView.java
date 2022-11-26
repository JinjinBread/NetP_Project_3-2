import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
/*
 * Created by JFormDesigner on Wed Nov 09 00:40:15 KST 2022
 */



/**
 * @author unknown
 */
public class ChatClientChatRoomView extends JFrame {
    @Serial
    private static final long serialVersionUID = 1L;
    //private ChatClientHome clientView = new ChatClientHome();

    private static final int BUF_LEN = 128;
    private String UserName;
    private FileDialog fd;
    private ChatClientMainView mainview;
    public int Room_Id; // 이 ChatRoom의 room_id
    public String UserList;
    private SimpleDateFormat dateFormat = new SimpleDateFormat(" aa kk:mm");

//    public ChatClientChatRoom(String user_name, String user_list, int room_id) {
//        this.UserName = user_name;
//        this.user_list = user_list;
//        this.room_id = room_id;
//
//    }

    public ChatClientChatRoomView(ChatClientMainView mainview, String userName, String user_list, int room_id) {
        //setVisible(true);
        initComponents();
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

    // Mouse Event 수신 처리
//    public void DoMouseEvent(ChatMsg cm) {
//        Color c;
//        if (cm.UserName.matches(UserName)) // 본인 것은 이미 Local 로 그렸다.
//            return;
//        c = new Color(255, 0, 0); // 다른 사람 것은 Red
//        gc2.setColor(c);
//        gc2.fillOval(cm.mouse_e.getX() - pen_size/2, cm.mouse_e.getY() - cm.pen_size/2, cm.pen_size, cm.pen_size);
//        gc.drawImage(panelImage, 0, 0, panel);
//    }
//
//    public void SendMouseEvent(MouseEvent e) {
//        ChatMsg cm = new ChatMsg(UserName, "500", "MOUSE");
//        cm.mouse_e = e;
//        cm.pen_size = pen_size;
//        SendObject(cm);
//    }
//
//    class MyMouseWheelEvent implements MouseWheelListener {
//        @Override
//        public void mouseWheelMoved(MouseWheelEvent e) {
//            // TODO Auto-generated method stub
//            if (e.getWheelRotation() < 0) { // 위로 올리는 경우 pen_size 증가
//                if (pen_size < 20)
//                    pen_size++;
//            } else {
//                if (pen_size > 2)
//                    pen_size--;
//            }
//            lblMouseEvent.setText("mouseWheelMoved Rotation=" + e.getWheelRotation()
//                    + " pen_size = " + pen_size + " " + e.getX() + "," + e.getY());
//
//        }
//
//    }
//    // Mouse Event Handler
//    class MyMouseEvent implements MouseListener, MouseMotionListener {
//        @Override
//        public void mouseDragged(MouseEvent e) {
//            lblMouseEvent.setText(e.getButton() + " mouseDragged " + e.getX() + "," + e.getY());// 좌표출력가능
//            Color c = new Color(0,0,255);
//            gc2.setColor(c);
//            gc2.fillOval(e.getX()-pen_size/2, e.getY()-pen_size/2, pen_size, pen_size);
//            // panelImnage는 paint()에서 이용한다.
//            gc.drawImage(panelImage, 0, 0, panel);
//            SendMouseEvent(e);
//        }
//
//        @Override
//        public void mouseMoved(MouseEvent e) {
//            lblMouseEvent.setText(e.getButton() + " mouseMoved " + e.getX() + "," + e.getY());
//        }
//
//        @Override
//        public void mouseClicked(MouseEvent e) {
//            lblMouseEvent.setText(e.getButton() + " mouseClicked " + e.getX() + "," + e.getY());
//            Color c = new Color(0,0,255);
//            gc2.setColor(c);
//            gc2.fillOval(e.getX()-pen_size/2, e.getY()-pen_size/2, pen_size, pen_size);
//            gc.drawImage(panelImage, 0, 0, panel);
//            SendMouseEvent(e);
//        }
//
//        @Override
//        public void mouseEntered(MouseEvent e) {
//            lblMouseEvent.setText(e.getButton() + " mouseEntered " + e.getX() + "," + e.getY());
//            // panel.setBackground(Color.YELLOW);
//
//        }
//
//        @Override
//        public void mouseExited(MouseEvent e) {
//            lblMouseEvent.setText(e.getButton() + " mouseExited " + e.getX() + "," + e.getY());
//            // panel.setBackground(Color.CYAN);
//
//        }
//
//        @Override
//        public void mousePressed(MouseEvent e) {
//            lblMouseEvent.setText(e.getButton() + " mousePressed " + e.getX() + "," + e.getY());
//
//        }
//
//        @Override
//        public void mouseReleased(MouseEvent e) {
//            lblMouseEvent.setText(e.getButton() + " mouseReleased " + e.getX() + "," + e.getY());
//            // 드래그중 멈출시 보임
//
//        }
//    }

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
                    ImageIcon img = new ImageIcon(fd.getDirectory() + fd.getFile());
                    obcm.img = img;
                    mainview.SendObject(obcm);
                }
            }
        }
    }

    // 프로필
    ImageIcon icon1 = new ImageIcon("resources/icon1.jpg");

    public void AppendIcon(ImageIcon icon) {
//        int len = textArea.getDocument().getLength();
//        // 끝으로 이동
//        textArea.setCaretPosition(len);
//        textArea.insertIcon(icon);

//        int radius = 30;
//        int margin = 5;
//        BufferedImage mask = new BufferedImage(2 * radius + (2 * margin), 2 * radius + (2 * margin), BufferedImage.TYPE_INT_ARGB);
//        Graphics2D g2d = mask.createGraphics();
//        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // 안티알리어싱 사용
//        //g2d.translate(mask.getWidth()/2, mask.getHeight()/2);
//        g2d.fillOval(0, 0, diameter - 1, diameter - 1);
//        g2d.dispose();
        // 프로필을 원형으로 자르기
        int width = 40;
        BufferedImage circleBuffer = new BufferedImage(width, width, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = circleBuffer.createGraphics();
        g2.setClip(new Ellipse2D.Float(0, 0, width, width));
        g2.drawImage(icon.getImage(), 0, 0, width, width, null);

        int len = textArea.getDocument().getLength();
        textArea.setCaretPosition(len);
        textArea.insertIcon(new ImageIcon(circleBuffer));
    }

    // 화면에 출력
    public void AppendTextL(String msg) {
        //textArea.append(msg + "\n");
        //AppendIcon(icon1);
        msg = msg.trim(); // 앞뒤 blank와 \n을 제거한다.

        StyledDocument doc = textArea.getStyledDocument();
        SimpleAttributeSet left = new SimpleAttributeSet();
        StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);
        StyleConstants.setForeground(left, Color.BLACK);
        doc.setParagraphAttributes(doc.getLength(), 1, left, false);
        Date cur = new Date();
        try {
            // JLabel ~ = new JLable;
            doc.insertString(doc.getLength(),msg + dateFormat.format(cur) + "\n", left);
            // string format이 힘드므로 채팅메시지를 받아다가 label로 만들어서 집어 넣는다.
            // insertComponent(JLabel);
        } catch (BadLocationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        int len = textArea.getDocument().getLength();
        textArea.setCaretPosition(len); // 맨 아래로 스크롤함
        //textArea.replaceSelection("\n");
    }
    // 화면 우측에 출력
    public void AppendTextR(String msg) {
        msg = msg.trim(); // 앞뒤 blank와 \n을 제거한다.
        StyledDocument doc = textArea.getStyledDocument();
        SimpleAttributeSet right = new SimpleAttributeSet();
        StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
        StyleConstants.setForeground(right, Color.BLACK);
        doc.setParagraphAttributes(doc.getLength(), 1, right, false);
        Date cur = new Date();
        String time = dateFormat.format(cur);
        try {
            doc.insertString(doc.getLength(), time + msg + "\n", right);
        } catch (BadLocationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        int len = textArea.getDocument().getLength();
        textArea.setCaretPosition(len);

        //textArea.replaceSelection("\n");

    }
    // 화면 중앙에 출력
    public void AppendTextC(String msg) {
        msg = msg.trim(); // 앞뒤 blank와 \n을 제거한다.
        StyledDocument doc = textArea.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        StyleConstants.setForeground(center, Color.BLUE);
        doc.setParagraphAttributes(doc.getLength(), 1, center, false);
        try {
            doc.insertString(doc.getLength(),msg+"\n", center );
        } catch (BadLocationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        int len = textArea.getDocument().getLength();
        textArea.setCaretPosition(len);
        //textArea.replaceSelection("\n");

    }

//    public void AppendImageL(ImageIcon ori_icon) {
//        //textArea.append(msg + "\n");
//        //AppendIcon(icon1);
//
//        StyledDocument doc = textArea.getStyledDocument();
//        SimpleAttributeSet left = new SimpleAttributeSet();
//        StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);
//        StyleConstants.setForeground(left, Color.BLACK);
//        doc.setParagraphAttributes(doc.getLength(), 1, left, false);
//        Date cur = new Date();
//        try {
//            // JLabel ~ = new JLable;
//            doc.insertString(doc.getLength(),msg + dateFormat.format(cur) + "\n", left);
//            // string format이 힘드므로 채팅메시지를 받아다가 label로 만들어서 집어 넣는다.
//            // insertComponent(JLabel);
//        } catch (BadLocationException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        int len = textArea.getDocument().getLength();
//        textArea.setCaretPosition(len); // 맨 아래로 스크롤함
//        //textArea.replaceSelection("\n");
//    }

    public void AppendImage(ImageIcon ori_icon) {
        int len = textArea.getDocument().getLength();
        textArea.setCaretPosition(len); // place caret at the end (with no selection)
        Image ori_img = ori_icon.getImage();
        Image new_img;
        ImageIcon new_icon;
        int width, height;
        double ratio;
        width = ori_icon.getIconWidth();
        height = ori_icon.getIconHeight();
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
            textArea.insertIcon(new_icon);
        } else {
            textArea.insertIcon(ori_icon);
            new_img = ori_img;
        }
        len = textArea.getDocument().getLength();
        textArea.setCaretPosition(len);
        textArea.replaceSelection("\n");
        // ImageViewAction viewaction = new ImageViewAction();
        // new_icon.addActionListener(viewaction); // 내부클래스로 액션 리스너를 상속받은 클래스로
        // panelImage = ori_img.getScaledInstance(panel.getWidth(), panel.getHeight(), Image.SCALE_DEFAULT);

//        gc2.drawImage(ori_img,  0,  0, panel.getWidth(), panel.getHeight(), panel);
//        gc.drawImage(panelImage, 0, 0, panel.getWidth(), panel.getHeight(), panel);
    }

    private void mouseEntered(MouseEvent e) {
        // TODO add your code here
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void mouseExited(MouseEvent e) {
        // TODO add your code here
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
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

                //---- textArea ----
                textArea.setBackground(new Color(0xbacee0));
                textArea.setBorder(null);
                textArea.setEnabled(false);
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
    private JPanel panel1;
    private JScrollPane scrollPane1;
    private JTextPane textArea;
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem participantsItem;
    private JMenuItem inviteItem;
    private JMenuItem exitItem;
    private JPanel panel2;
    private JScrollPane scrollPane2;
    private JTextArea txtInput;
    private JButton fileBtn;
    private JButton emoticonBtn;
    private JButton sendBtn;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
