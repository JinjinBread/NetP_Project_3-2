import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
/*
 * Created by JFormDesigner on Fri Dec 02 20:40:46 KST 2022
 */



/**
 * @author unknown
 */
public class MyProfile extends JFrame {
    private FileDialog fd;
    private ChatClientMainView mainview;
    private ChatObject obcm;
    public MyProfile(ChatClientMainView mainview) {
        initComponents();
        this.addWindowListener(new CloseAction());
        // resize
        Image img = mainview.UserIcon.getImage();
        Image resizedImg = img.getScaledInstance(lblProfile.getWidth(), lblProfile.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImg);
        lblProfile.setIcon(resizedIcon);

        obcm = new ChatObject(mainview.UserName, "0", "nothing change");
        this.mainview = mainview;
        MyProfile.ImageSendAction action = new MyProfile.ImageSendAction();
        lblProfile.addMouseListener(action);
    }

    private void statusMsgFocusGained(FocusEvent e) {
        // TODO add your code here
        statusMsg.setForeground(Color.black);
        if (statusMsg.getText().isEmpty() || statusMsg.getText().equals(" 상태메시지")) {
            statusMsg.setText(""); // hint 없애기
        } else {
            obcm.statusMsg = statusMsg.getText();
        }
    }

    private void statusMsgFocusLost(FocusEvent e) {
        // TODO add your code here
        if (statusMsg.getText().isEmpty()) { // 비어있으면 hint 출력
            statusMsg.setForeground(new Color(0x999999));
            statusMsg.setText(" 상태메시지");
        } else {
            obcm.statusMsg = statusMsg.getText();
        }
    }

    private void okMouseClicked(MouseEvent e) {
        // TODO add your code here
        if (obcm.statusMsg == null && fd != null) { // 프로필 사진만 변경
            obcm.code = "110";
            obcm.data = "Change Profile";
            mainview.SendObject(obcm);
        } else if (obcm.statusMsg != null && fd == null) { // 상태메시지만 변경
            obcm.code = "120";
            obcm.data = "Change Status Message";
            mainview.SendObject(obcm);
        } else if (obcm.statusMsg != null && fd != null)  { // 둘 다 변경
            obcm.code = "110";
            obcm.data = "Change Profile";
            ChatObject obcm2 = new ChatObject(mainview.UserName, "120", "Change Status Message");
            obcm2.statusMsg = statusMsg.getText();
            mainview.SendObject(obcm); // 프로필 사진
            mainview.SendObject(obcm2); // 상태메시지
        }
        dispose();
    }

    private void cancelMouseClicked(MouseEvent e) {
        // TODO add your code here
        dispose();
    }

    class ImageSendAction extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getSource() == lblProfile) {
                Frame frame = new Frame("이미지첨부");
                fd = new FileDialog(frame, "이미지 선택", FileDialog.LOAD);
                // frame.setVisible(true);
                // fd.setDirectory(".\\");
                fd.setVisible(true);
                // System.out.println(fd.getDirectory() + fd.getFile());
                if (fd.getDirectory().length() > 0 && fd.getFile().length() > 0) {
                    ImageIcon img = new ImageIcon(fd.getDirectory() + fd.getFile());
                    Image resizedImg = img.getImage().getScaledInstance(lblProfile.getWidth(), lblProfile.getHeight(), Image.SCALE_SMOOTH);
                    lblProfile.setIcon(new ImageIcon(resizedImg));
                    obcm.img = img;
//                    ChatObject obcm = new ChatObject(UserName, "300", "IMG");
//                    obcm.userlist = UserList;
//                    obcm.room_id = Room_Id;
//                    ImageIcon img = new ImageIcon(fd.getDirectory() + fd.getFile());
//                    obcm.imgData = img;
//                    obcm.ori_imgData = img;
//                    mainview.SendObject(obcm);
                }
            }
        }
    }

    class CloseAction extends WindowAdapter {
        @Override
        public void windowClosed(WindowEvent e) {
            // 닫기 버튼을 누르면 현재 프로필 변강하는 창만 닫아짐.
            dispose();
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        panel1 = new JPanel();
        lblProfile = new JLabel();
        statusMsg = new JTextField();
        label = new JLabel();
        ok = new JButton();
        cancel = new JButton();

        //======== this ========
        setBackground(Color.white);
        var contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== panel1 ========
        {
            panel1.setBackground(Color.white);
            panel1.setLayout(null);
            panel1.add(lblProfile);
            lblProfile.setBounds(114, 76, 100, 100);

            //---- statusMsg ----
            statusMsg.setBorder(new MatteBorder(0, 0, 1, 0, new Color(0xe6e6e6)));
            statusMsg.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    statusMsgFocusGained(e);
                }
                @Override
                public void focusLost(FocusEvent e) {
                    statusMsgFocusLost(e);
                }
            });
            panel1.add(statusMsg);
            statusMsg.setBounds(52, 207, 224, 40);

            //---- label ----
            label.setText("\ud504\ub85c\ud544 \ubcc0\uacbd");
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setFont(new Font("\ub9d1\uc740 \uace0\ub515 Semilight", Font.PLAIN, 17));
            label.setForeground(Color.black);
            panel1.add(label);
            label.setBounds(101, 13, 125, 45);

            //---- ok ----
            ok.setText("\ud655\uc778");
            ok.setBorder(new MatteBorder(1, 1, 1, 1, new Color(0xe6e6e6)));
            ok.setBackground(Color.white);
            ok.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    okMouseClicked(e);
                }
            });
            panel1.add(ok);
            ok.setBounds(135, 280, 80, 30);

            //---- cancel ----
            cancel.setText("\ucde8\uc18c");
            cancel.setBorder(new MatteBorder(1, 1, 1, 1, new Color(0xe6e6e6)));
            cancel.setBackground(Color.white);
            cancel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    cancelMouseClicked(e);
                }
            });
            panel1.add(cancel);
            cancel.setBounds(225, 280, 80, 30);

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
        panel1.setBounds(-1, 0, 330, 340);

        contentPane.setPreferredSize(new Dimension(330, 370));
        setSize(330, 370);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel panel1;
    private JLabel lblProfile;
    private JTextField statusMsg;
    private JLabel label;
    private JButton ok;
    private JButton cancel;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
