import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
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
public class ChatClientChatRoom extends JFrame {
    @Serial
    private static final long serialVersionUID = 1L;

    private static final int BUF_LEN = 128;
    private String UserName;
    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private FileDialog fd;

    public ChatClientChatRoom(String name, String ip, String port) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        initComponents();

        listBtn.setContentAreaFilled(false); emoticonBtn.setContentAreaFilled(false); fileBtn.setContentAreaFilled(false);
        listBtn.setFocusPainted(false); emoticonBtn.setFocusPainted(false); fileBtn.setFocusPainted(false);

        AppendTextL("User " + name + " connecting " + ip + " " + port);
        UserName = name;

        try {
            socket = new Socket(ip, Integer.parseInt(port));

            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.flush();
            ois = new ObjectInputStream(socket.getInputStream());

            ChatObject obcm = new ChatObject(UserName, "100", "Hello");
            SendObject(obcm);

            ListenNetwork net = new ListenNetwork();
            net.start();
            TextSendAction action = new TextSendAction();
            sendBtn.addActionListener(action);
            txtInput.addActionListener(action); // Enter로도 event 받기 위해
            txtInput.requestFocus();
            ImageSendAction action2 = new ImageSendAction();
            fileBtn.addActionListener(action2);

        } catch (NumberFormatException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            AppendTextL("connect error");
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
                        msg = String.format("[%s]\n%s", cm.UserName, cm.data);
                    } else
                        continue;
                    switch (cm.code) {
                        case "200": // chat message
                            if (cm.UserName.equals(UserName))
                                AppendTextR(msg); // 내 메세지는 우측에
                            else
                                AppendTextL(msg);
                            break;
                        case "300": // Image 첨부
                            if (cm.UserName.equals(UserName))
                                AppendTextR("[" + cm.UserName + "]");
                            else
                                AppendTextL("[" + cm.UserName + "]");
                            AppendImage(cm.img);
                            break;
//                        case "500": // Mouse Event 수신
//                            DoMouseEvent(cm);
//                            break;
                    }
                } catch (IOException e) {
                    AppendTextL("ois.readObject() error");
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

    // keyboard enter key 치면 서버로 전송
    class TextSendAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Send button을 누르거나 메시지 입력하고 Enter key 치면
            if (e.getSource() == sendBtn || e.getSource() == txtInput) {
                String msg = null;
                // msg = String.format("[%s] %s\n", UserName, txtInput.getText());
                msg = txtInput.getText();
                SendMessage(msg);
                txtInput.setText(""); // 메세지를 보내고 나면 메세지 쓰는창을 비운다.
                txtInput.requestFocus(); // 메세지를 보내고 커서를 다시 텍스트 필드로 위치시킨다
                if (msg.contains("/exit")) // 종료 처리
                    System.exit(0);
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
                    SendObject(obcm);
                }
            }
        }
    }

    // 프로필
