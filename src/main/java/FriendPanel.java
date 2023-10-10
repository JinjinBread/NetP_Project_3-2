import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
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

    public ImageIcon onlineImg = new ImageIcon("resources/online.png");
    public ImageIcon offlineImg = new ImageIcon("resources/offline.png");

    public FriendPanel(ChatClientMainView mainview, ImageIcon icon, String name, String userStatus, String statusMsg) {
        initComponents();
        this.UserName = name;
        this.UserStatus = userStatus;
        this.UserStatusMsg = statusMsg;
        this.UserIcon = icon;
        this.mainview = mainview;

        if (UserStatus.equals("O"))
            online = true;
        else
            online = false;
        setOnline(online);
        resizeIcon();

        setVisible(true);
        this.lblName.setText(UserName);
        lblStatusMsg.setText(UserStatusMsg);
    }

    // 원형 프로필
//    class MyLblProfile extends JLabel {
//
//        public MyLblProfile() {
//            init();
//        }
//
//        @Override
//        protected void paintComponent(Graphics g) {
//            super.paintComponent(g);
//
//            resizedIcon();
//
//            int diameter = Math.min(lblProfile.getWidth(), lblProfile.getHeight());
//            BufferedImage mask = new BufferedImage(UserIcon.getIconWidth(), UserIcon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
//
//            Graphics2D g2 = mask.createGraphics();
//            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
//            g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
//            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
//            g2.fillOval(0, 0, diameter, diameter);
//            g2.dispose();
//
//            BufferedImage masked = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
//            g2 = masked.createGraphics();
//            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
//            g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
//            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
//            int x = (diameter - UserIcon.getIconWidth()) / 2;
//            int y = (diameter - UserIcon.getIconHeight()) / 2;
//            g2.drawImage(UserIcon.getImage(), x, y, null);
//            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_IN));
//            g2.drawImage(mask, 0, 0, null);
//            g2.dispose();
//
//            UserIcon = new ImageIcon(masked);
//            this.setIcon(UserIcon);
//        }
//
//        private void lblProfileMouseClicked(MouseEvent e) { // 프로필 클릭 시
//            // TODO add your code here
//            if (UserName.equals(mainview.UserName)) { // 자기 자신의 프로필만 변경할 수 있음.
//                mainview.UserIcon = UserIcon;
//                MyProfile myProfile = new MyProfile(mainview);
//                myProfile.setVisible(true);
//            }
//        }
//
//        private void init() {
//            setIcon(null);
//            addMouseListener(new MouseAdapter() {
//                @Override
//                public void mouseClicked(MouseEvent e) {
//                    lblProfileMouseClicked(e);
//                }
//            });
//        }
//    }

    private void resizeIcon() {
        Image img = UserIcon.getImage();
        Image resizedImg = img.getScaledInstance(lblProfile.getWidth(), lblProfile.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImg);
        this.lblProfile.setIcon(resizedIcon);
    }

    public void setOnline(boolean tf) {
        online = tf;
        Image resizedImg;
        if (online) { // 온라인 이미지로 변경
            resizedImg = onlineImg.getImage().getScaledInstance(lblStatus.getWidth(), lblStatus.getHeight(), Image.SCALE_SMOOTH);
            lblStatus.setIcon(new ImageIcon(resizedImg));
        }
        else { // 오프라인 이미지로 변경
            resizedImg = offlineImg.getImage().getScaledInstance(lblStatus.getWidth(), lblStatus.getHeight(), Image.SCALE_SMOOTH);
            lblStatus.setIcon(new ImageIcon(resizedImg));
        }
        repaint();
    }

    public void setUserIcon(ImageIcon img) {
        Image resizedImg;
        resizedImg = img.getImage().getScaledInstance(lblProfile.getWidth(), lblProfile.getHeight(), Image.SCALE_SMOOTH);
        lblProfile.setIcon(new ImageIcon(resizedImg));
    }

    public void setUserStatusMsg(String userStatusMsg) {
        UserStatusMsg = userStatusMsg;
        lblStatusMsg.setText(userStatusMsg);
    }

    private void lblProfileMouseClicked(MouseEvent e) {
        // TODO add your code here
        if (UserName.equals(mainview.UserName)) { // 자기 자신의 프로필만 변경할 수 있음.
            mainview.UserIcon = UserIcon;
            MyProfile myProfile = new MyProfile(mainview);
            myProfile.setVisible(true);
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        lblName = new JLabel();
        lblStatusMsg = new JLabel();
        lblStatus = new JLabel();
        lblProfile = new JLabel();

        //======== this ========
        setBackground(Color.white);
        setLayout(null);

        //---- lblName ----
        lblName.setFont(lblName.getFont().deriveFont(lblName.getFont().getStyle() | Font.BOLD, 14f));
        add(lblName);
        lblName.setBounds(90, 23, 60, 20);

        //---- lblStatusMsg ----
        lblStatusMsg.setFont(new Font("\ub9d1\uc740 \uace0\ub515", Font.PLAIN, 9));
        add(lblStatusMsg);
        lblStatusMsg.setBounds(90, 40, 150, 20);
        add(lblStatus);
        lblStatus.setBounds(265, 30, 20, 20);

        //---- lblProfile ----
        lblProfile.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                lblProfileMouseClicked(e);
            }
        });
        add(lblProfile);
        lblProfile.setBounds(25, 15, 50, 50);

        setPreferredSize(new Dimension(309, 80));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    public JLabel lblName;
    public JLabel lblStatusMsg;
    public JLabel lblStatus;
    public JLabel lblProfile;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
