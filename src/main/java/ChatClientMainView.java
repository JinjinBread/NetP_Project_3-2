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

public class ChatClientMainView extends JFrame {
    @Serial
    private static final long serialVersionUID = 1L;
    //private ChatClientChatRoom chatRoom = ChatClientChatRoom();
    private final ChatClientMainView mainview = this;
    //    private String UserName;
    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("aa hh:mm"); // 채팅이 올 때마다 다시 받아서 다시 그려줌

//    private String ip;
//    private String port;

    public String UserName;
    public String UserStatus;
    public String UserStatusMsg = "HSU 컴퓨터공학부";
    public ImageIcon UserIcon;

    private Vector<String> addedUserList = new Vector();
    private ImageIcon icon1 = new ImageIcon("resources/default_profile.jpg");
    //    private Vector<ChatRoomPanel> RoomPanelVec = new Vector<>(); // 현재 유저가 들어가있는 채팅방 패널을 관리(시간, 마지막 채팅 관리)
    //private Vector<ChatClientChatRoomView> RoomVec = new Vector(); // 현재 유저가 들어가있는 채팅방을관리
//    private ChatObject myObject = new ChatObject(); // 각 스레드의 객체
//    private int room; // 방 id
    public ChatClientMainView(String name, String ip, String port) {
//        this.name = name; this.ip = ip; this.port = port;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        homeBtn.setIcon(new ImageIcon("resources/clicked_home.png"));
        chatListBtn.setIcon(new ImageIcon("resources/clicked_chatList.png"));
        createRoomBtn.setIcon(new ImageIcon("resources/create_chatRoom.png"));
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

        homeBtn.setContentAreaFilled(false);
        homeBtn.setFocusPainted(false);
        chatListBtn.setContentAreaFilled(false);
        chatListBtn.setFocusPainted(false);
        createRoomBtn.setContentAreaFilled(false);
        createRoomBtn.setFocusPainted(false);

        UserName = name;

        try {
            socket = new Socket(ip, Integer.parseInt(port));

            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.flush();
            ois = new ObjectInputStream(socket.getInputStream());

            AddFriend(icon1, UserName, "O", UserStatusMsg);
            UserIcon = icon1;

            ChatObject obcm = new ChatObject(UserName, "100", "Login");
            obcm.status = "O";
            obcm.statusMsg = UserStatusMsg;
            obcm.img = UserIcon;
            SendObject(obcm);

            ListenNetwork net = new ListenNetwork();
            net.start();
        }
        catch (NumberFormatException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "connect error", "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Vector<FriendPanel> FriendVector = new Vector<>();
    public Vector<ChatRoomPanel> RoomVector = new Vector<>();

    public void AddFriend(ImageIcon icon, String name, String status, String statusMsg) {
        int len = homePane.getDocument().getLength();
        homePane.setCaretPosition(len);
        FriendPanel friend = new FriendPanel(mainview, icon, name, status, statusMsg);
        friend.UserStatusMsg = statusMsg;
        homePane.insertComponent(friend);
        FriendVector.add(friend);
        //homePane.setCaretPosition(0);
        repaint();
    }

    public FriendPanel SearchFriend(String name) {
        for (FriendPanel friend : FriendVector) {
            if (friend.UserName.equals(name))
                return friend;
        }
        return null; // 없음
    }

    public ImageIcon GetUserIcon(String user) {
        // Friend를 모아둔 벡터에서 username과 user가 같은 Friend 객체를 찾아서 그 객체의 UserIcon을 리턴
        for (FriendPanel friend : FriendVector) {
            if (friend.UserName.equals(user))
                return friend.UserIcon;
        }
        return null;
    }

    public void LoginNewFriend(ChatObject cm) { // 로그인
        for (FriendPanel friend : FriendVector) {
            if (friend.UserName.equals(cm.UserName)) { // ex) user1이 로그아웃한 후 다시 user1으로 로그인하면 offline 상태를 online으로 변경
                friend.setOnline(cm.status.equals("O")); // 의문: LoginNewFriend는 status가 O인 상태로만 오지 않나? 그냥 friend.setOnline(true)하면 안됨?
                return;
            }
        }
        AddFriend(cm.img, cm.UserName, cm.status, cm.statusMsg); // 완전히 새로운 유저
    }

    public void LogoutFriend(ChatObject cm) { // 로그아웃
        for (FriendPanel friend : FriendVector) {
            if (friend.UserName.equals(cm.UserName)) { // 친구 벡터에서 로그아웃 한 유저를 찾음
                friend.setOnline(false);
            }
        }
    }

    public void ChangeFriendProfile(ChatObject cm) { // 프로필 변경
        UserIcon = cm.img;
        for (FriendPanel friend : FriendVector) {
            if (friend.UserName.equals(cm.UserName)) {
                friend.setIcon(cm);
            }
        }
        for (ChatRoomPanel chatRoom : RoomVector) {
            //if ()
        }
    }

    public void AddChatRoom(ChatObject cm) { // 채팅방 생성
//        int len = chatPane.getDocument().getLength();
//        chatPane.setCaretPosition(len);
        ChatRoomPanel chatRoom = new ChatRoomPanel(mainview, cm.img, cm.UserName, cm.userlist, cm.room_id);
        chatPane.insertComponent(chatRoom);
        RoomVector.add(chatRoom);
//        chatPane.setCaretPosition(0);
        repaint();
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
                        e.printStackTrace();
                        break;
                    }
                    if (obcm == null)
                        break;
                    if (obcm instanceof ChatObject) {
                        cm = (ChatObject) obcm;
                        msg = String.format(" [%s]\n%s", cm.UserName, cm.data);
                    } else
                        continue;
                    switch (cm.code) {
                        case "100":
                            LoginNewFriend(cm);
                            break;
                        case "110": // 프로필 사진 변경
                            break;
                        case "120": // 상태 메시지 변경
                            break;
                        case "200": // chat message
//                            RoomPanelVec.get(n).room_id와 cm.room_id가 일치하는 panel의 profile, time, lastChat을 변경함.
                            SendObject(cm);
                            break;
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
                        case "400":
                            LogoutFriend(cm);
                            break;
                        case "510": // 채팅방 생성
                            AddChatRoom(cm);
//                            ChatClientChatRoomView chatRoom = new ChatClientChatRoomView(mainview, UserName, cm.userlist, cm.room_id); // 서버에서 넘어온 room_id로 채팅방 생성
//                            RoomVec.add(chatRoom); // 자신이 입장해있는 채팅방을 RoomVec에 add
//                            String[] users = cm.userlist.split(" "); // 현재 채팅방에 입장해있는 유저들
//                            String tempUserList = "";
//                            for(int i = 0; i < users.length; i++) {
//                                if (users[i].equals(UserName)) // '나'는 제외
//                                    continue;
//                                tempUserList += String.format("%s ", users[i]); // '나'를 제외한 채팅방에 참가한 유저들의 리스트만 보내기 위함 (채팅방 제목에 사용)
//                            }
//                            ChatRoomPanel tempRoomPanel = new ChatRoomPanel(chatRoom, tempUserList, cm.room_id); // 방 id
//                            RoomPanelVec.add(tempRoomPanel);
//                            chatPane.insertComponent(tempRoomPanel);
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

    public void AppendText(ChatObject cm) {

    }

    public void AppendImage(ChatObject cm) {

    }

    public void SendObject(ChatObject ob) { // 서버로 메세지를 보내는 메소드
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
        //chatListBtn.setEnabled(false);
        friendList.setVisible(false);
        chatList.setVisible(true);
        friendHeader.setVisible(false);
        chatListHeader.setVisible(true);
    }

//    private void showFriendListDialog(MouseEvent e) {
//        // TODO add your code here
//
//        SelectFriendDialog selectFriendDialog = new SelectFriendDialog(mainview, addedUserList);
//        selectFriendDialog.setVisible(true);
//    }

    private void homeBtnMouseClicked(MouseEvent e) { // 홈화면( == 친구 목록 화면)으로 이동
        // TODO add your code here
        friendList.setVisible(true);
        chatList.setVisible(false);
        friendHeader.setVisible(true);
        chatListHeader.setVisible(false);
    }

    private void createRoom(MouseEvent e) {
        // TODO add your code here
        SelectFriendDialog dialog = new SelectFriendDialog(mainview); // 아직 보여지진 않음
        String userlist = dialog.ShowDialog(); // 이때 보여짐
        dialog = null; // dialog 삭제

        if (userlist != null) { // 선택된 유저가 있다면,
            ChatObject obcm = new ChatObject(UserName, "510", "NEW ROOM");
            obcm.userlist = userlist;
            SendObject(obcm);
        }
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
            homeBtn.setIcon(null);
            homeBtn.setBorder(null);
            homeBtn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    homeBtnMouseClicked(e);
                }
                @Override
                public void mouseEntered(MouseEvent e) {
                    ChatClientMainView.this.mouseEntered(e);
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    ChatClientMainView.this.mouseExited(e);
                }
            });
            list.add(homeBtn);
            homeBtn.setBounds(18, 40, 40, 40);

            //---- chatListBtn ----
            chatListBtn.setBorder(null);
            chatListBtn.setIcon(null);
            chatListBtn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    chatListBtnMouseClicked(e);
                }
                @Override
                public void mouseEntered(MouseEvent e) {
                    ChatClientMainView.this.mouseEntered(e);
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    ChatClientMainView.this.mouseExited(e);
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
            createRoomBtn.setIcon(null);
            createRoomBtn.setBackground(Color.white);
            createRoomBtn.setBorder(null);
            createRoomBtn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    createRoom(e);
                }
                @Override
                public void mouseEntered(MouseEvent e) {
                    ChatClientMainView.this.mouseEntered(e);
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    ChatClientMainView.this.mouseExited(e);
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
                homePane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
                friend.setViewportView(homePane);
            }
            friendList.add(friend);
            friend.setBounds(0, 0, 309, 493);
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

                //---- chatPane ----
                chatPane.setEnabled(false);
                chatRoom.setViewportView(chatPane);
            }
            chatList.add(chatRoom);
            chatRoom.setBounds(0, 0, 309, 493);
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
