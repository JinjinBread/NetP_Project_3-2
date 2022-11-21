import panel.FriendPanel;

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
    //private ChatClientChatRoom chatRoom = ChatClientChatRoom();
    private final ChatClientHome mainview = this;
    private String UserName;
    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("aa hh:mm"); // 채팅이 올 때마다 다시 받아서 다시 그려줌

    private String name;
    private String ip;
    private String port;
    private Vector<String> addedUserList = new Vector();
    private Vector<ChatRoom> RoomVec = new Vector(); // 현재 유저가 들어가있는 채팅방을 관리
    private String lastChat = ""; // 가장 마지막에 입력한 채팅
    private Vector<ChatRoomPanel> RoomPanelVec = new Vector<>(); // 현재 유저가 들어가있는 채팅방 패널을 관리(시간, 마지막 채팅 관리)
    private ChatObject myObject = new ChatObject();
//    private int room; // 방 id
    public ChatClientHome(String name, String ip, String port) {
        this.name = name; this.ip = ip; this.port = port;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();

//        try {
//            homePane.getDocument().insertString(homePane.getDocument().getLength(), " ", null);
//        } catch(BadLocationException ex1){
//            // Ignore
//        }
//        try {
//            homePane.setCaretPosition(homePane.getDocument().getLength()-1);
//        } catch(Exception ex){
//            homePane.setCaretPosition(0);
//        }

        chatListHeader.setVisible(false);
        chatList.setVisible(false);
        setVisible(true);
        homeBtn.setContentAreaFilled(false); homeBtn.setFocusPainted(false);
        chatListBtn.setContentAreaFilled(false); chatListBtn.setFocusPainted(false);
        createRoomBtn.setContentAreaFilled(false); createRoomBtn.setFocusPainted(false);

        UserName = name;
        //myObject.UserName = name;
        addedUserList.add(UserName);
        homePane.insertComponent(new FriendPanel(UserName));

        try {
            socket = new Socket(ip, Integer.parseInt(port));

            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.flush();
            ois = new ObjectInputStream(socket.getInputStream());

            ChatObject obcm = new ChatObject(UserName, "100", "Hello");
            SendObject(obcm);

            ChatClientHome.ListenNetwork net = new ChatClientHome.ListenNetwork();
            net.start();
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
                        // X 버튼을 누르고 강제 종료한 경우 친구 목록에서 실시간으로 빼는 작업 <-- 구현해야 함.
                        addedUserList.remove(UserName);
                        cm = new ChatObject(UserName, "400", "Force Shutdown");
                        SendObject(cm);
                        //e.printStackTrace();
                        break;
                    }
                    if (obcm == null)
                        break;
                    if (obcm instanceof ChatObject) {
                        cm = (ChatObject) obcm;
                        // chatRoom.setViewportView(new FriendPanel(cm));
                        // validate();
                        // 출력 format
                        msg = String.format(" [%s]\n%s", cm.UserName, cm.data);
                    } else
                        continue;
                    switch (cm.code) {
                        case "100":
                            String[] user = cm.userlist.split(" ");
                            for (int i = 0; i < user.length; i++) { // 문제. 이미 생성한 panel을 또 중복해서 생성하기 때문에 user가 중복돼서 나타남
                                if (user[i].equals(UserName) || addedUserList.contains(user[i])) // 이미 추가된 user와 자기 자신은 생성하지 않음.
                                    continue;
                                homePane.insertComponent(new FriendPanel(user[i]));
                                addedUserList.add(user[i]);
                            }
                            break;
                        case "200": // chat message
//                            RoomPanelVec.get(n).room_id와 cm.room_id가 일치하는 panel의 profile, time, lastChat을 변경함.
                            // 방 id와
//                            if (cm.UserName.equals(UserName))
//                                room.AppendTextR(msg); // 내 메세지는 우측에
//                            else if (cm.UserName.equals("SERVER"))
//                                room.AppendTextC(msg);
//                            else
//                                AppendTextL(msg);
//                            break;
                        case "300": // Image 첨부
//                            if (cm.UserName.equals(UserName))
//                                AppendTextR("[" + cm.UserName + "]");
//                            else if (cm.UserName.equals("SERVER"))
//                                AppendTextC(msg);
//                            else
//                                AppendTextL("[" + cm.UserName + "]");
//                            AppendImage(cm.img);
//                            break;
                        case "510":
                            ChatRoom temp = new ChatRoom(cm.room_id, cm.userlist);
                            RoomVec.add(temp); // 자신이 입장해있는 채팅방을 추가해줌
                            String[] users = cm.userlist.split(" ");
                            String tempUserList = "";
                            for(int i = 0; i < users.length; i++) {
                                if (users[i].equals(UserName))
                                    continue;
                                tempUserList += String.format("%s ", users[i]); // '나'를 제외한 채팅방에 참가한 유저들의 리스트만 보내기 위함 (채팅방 제목에 사용)
                            }
                            ChatRoomPanel tempRoomPanel = new ChatRoomPanel(mainview, tempUserList, cm.room_id);
                            RoomPanelVec.add(tempRoomPanel);
                            chatPane.insertComponent(tempRoomPanel);
                            break;
//                        case "500": // Mouse Event 수신
//                            DoMouseEvent(cm);
//                            break;
                    }
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
//    public void SendMessage(String msg, int room_id) {
//        try {
//            // dos.writeUTF(msg);
////			byte[] bb;
////			bb = MakePacket(msg);
////			dos.write(bb, 0, bb.length);
//            ChatObject obcm = new ChatObject(UserName, "200", msg, room_id);
//            oos.writeObject(obcm);
//        } catch (IOException e) {
//            JOptionPane.showMessageDialog(null, "oos.writeObject() error", "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);
//            try {
//                ois.close();
//                oos.close();
//                socket.close();
//            } catch (IOException e1) {
//                // TODO Auto-generated catch block
//                e1.printStackTrace();
//                System.exit(0);
//            }
//        }
//    }

    public void SendObject(Object ob) { // 서버로 메세지를 보내는 메소드
        try {
            oos.writeObject(ob);
        } catch (IOException e) {
            // textArea.append("메세지 송신 에러!!\n");
            JOptionPane.showMessageDialog(null, "SendObject Error", "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);
        }
    }

//    public void setRoomID(int room) {
//        this.room = room;
//    }
    public String getUserName() {
        return UserName;
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
        //chatListBtn.setEnabled(false);
        friendList.setVisible(false);
        chatList.setVisible(true);
        friendHeader.setVisible(false);
        chatListHeader.setVisible(true);
    }

    private void createRoom(MouseEvent e) {
        // TODO add your code here
        FriendListDialog friendListDialog = new FriendListDialog(mainview, addedUserList);
        friendListDialog.setVisible(true);
    }

    private void homeBtnMouseClicked(MouseEvent e) { // 홈화면( == 친구 목록 화면)으로 이동
        // TODO add your code here
        friendList.setVisible(true);
        chatList.setVisible(false);
        friendHeader.setVisible(true);
        chatListHeader.setVisible(false);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        list = new JPanel();
        homeBtn = new JButton();
        chatListBtn = new JButton();
        friendHeader = new JPanel();
        label3 = new JLabel();
        chatListHeader = new JPanel();
        label4 = new JLabel();
        createRoomBtn = new JButton();
        friendList = new JPanel();
        friend = new JScrollPane();
        homePane = new JTextPane();
        chatList = new JPanel();
        chatRoom = new JScrollPane();
        chatPane = new JTextPane();

        //======== this ========
        setResizable(false);
        var contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== list ========
        {
            list.setBackground(new Color(0xececed));
            list.setLayout(null);

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
                    ChatClientHome.this.mouseEntered(e);
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    ChatClientHome.this.mouseExited(e);
                }
            });
            list.add(homeBtn);
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
            list.add(chatListBtn);
            chatListBtn.setBounds(18, 125, 40, 40);

            {
                // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < list.getComponentCount(); i++) {
                    Rectangle bounds = list.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = list.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                list.setMinimumSize(preferredSize);
                list.setPreferredSize(preferredSize);
            }
        }
        contentPane.add(list);
        list.setBounds(-1, 0, 76, 573);

        //======== friendHeader ========
        {
            friendHeader.setBackground(Color.white);
            friendHeader.setLayout(null);

            //---- label3 ----
            label3.setText("\uce5c\uad6c");
            label3.setForeground(Color.black);
            label3.setFont(label3.getFont().deriveFont(Font.BOLD, 14f));
            friendHeader.add(label3);
            label3.setBounds(15, 38, 30, 30);

            {
                // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < friendHeader.getComponentCount(); i++) {
                    Rectangle bounds = friendHeader.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = friendHeader.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                friendHeader.setMinimumSize(preferredSize);
                friendHeader.setPreferredSize(preferredSize);
            }
        }
        contentPane.add(friendHeader);
        friendHeader.setBounds(75, 0, 309, 80);

        //======== chatListHeader ========
        {
            chatListHeader.setBackground(Color.white);
            chatListHeader.setLayout(null);

            //---- label4 ----
            label4.setText("\ucc44\ud305");
            label4.setForeground(Color.black);
            label4.setFont(label4.getFont().deriveFont(Font.BOLD, 14f));
            chatListHeader.add(label4);
            label4.setBounds(15, 38, 30, 30);

            //---- createRoomBtn ----
            createRoomBtn.setIcon(new ImageIcon(getClass().getResource("/create_chatRoom.png")));
            createRoomBtn.setBackground(Color.white);
            createRoomBtn.setBorder(null);
            createRoomBtn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    createRoom(e);
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
            chatListHeader.add(createRoomBtn);
            createRoomBtn.setBounds(250, 20, 50, 50);

            {
                // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < chatListHeader.getComponentCount(); i++) {
                    Rectangle bounds = chatListHeader.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = chatListHeader.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                chatListHeader.setMinimumSize(preferredSize);
                chatListHeader.setPreferredSize(preferredSize);
            }
        }
        contentPane.add(chatListHeader);
        chatListHeader.setBounds(75, 0, 309, 80);

        //======== friendList ========
        {
            friendList.setBackground(Color.white);
            friendList.setForeground(Color.white);
            friendList.setLayout(null);

            //======== friend ========
            {
                friend.setBorder(null);

                //---- homePane ----
                homePane.setEnabled(false);
                friend.setViewportView(homePane);
            }
            friendList.add(friend);
            friend.setBounds(0, 0, 309, 493);

            {
                // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < friendList.getComponentCount(); i++) {
                    Rectangle bounds = friendList.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = friendList.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                friendList.setMinimumSize(preferredSize);
                friendList.setPreferredSize(preferredSize);
            }
        }
        contentPane.add(friendList);
        friendList.setBounds(75, 80, 309, 493);

        //======== chatList ========
        {
            chatList.setBackground(Color.white);
            chatList.setForeground(Color.white);
            chatList.setLayout(null);

            //======== chatRoom ========
            {
                chatRoom.setEnabled(false);
                chatRoom.setBorder(null);
                chatRoom.setViewportView(chatPane);
            }
            chatList.add(chatRoom);
            chatRoom.setBounds(0, 0, 309, 493);

            {
                // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < chatList.getComponentCount(); i++) {
                    Rectangle bounds = chatList.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = chatList.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                chatList.setMinimumSize(preferredSize);
                chatList.setPreferredSize(preferredSize);
            }
        }
        contentPane.add(chatList);
        chatList.setBounds(75, 80, 309, 493);

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
    private JPanel list;
    private JButton homeBtn;
    private JButton chatListBtn;
    private JPanel friendHeader;
    private JLabel label3;
    private JPanel chatListHeader;
    private JLabel label4;
    private JButton createRoomBtn;
    private JPanel friendList;
    private JScrollPane friend;
    private JTextPane homePane;
    private JPanel chatList;
    private JScrollPane chatRoom;
    private JTextPane chatPane;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
