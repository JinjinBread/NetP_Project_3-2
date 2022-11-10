import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import javax.swing.border.*;
/*
 * Created by JFormDesigner on Wed Nov 09 00:40:15 KST 2022
 */



/**
 * @author unknown
 */
public class ChatClientView extends JFrame {
    private static final int BUF_LEN = 128;
    private String UserName;
    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private FileDialog fd;

    public ChatClientView(String name, String ip, String port) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        initComponents();

        listBtn.setContentAreaFilled(false); emoticonBtn.setContentAreaFilled(false); fileBtn.setContentAreaFilled(false);
        listBtn.setFocusPainted(false); emoticonBtn.setFocusPainted(false); fileBtn.setFocusPainted(false);

        AppendText("User " + name + " connecting " + ip + " " + port);
        UserName = name;

        try {
            socket = new Socket(ip, Integer.parseInt(port));

            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.flush();
            ois = new ObjectInputStream(socket.getInputStream());

            ChatObject obcm = new ChatObject(UserName, "100", "Hello");
            SendChatMsg(obcm);

            ListenNetwork net = new ListenNetwork();
            net.start();
            TextSendAction action = new TextSendAction();
            sendBtn.addActionListener(action);
            txtInput.addActionListener(action);
            txtInput.requestFocus();
            ImageSendAction action2 = new ImageSendAction();
            fileBtn.addActionListener(action2);

        } catch (NumberFormatException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            //AppendText("connect error");
        }
    }

    public ChatObject ReadChatMsg() {
        Object obj = null;
        String msg = null;
        ChatObject cm = new ChatObject("", "", "");
        // Android와 호환성을 위해 각각의 Field를 따로따로 읽는다.

        try {
            obj = ois.readObject();
            cm.code = (String) obj;
            obj = ois.readObject();
            cm.UserName = (String) obj;
            obj = ois.readObject();
            cm.data = (String) obj;
            if (cm.code.equals("300")) {
                obj = ois.readObject();
                cm.imgbytes = (byte[]) obj;
            }
        } catch (ClassNotFoundException | IOException e) {
            // TODO Auto-generated catch block
            AppendText("ReadChatMsg Error");
            e.printStackTrace();
            try {
                oos.close();
                socket.close();
                ois.close();
                socket = null;
                return null;
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                try {
                    oos.close();
                    socket.close();
                    ois.close();
                } catch (IOException e2) {
                    // TODO Auto-generated catch block
                    e2.printStackTrace();
                }

                socket = null;
                return null;
            }

            // textArea.append("메세지 송신 에러!!\n");
            // System.exit(0);
        }


        return cm;
    }
    class ListenNetwork extends Thread {
        public void run() {
            while (true) {
                ChatObject cm = ReadChatMsg();
                if (cm==null)
                    break;
                if (socket == null)
                    break;
                String msg;
                msg = String.format("[%s] %s", cm.UserName, cm.data);
                switch (cm.code) {
                    case "200": // chat message
                        AppendText(msg);
                        break;
                    case "300": // Image 첨부
                        AppendText("[" + cm.UserName + "]" + " " + cm.data);
                        //AppendImage(cm.img);
                        AppendImageBytes(cm.imgbytes);

                        break;
                }

            }
        }
    }

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
                JFrame frame = new JFrame("이미지첨부");
                fd = new FileDialog(frame, "이미지 선택", FileDialog.LOAD);
                fd.setVisible(true);
                // System.out.println(fd.getDirectory() + fd.getFile());
                if (fd.getDirectory().length() > 0 && fd.getFile().length() > 0) {
                    ChatObject obcm = new ChatObject(UserName, "300", fd.getFile());
//					ImageIcon img = new ImageIcon(fd.getDirectory() + fd.getFile());
//					obcm.img = img;
//					SendChatMsg(obcm);

                    BufferedImage bImage = null;
                    String filename = fd.getDirectory() + fd.getFile();
                    try {
                        bImage = ImageIO.read(new File(filename));
                    } catch (IOException e2) {
                        // TODO Auto-generated catch block
                        e2.printStackTrace();
                    }
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    try {
                        ImageIO.write(bImage, "jpg", bos);
                        byte[] data = bos.toByteArray();
                        bos.close();
                        obcm.imgbytes = data;
                        //AppendImageBytes(data);

                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    SendChatMsg(obcm);
                }
            }
        }
    }

    ImageIcon icon1 = new ImageIcon("src/icon1.jpg");

    public void AppendIcon(ImageIcon icon) {
        int len = textArea.getDocument().getLength();
        // 끝으로 이동
        textArea.setCaretPosition(len);
        textArea.insertIcon(icon);
    }

    // 화면에 출력
    public void AppendText(String msg) {
        // textArea.append(msg + "\n");
        // AppendIcon(icon1);
        msg = msg.trim(); // 앞뒤 blank와 \n을 제거한다.
        int len = textArea.getDocument().getLength();
        // 끝으로 이동
        textArea.setCaretPosition(len);
        textArea.replaceSelection(msg + "\n");
    }

    public void AppendImage(ImageIcon ori_icon) {
        int len = textArea.getDocument().getLength();
        textArea.setCaretPosition(len); // place caret at the end (with no selection)
        Image ori_img = ori_icon.getImage();
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
            Image new_img = ori_img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            ImageIcon new_icon = new ImageIcon(new_img);
            textArea.insertIcon(new_icon);

        } else
            textArea.insertIcon(ori_icon);
        len = textArea.getDocument().getLength();
        textArea.setCaretPosition(len);
        textArea.replaceSelection("\n");
    }

    public void AppendImageBytes(byte[] imgbytes) {
        ByteArrayInputStream bis = new ByteArrayInputStream(imgbytes);
        BufferedImage ori_img = null;
        try {
            ori_img = ImageIO.read(bis);
            bis.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ImageIcon new_icon = new ImageIcon(ori_img);
        AppendImage(new_icon);
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
        ChatObject obcm = new ChatObject(UserName, "200", msg);
        SendChatMsg(obcm);
    }

    // 하나의 Message 보내는 함수
    // Android와 호환성을 위해 code, UserName, data 모드 각각 전송한다.
    public void SendChatMsg(ChatObject obj) {
        try {
            oos.writeObject(obj.code);
            oos.writeObject(obj.UserName);
            oos.writeObject(obj.data);
            if (obj.code.equals("300")) { // 이미지 첨부 있는 경우
                oos.writeObject(obj.imgbytes);
            }
            oos.flush();
        } catch (IOException e) {
            AppendText("SendChatMsg Error");
            e.printStackTrace();
            try {
                oos.close();
                socket.close();
                ois.close();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

            // textArea.append("메세지 송신 에러!!\n");
            // System.exit(0);
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
                    ChatClientView.this.mouseEntered(e);
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    ChatClientView.this.mouseExited(e);
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
                    ChatClientView.this.mouseEntered(e);
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    ChatClientView.this.mouseExited(e);
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
                    ChatClientView.this.mouseEntered(e);
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    ChatClientView.this.mouseExited(e);
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
