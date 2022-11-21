import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/*
 * Created by JFormDesigner on Thu Nov 17 14:08:39 KST 2022
 */



/**
 * @author unknown
 */
public class ChatRoomPanel extends JPanel {
    public int room_id;
    private ChatClientHome mainview;
    public ChatRoomPanel(ChatClientHome mainview, String userlist, int room_id) {
        initComponents();
        this.room_id = room_id;
        this.mainview = mainview;
        String[] users = userlist.split(" ");
        String temp = "";
        for(int i = 0; i < users.length - 1; i++) {
            temp += String.format("%s, ", users[i]);
        }
        temp += String.format("%s", users[users.length - 1]);
        this.users.setText(temp);
    }

    public void setTime(String time) {
        this.time.setText(time);
    }

    public void setChatting(String lastChat) {
        this.chatting.setText(lastChat);
    }

    public void setProfile() {

    }

    private void ChatRoomMouseClicked(MouseEvent e) {
        // TODO add your code here
        if (e.getClickCount() == 1) {
            // background 색 변경 (우선순위 下)

        }
        if (e.getClickCount() == 2) { // double click하면,
            new ChatClientChatRoom(mainview, room_id);
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        profile = new JLabel();
        users = new JLabel();
        chatting = new JLabel();
        time = new JLabel();

        //======== this ========
        setBackground(Color.white);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ChatRoomMouseClicked(e);
            }
        });
        setLayout(null);
        add(profile);
        profile.setBounds(20, 20, 55, 55);

        //---- users ----
        users.setFont(users.getFont().deriveFont(users.getFont().getStyle() | Font.BOLD, 13f));
        add(users);
        users.setBounds(85, 20, 130, 20);
        add(chatting);
        chatting.setBounds(85, 45, 150, 30);
        add(time);
        time.setBounds(245, 20, 50, 20);

        {
            // compute preferred size
            Dimension preferredSize = new Dimension();
            for(int i = 0; i < getComponentCount(); i++) {
                Rectangle bounds = getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            setMinimumSize(preferredSize);
            setPreferredSize(preferredSize);
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JLabel profile;
    private JLabel users;
    private JLabel chatting;
    private JLabel time;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