//    ImageIcon icon1 = new ImageIcon("src/icon1.jpg");
//
//    public void AppendIcon(ImageIcon icon) {
//        int len = textArea.getDocument().getLength();
//        // 끝으로 이동
//        textArea.setCaretPosition(len);
//        textArea.insertIcon(icon);
//    }

    // 화면에 출력
    public void AppendTextL(String msg) {
        // textArea.append(msg + "\n");
        // AppendIcon(icon1);
        msg = msg.trim(); // 앞뒤 blank와 \n을 제거한다.

        StyledDocument doc = textArea.getStyledDocument();
        SimpleAttributeSet left = new SimpleAttributeSet();
        StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);
        StyleConstants.setForeground(left, Color.BLACK);
        doc.setParagraphAttributes(doc.getLength(), 1, left, false);
        try {
            doc.insertString(doc.getLength(), msg+"\n", left );
        } catch (BadLocationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        int len = textArea.getDocument().getLength();
        textArea.setCaretPosition(len);
        //textArea.replaceSelection("\n");


    }
    // 화면 우측에 출력
    public void AppendTextR(String msg) {
        msg = msg.trim(); // 앞뒤 blank와 \n을 제거한다.
        StyledDocument doc = textArea.getStyledDocument();
        SimpleAttributeSet right = new SimpleAttributeSet();
        StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
        StyleConstants.setForeground(right, Color.BLUE);
        doc.setParagraphAttributes(doc.getLength(), 1, right, false);
        try {
            doc.insertString(doc.getLength(),msg+"\n", right );
        } catch (BadLocationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        int len = textArea.getDocument().getLength();
        textArea.setCaretPosition(len);
        //textArea.replaceSelection("\n");

    }

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

    // Windows 처럼 message 제외한 나머지 부분은 NULL 로 만들기 위한 함수
    public byte[] MakePacket(String msg) {
        byte[] packet = new byte[BUF_LEN];
        byte[] bb = null;
        int i;
        for (i = 0; i < BUF_LEN; i++)
            packet[i] = 0;
        try {
            bb = msg.getBytes("euc-kr");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.exit(0);
        }
        for (i = 0; i < bb.length; i++)
            packet[i] = bb[i];
        return packet;
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
            AppendTextL("oos.writeObject() error");
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
            AppendTextL("SendObject Error");
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

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        panel1 = new JPanel();
        listBtn = new JButton();
        scrollPane1 = new JScrollPane();
        textArea = new JTextPane();
        txtInput = new JTextField();
        panel2 = new JPanel();
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

            //---- listBtn ----
            listBtn.setIcon(new ImageIcon(getClass().getResource("/list.png")));
            listBtn.setBorder(null);
            listBtn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    ChatClientChatRoom.this.mouseEntered(e);
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    ChatClientChatRoom.this.mouseExited(e);
                }
            });
            panel1.add(listBtn);
            listBtn.setBounds(345, 8, 30, 30);

            //======== scrollPane1 ========
            {
                scrollPane1.setBorder(null);

                //---- textArea ----
                textArea.setBackground(new Color(0xbacee0));
                textArea.setBorder(null);
                scrollPane1.setViewportView(textArea);
            }
            panel1.add(scrollPane1);
            scrollPane1.setBounds(0, 50, 383, 385);

            //---- txtInput ----
            txtInput.setBackground(Color.white);
            txtInput.setBorder(null);
            panel1.add(txtInput);
            txtInput.setBounds(0, 435, 383, 100);

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
        panel1.setBounds(0, 0, 383, 535);

        //======== panel2 ========
        {
            panel2.setBackground(Color.white);
            panel2.setBorder(null);
            panel2.setLayout(null);

            //---- fileBtn ----
            fileBtn.setIcon(new ImageIcon(getClass().getResource("/file.png")));
            fileBtn.setBorder(null);
            fileBtn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    ChatClientChatRoom.this.mouseEntered(e);
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    ChatClientChatRoom.this.mouseExited(e);
                }
            });
            panel2.add(fileBtn);
            fileBtn.setBounds(40, 4, 30, 30);

            //---- emoticonBtn ----
            emoticonBtn.setIcon(new ImageIcon(getClass().getResource("/emoticon.png")));
            emoticonBtn.setBorder(null);
            emoticonBtn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    ChatClientChatRoom.this.mouseEntered(e);
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    ChatClientChatRoom.this.mouseExited(e);
                }
            });
            panel2.add(emoticonBtn);
            emoticonBtn.setBounds(4, 4, 30, 30);

            //---- sendBtn ----
            sendBtn.setText("\uc804\uc1a1");
            sendBtn.setBackground(new Color(0xf2f2f2));
            panel2.add(sendBtn);
            sendBtn.setBounds(320, 5, 60, 30);

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
        panel2.setBounds(0, 535, 383, 38);

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
    private JButton listBtn;
    private JScrollPane scrollPane1;
    private JTextPane textArea;
    private JTextField txtInput;
    private JPanel panel2;
    private JButton fileBtn;
    private JButton emoticonBtn;
    private JButton sendBtn;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
