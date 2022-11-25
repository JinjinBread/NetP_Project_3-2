import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;
/*
 * Created by JFormDesigner on Thu Nov 17 14:08:39 KST 2022
 */


/**
 * @author unknown
 */
public class ChatRoomPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    public int Room_Id;
    public String UserName;
    public String UserList;
    public String UserStatus;
    public String LastMsg = " "; // 마지막 메시지 내용
    public String LastTime = " "; // 마지막 메시지가 온 시간
    public ChatClientMainView mainview;
    public ImageIcon RoomIcon;
    public ChatClientChatRoomView roomview; // 실제 열리는 채팅방
    public Boolean online = false;
    public Image tmpImg = null;
    public Graphics2D tmpGc;
    public ChatObject[] ChatMsgList; // 채팅방 내용 저장
    private boolean clicked = false; // 클릭되어 있으면 background 색을 회색으로

    public ChatRoomPanel(ChatClientMainView mainview, ImageIcon icon, String name, String userlist, int room_id) {
        this.mainview = mainview;
        RoomIcon = icon;
        UserName = name;
        Room_Id = room_id;
        UserList = userlist;
        String[] users = userlist.split(" ");
        String temp = users[0];
        for (int i = 1; i < users.length; i++) {
            temp += String.format(", %s", users[i]);
        }
        this.lblChatRoomName.setText(temp);
        initComponents();
    }

