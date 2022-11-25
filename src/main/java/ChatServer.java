import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
/*
 * Created by JFormDesigner on Thu Nov 10 13:39:34 KST 2022
 */



/**
 * @author unknown
 */
public class ChatServer extends JFrame {
    @Serial
    private static final long serialVersionUID = 1L;
    private ServerSocket socket;
    private Socket client_socket;
    private Vector<UserService> UserVec = new Vector();
    private String UserList = "";
    private int RoomID = 0;
    private Vector<ChatRoomPanel> RoomVec = new Vector(); // ChatRoom 삭제 후 Vector<String>으로 변경
    public Vector<LoggedUser> LoggedUserVector = new Vector<>();

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ChatServer frame = new ChatServer();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public ChatServer() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    socket = new ServerSocket(Integer.parseInt(port.getText()));
                } catch (NumberFormatException | IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                AppendText("Chat Server Running..");
                startBtn.setText("Chat Server Running..");
                startBtn.setEnabled(false); // 서버를 더이상 실행시키지 못 하게 막는다
                port.setEnabled(false); // 더이상 포트번호 수정못 하게 막는다
                AcceptServer accept_server = new AcceptServer();
                accept_server.start();
            }
        });

        // 데이터베이스
//        Class.forName("oracle.jdbc.driver.OracleDriver");

    }

    // 새로운 참가자 accept() 하고 user thread를 새로 생성한다.
    class AcceptServer extends Thread {
        @SuppressWarnings("unchecked")
        public void run() {
            while (true) { // 사용자 접속을 계속해서 받기 위해 while문
                try {
                    AppendText("Waiting new clients ...");
                    client_socket = socket.accept(); // accept가 일어나기 전까지는 무한 대기중
                    AppendText("새로운 참가자 from " + client_socket);
                    // User 당 하나씩 Thread 생성
                    UserService new_user = new UserService(client_socket);
                    UserVec.add(new_user); // 새로운 참가자 배열에 추가
                    new_user.start(); // 만든 객체의 스레드 실행
                    AppendText("현재 참가자 수 " + UserVec.size());
                } catch (IOException e) {
                    AppendText("accept() error");
                    // System.exit(0);
                }
            }
        }
    }

    public void AppendText(String str) {
        textArea.append(str + "\n");
        textArea.setCaretPosition(textArea.getText().length());
    }

    public void AppendObject(ChatObject msg) {
        textArea.append("code = " + msg.code + "\n");
        textArea.append("id = " + msg.UserName + "\n");
        textArea.append("data = " + msg.data + "\n");
        textArea.setCaretPosition(textArea.getText().length());
    }

    class LoggedUser {
        public String UserName;
        public String UserStatus;
        public String UserStatusMsg;
        public UserService user_svc;
        public ImageIcon Icon;
        public LoggedUser(ImageIcon icon, String UserName, String UserStatus, String UserStatusMsg, UserService user_svc) {
            this.Icon = icon;
            this.UserName = UserName;
            this.UserStatus = UserStatus;
            this.UserStatusMsg = UserStatusMsg;
            this.user_svc = user_svc;
        }
    }

    public LoggedUser SearchLoggedUser(String name) {
        for(LoggedUser loggedUser: LoggedUserVector) {
            if (loggedUser.UserName.equals(name)) {
                return loggedUser;
            }
        }
        return null;
    }

    // User 당 생성되는 Thread
    // Read One 에서 대기 -> Write All
    class UserService extends Thread {
        private ObjectInputStream ois;
        private ObjectOutputStream oos;

        private Socket client_socket;
        private Vector user_vc;
        public String UserName = "";
        public String UserStatus;
        public String UserStatusMsg;

        public UserService(Socket client_socket) {
            // TODO Auto-generated constructor stub
            // 매개변수로 넘어온 자료 저장
            this.client_socket = client_socket;
            this.user_vc = UserVec;
            try {
                oos = new ObjectOutputStream(client_socket.getOutputStream());
                oos.flush();
                ois = new ObjectInputStream(client_socket.getInputStream());
            } catch (Exception e) {
                AppendText("userService error");
            }
        }

        public void Login(ChatObject cm) {
            AppendText("새로운 참가자 " + UserName + " 입장.");
//            WriteOne("Welcome to Java chat server\n");
//            WriteOne(UserName + "님 환영합니다.\n"); // 연결된 사용자에게 정상접속을 알림
//            String msg = "[" + UserName + "]님이 입장 하였습니다.\n";
//            WriteOthers(msg); // 아직 user_vc에 새로 입장한 user는 포함되지 않았다.
            LoggedUser u;
            if ((u = SearchLoggedUser(cm.UserName)) != null) { // 이미 로그인한 적이 있는 클라이언트
                u.UserStatus = "O";
                u.UserStatusMsg = cm.statusMsg;
                u.user_svc = this;
                // (ex. 다시 재로그인한 user1. user1이 마지막으로 설정한 profile 사진을 보내줌);
                ChatObject ob = new ChatObject(cm.UserName, "110", "OLDPROFILE");
                WriteOneObject(ob);
            }
            else { // 처음 들어온 유저
                u = new LoggedUser(cm.img, cm.UserName, "O", cm.statusMsg, this);
                LoggedUserVector.add(u);
            }
            WriteOthersObject(cm); // 다른 유저들에게 로그인 사실을 알림
            // 지금 로그인한 유저에게는 기존의 유저 목록을 보내준다. 기존에 있던 유저들을 마치 지금 로그인하는 것처럼 보내줌
            for (LoggedUser loggedUser: LoggedUserVector) {
                if (loggedUser.UserName.equals(UserName)) // 나 자신은 보내지 않음
                    continue;
                ChatObject ob = new ChatObject(loggedUser.UserName, "100", "Login");
                ob.status = loggedUser.UserStatus;
                ob.statusMsg = loggedUser.UserStatusMsg;
                ob.img = loggedUser.Icon;
                WriteOneObject(ob);
            }

            for (ChatRoomPanel room : RoomVec) {
                if (room.UserList.contains(UserName)) { // 각 채팅방의 유저리스트에서 내가 있으면(즉, 내가 들어가있는 채팅방이면)
                    ChatObject obcm = new ChatObject(UserName, "510", "NEW ROOM");
                    obcm.room_id = room.Room_Id;
                    obcm.userlist = room.UserList;
                    WriteOneObject(obcm);
                    for (ChatObject cm1 : room.ChatMsgList)
                        WriteOneObject(cm1);
                }
            }
        }

        public void Logout() {
            String msg = "[" + UserName + "]님이 퇴장 하였습니다.\n";
            UserVec.removeElement(this); // Logout한 현재 객체를 벡터에서 지운다
            //UserList = UserList.replace(this.UserName, ""); // Logout한 현재 객체를 리스트에서 지운다.
            //WriteAll(msg); // 나를 제외한 다른 User들에게 전송
//            WriteOthersObject(ob);
            AppendText("사용자 " + "[" + UserName + "] 퇴장. 현재 참가자 수 " + UserVec.size());
        }

        // 모든 User들에게 Object를 방송. 채팅 message와 image object를 보낼 수 있다
        public void WriteAllObject(Object ob) {
            for (int i = 0; i < user_vc.size(); i++) {
                UserService user = (UserService) user_vc.elementAt(i);
                if (user.UserStatus == "O")
                    user.WriteOneObject(ob);
            }
        }

        public void WriteOthersObject(Object ob) {
            for (int i = 0; i < user_vc.size(); i++) {
                UserService user = (UserService) user_vc.elementAt(i);
                if (user != this && user.UserStatus == "O")
                    user.WriteOneObject(ob);
            }
        }

        // UserService Thread가 담당하는 Client 에게 1:1 전송
        public void WriteOne(String msg) {
            try {
                ChatObject obcm = new ChatObject("SERVER", "200", msg);
                oos.writeObject(obcm);
            } catch (IOException e) {
                AppendText("dos.writeObject() error");
                try {
                    ois.close();
                    oos.close();
                    client_socket.close();
                    client_socket = null;
                    ois = null;
                    oos = null;
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                Logout(); // 에러가난 현재 객체를 벡터에서 지운다
            }
        }

        // 귓속말 전송
        public void WritePrivate(String msg) {
            try {
                ChatObject obcm = new ChatObject("귓속말", "200", msg);
                oos.writeObject(obcm);
            } catch (IOException e) {
                AppendText("dos.writeObject() error");
                try {
                    oos.close();
                    client_socket.close();
                    client_socket = null;
                    ois = null;
                    oos = null;
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                Logout(); // 에러가난 현재 객체를 벡터에서 지운다
            }
        }

        public void WriteOneObject(Object ob) {
            try {
                oos.writeObject(ob);
            } catch (IOException e) {
                AppendText("oos.writeObject(ob) error");
                try {
                    ois.close();
                    oos.close();
                    client_socket.close();
                    client_socket = null;
                    ois = null;
                    oos = null;
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                Logout();
            }
        }

        public void ChangeProfile(ChatObject cm) {

        }

        public void run() {
            while (true) { // 사용자 접속을 계속해서 받기 위해 while문
                try {
                    Object obcm = null;
                    String msg = null;
                    ChatObject cm = null;
                    if (socket == null)
                        break;
                    try {
                        obcm = ois.readObject();
                    } catch (ClassNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        return;
                    }
                    if (obcm == null)
                        break;
                    if (obcm instanceof ChatObject) {
                        cm = (ChatObject) obcm;
                        AppendObject(cm);
                    } else
                        continue;
                    if (cm.code.matches("100")) { // 유저가 로그인했음
                        UserName = cm.UserName;
                        //vectorToString(); // 업데이트된 UserVec와 UserList를 동기화시킴
                        UserStatus = "O"; // Online 상태
                        UserStatusMsg = cm.statusMsg;
//                        UserList += String.format("%s ", UserName);
//                        cm.userlist = UserList;
                        Login(cm);
                    } else if(cm.code.matches("110")) { // 프로필 변경
//                        ChangeProfile(cm);
                    } else if(cm.code.matches("120")) { // 상태 메시지 변경
//                        ChangeStatusMsg(cm);
                    } else if (cm.code.matches("200")) {
                        msg = String.format("[%s] %s", cm.UserName, cm.data);
                        AppendText(msg); // server 화면에 출력
                        //String[] args = msg.split(" "); // 단어들을 분리한다.

                        UserStatus = "O";

                        String[] users = cm.userlist.split(" ");
                        for(int i = 0; i < UserVec.size(); i++) { // 채팅방에 있는 모든 유저에게 채팅을 보냄
                            UserService tempUser = (UserService) user_vc.elementAt(i);
                            for (int j = 0; j < users.length; j++) {
                                if (tempUser.UserName.equals(users[j])) { // UserVec에서 채팅방에 참가한 user들을 찾음
                                    tempUser.WriteOneObject(cm);
                                }
                            }
                        }
//                        if (args.length == 1) { // Enter key 만 들어온 경우 Wakeup 처리만 한다.
//                            UserStatus = "O";
//                        } else if (args[1].matches("/exit")) {
//                            Logout();
//                            break;
//                        } else if (args[1].matches("/list")) {
//                            WriteOne("User list\n");
//                            WriteOne("Name\tStatus\n");
//                            WriteOne("-----------------------------\n");
//                            for (int i = 0; i < user_vc.size(); i++) {
//                                UserService user = (UserService) user_vc.elementAt(i);
//                                WriteOne(user.UserName + "\t" + user.UserStatus + "\n");
//                            }
//                            WriteOne("-----------------------------\n");
//                        } else if (args[1].matches("/sleep")) {
//                            UserStatus = "S";
//                        } else if (args[1].matches("/wakeup")) {
//                            UserStatus = "O";
//                        } else if (args[1].matches("/to")) { // 귓속말
//                            for (int i = 0; i < user_vc.size(); i++) {
//                                UserService user = (UserService) user_vc.elementAt(i);
//                                if (user.UserName.matches(args[2]) && user.UserStatus.matches("O")) {
//                                    String msg2 = "";
//                                    for (int j = 3; j < args.length; j++) {// 실제 message 부분
//                                        msg2 += args[j];
//                                        if (j < args.length - 1)
//                                            msg2 += " ";
//                                    }
//                                    // /to 빼고.. [귓속말] [user1] Hello user2..
//                                    user.WritePrivate(args[0] + " " + msg2 + "\n");
//                                    //user.WriteOne("[귓속말] " + args[0] + " " + msg2 + "\n");
//                                    break;
//                                }
//                            }
//                        } else { // 일반 채팅 메시지
//                            UserStatus = "O";
//                            //WriteAll(msg + "\n"); // Write All
//                            WriteAllObject(cm);
//                        }
                    } else if (cm.code.matches("400")) { // logout message 처리
                        Logout();
                        break;
                    }
//                    else if (cm.code.matches("500")) { // 채팅방 생성 요청
//                        if (cm.userlist.equals("")) // 아무도 선택하지 않았다면
//                            break; // 아무것도 하지 않음.
//                        ChatRoomPanel temp = new ChatRoomPanel(RoomID, cm.userlist);
//                        RoomVec.add(temp);
//                        ChatObject co = new ChatObject("510", RoomID, cm.userlist);
//                        String[] users = cm.userlist.split(" ");
//                        // 모든 유저 중에 선택된 유저들에게만 ChatObject를 보냄
//                        for (int i = 0; i < user_vc.size(); i++) {
//                            UserService tempUser = (UserService) user_vc.elementAt(i);
//                            for (int j = 0; j < users.length; j++) {
//                                if (tempUser.UserName.equals(users[j])) {
//                                    tempUser.WriteOneObject(co);
//                                    break;
//                                }
//                            }
//                        }
//                        RoomID++;
//                        break;
//                    }
                    else if (cm.code.matches("510")) {

                    } else { // 300, 500, ... 기타 object는 모두 방송한다.
                        WriteAllObject(cm);
                    }
                } catch (IOException e) {
                    AppendText("ois.readObject() error");
                    try {
                        ois.close();
                        oos.close();
                        client_socket.close();
                        Logout(); // 에러가난 현재 객체를 벡터에서 지운다
                        break;
                    } catch (Exception ee) {
                        break;
                    } // catch문 끝
                } // 바깥 catch문끝
            } // while
        } // run
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        panel1 = new JPanel();
        label2 = new JLabel();
        port = new JTextField();
        startBtn = new JButton();
        scrollPane1 = new JScrollPane();
        textArea = new JTextArea();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== panel1 ========
        {
            panel1.setLayout(null);

            //---- label2 ----
            label2.setText("Port Number");
            panel1.add(label2);
            label2.setBounds(65, 425, 80, 17);

            //---- port ----
            port.setText("30000");
            panel1.add(port);
            port.setBounds(150, 420, 155, 30);

            //---- startBtn ----
            startBtn.setText("Server Start");
            panel1.add(startBtn);
            startBtn.setBounds(110, 475, 150, 30);

            //======== scrollPane1 ========
            {

                //---- textArea ----
                textArea.setEditable(false);
                scrollPane1.setViewportView(textArea);
            }
            panel1.add(scrollPane1);
            scrollPane1.setBounds(22, 15, 325, 370);

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
        panel1.setBounds(0, 0, 370, 550);

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
    private JLabel label2;
    private JTextField port;
    private JButton startBtn;
    private JScrollPane scrollPane1;
    private JTextArea textArea;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
