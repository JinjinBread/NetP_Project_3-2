import java.awt.*;
import javax.swing.*;
/*
 * Created by JFormDesigner on Tue Nov 15 16:14:16 KST 2022
 */



/**
 * @author unknown
 */
public class FriendPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    public ChatClientMainView mainview;
    public String UserName; // 유저 이름
    public String UserStatus; // 유저 상태
    public String UserStatusMsg; // 유저 상태 메시지
    public ImageIcon UserIcon; // 프로필 사진
    public boolean online = false; // 유저가 온라인인지 오프라인인지

    private ImageIcon onlineImg = new ImageIcon("resources/online.png");
    private ImageIcon offlineImg = new ImageIcon("resources/offline.png");

    public FriendPanel(ChatClientMainView mainview, ImageIcon icon, String name, String userStatus, String statusMsg) {
        initComponents();

        this.UserName = name;
        this.UserStatus = userStatus;
        this.UserStatusMsg = statusMsg;
        this.UserIcon = icon;
        this.mainview = mainview;
        this.online = true;

        setOnOffImg();
        resizeIcon();

        setVisible(true);
        //this.profile.setIcon(cm.img);
        this.name.setText(UserName);
        lblStatusMsg.setText(UserStatusMsg);
        //this.status.setText(cm.status);
    }

    private void resizeIcon() {
        Image img = UserIcon.getImage();
        Image resizedImg = img.getScaledInstance(profile.getWidth(), profile.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImg);
        this.profile.setIcon(resizedIcon);
    }

    public void setOnOffImg() {
        if (online)
            status.setIcon(onlineImg);
        else
            status.setIcon(offlineImg);
    }

    public void setOnline(boolean tf) {
        online = tf;
        if (online) // 온라인 이미지로 변경
            status.setIcon(onlineImg);
        else // 오프라인 이미지로 변경
            status.setIcon(offlineImg);
        repaint();
    }

    public void setIcon(ChatObject cm) {
        UserIcon = cm.img;
    }

    public void setStatus(String status) {
        lblStatusMsg.setText(status);
    }

    public void setUserStatusMsg(String userStatusMsg) {
        UserStatusMsg = userStatusMsg;
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        name = new JLabel();
        lblStatusMsg = new JLabel();
        status = new JLabel();
        profile = new JLabel();

        //======== this ========
        setBackground(Color.white);
        setLayout(null);

        //---- name ----
        name.setFont(name.getFont().deriveFont(name.getFont().getStyle() | Font.BOLD, 14f));
        add(name);
        name.setBounds(90, 23, 60, 20);

        //---- lblStatusMsg ----
        lblStatusMsg.setFont(new Font("\ub9d1\uc740 \uace0\ub515", Font.PLAIN, 9));
        add(lblStatusMsg);
        lblStatusMsg.setBounds(90, 40, 150, 20);
        add(status);
        status.setBounds(265, 30, 20, 20);

        //---- profile ----
        profile.setIcon(new ImageIcon("resources/default_profile.jpg"));
        add(profile);
        profile.setBounds(25, 15, 50, 50);

        setPreferredSize(new Dimension(309, 80));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    public JLabel name;
    public JLabel lblStatusMsg;
    public JLabel status;
    public JLabel profile;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