//    public ChatClientChatRoom getChatRoom(int room_id) {}

    public void createChatRoomView() {
        roomview = new ChatClientChatRoomView(mainview, UserName, UserList, Room_Id);
    }

    public void paint(Graphics g) {
        super.paint(g);
        if (tmpImg == null) {
            tmpImg = createImage(lblRoomIcon.getWidth(), lblRoomIcon.getHeight());
            tmpGc = (Graphics2D) tmpImg.getGraphics();
            setChatRoomIcon();
        }
    }

    public void setChatRoomIcon() {
        String[] users = UserList.split(" ");
        Image img = null;
        if (users.length == 1) { // 나와의 채팅
            RoomIcon = mainview.UserIcon; // 내 아이콘으로
            img = RoomIcon.getImage().getScaledInstance(lblRoomIcon.getWidth(), lblRoomIcon.getHeight(), Image.SCALE_SMOOTH);
        } else { // 상대와의 채팅이면
            ArrayList<ImageIcon> icons = new ArrayList<>();
            int i = 0;
            for (int j = 0; j < users.length && i < 4; j++) { // 최대 4명의 유저 프로필 사진만 보여주므로 i < 4
                if (users[i].equals(UserName)) // 자신의 프로필은 제외
                    continue;
                icons.add(mainview.GetUserIcon(users[i]));
                i++;
            }
            if (i == 1) { // 2인 채팅방일 경우
                RoomIcon = icons.get(0); // 상대방 아이콘으로 RoomIcon 등록
                img = RoomIcon.getImage().getScaledInstance(lblRoomIcon.getWidth()/2, lblRoomIcon.getHeight()/2, Image.SCALE_SMOOTH);
            } else {
                tmpGc.setColor(Color.WHITE);
                tmpGc.fillRect(0, 0, lblRoomIcon.getWidth(), lblRoomIcon.getHeight());

                if (i == 2) { // 3인 채팅방
                    Image img1 = icons.get(0).getImage().getScaledInstance(lblRoomIcon.getWidth()/2, lblRoomIcon.getHeight()/2, Image.SCALE_SMOOTH);
                    Image img2 = icons.get(1).getImage().getScaledInstance(lblRoomIcon.getWidth()/2, lblRoomIcon.getHeight()/2, Image.SCALE_SMOOTH);
                    tmpGc.drawImage(img1, 0, 0, lblRoomIcon);
                    tmpGc.drawImage(img2, lblRoomIcon.getWidth()/2, lblRoomIcon.getHeight()/2, lblRoomIcon);
                }

                if (i == 3) { // 4인 채팅방
                    Image img1 = icons.get(0).getImage().getScaledInstance(lblRoomIcon.getWidth()/2, lblRoomIcon.getHeight()/2, Image.SCALE_SMOOTH);
                    Image img2 = icons.get(1).getImage().getScaledInstance(lblRoomIcon.getWidth()/2, lblRoomIcon.getHeight()/2, Image.SCALE_SMOOTH);
                    Image img3 = icons.get(2).getImage().getScaledInstance(lblRoomIcon.getWidth()/2, lblRoomIcon.getHeight()/2, Image.SCALE_SMOOTH);
                    tmpGc.drawImage(img1, lblRoomIcon.getWidth()/4, 0, lblRoomIcon);
                    tmpGc.drawImage(img2, 0, lblRoomIcon.getHeight()/2, lblRoomIcon);
                    tmpGc.drawImage(img3, lblRoomIcon.getWidth()/2, lblRoomIcon.getHeight()/2, lblRoomIcon);
                }

                if (i == 4) { // 5인 이상 채팅방
                    Image img1 = icons.get(0).getImage().getScaledInstance(lblRoomIcon.getWidth()/2, lblRoomIcon.getHeight()/2, Image.SCALE_SMOOTH);
                    Image img2 = icons.get(1).getImage().getScaledInstance(lblRoomIcon.getWidth()/2, lblRoomIcon.getHeight()/2, Image.SCALE_SMOOTH);
                    Image img3 = icons.get(2).getImage().getScaledInstance(lblRoomIcon.getWidth()/2, lblRoomIcon.getHeight()/2, Image.SCALE_SMOOTH);
                    Image img4 = icons.get(3).getImage().getScaledInstance(lblRoomIcon.getWidth()/2, lblRoomIcon.getHeight()/2, Image.SCALE_SMOOTH);
                    // 정사각형을 십자가로 나눠서 왼쪽 위를 1, 오른쪽 위를 2, 왼쪽 아래를 3, 오른쪽 아래를 4라고 지칭하면
                    tmpGc.drawImage(img1, 0, 0, lblRoomIcon); // 1에 위치
                    tmpGc.drawImage(img2, lblRoomIcon.getWidth()/2, 0, lblRoomIcon); // 2에 위치
                    tmpGc.drawImage(img3, 0, lblRoomIcon.getHeight()/2, lblRoomIcon); // 3에 위치
                    tmpGc.drawImage(img4, lblRoomIcon.getWidth()/2, lblRoomIcon.getHeight()/2, lblRoomIcon); // 4에 위치
                }
                img = tmpImg;
            }
        }
        lblRoomIcon.setIcon(new ImageIcon(img));
    }

    public void AppendText(ChatObject cm) {
        setLastMsg(cm);
        mainview.AppendText(cm);
    }

    public void AppendImage(ChatObject cm) {
        setLastMsg(cm);
        mainview.AppendImage(cm);
    }

    public void ChangeFriendProfile(ChatObject cm) {
        mainview.ChangeFriendProfile(cm);
        setChatRoomIcon(); // roomIcon을 update해줌
        // setSelectFriendIcon(); // SelectFriendDialog icon를 update해줌 
    }

    public void setLastMsg(ChatObject cm) {
        LastMsg = cm.data;
        lblLastMsg.setText(LastMsg);
        lblLastTime.setText(getTime(cm.date));
    }

    public String getTime(Date date) {
        SimpleDateFormat f = new SimpleDateFormat("a h:mm");
        return f.format(date);
    }

    private void ChatRoomMouseClicked(MouseEvent e) {
        // TODO add your code here
        if (e.getClickCount() == 1) {
            // background 색 변경 (우선순위 下)

        }
        // 더블 클릭하면 채팅방 켜짐
        if (e.getClickCount() == 2) {
            roomview.setVisible(true);
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        lblRoomIcon = new JLabel();
        lblChatRoomName = new JLabel();
        lblLastMsg = new JLabel();
        lblLastTime = new JLabel();

        //======== this ========
        setBackground(Color.white);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ChatRoomMouseClicked(e);
            }
        });
        setLayout(null);
        add(lblRoomIcon);
        lblRoomIcon.setBounds(20, 20, 55, 55);

        //---- lblChatRoomName ----
        lblChatRoomName.setFont(lblChatRoomName.getFont().deriveFont(lblChatRoomName.getFont().getStyle() | Font.BOLD, 13f));
        add(lblChatRoomName);
        lblChatRoomName.setBounds(85, 20, 130, 20);
        add(lblLastMsg);
        lblLastMsg.setBounds(85, 45, 150, 30);
        add(lblLastTime);
        lblLastTime.setBounds(245, 20, 50, 20);

        setPreferredSize(new Dimension(309, 95));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    public JLabel lblRoomIcon;
    public JLabel lblChatRoomName;
    public JLabel lblLastMsg;
    public JLabel lblLastTime;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
