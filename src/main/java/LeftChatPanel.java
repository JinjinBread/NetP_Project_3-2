import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.border.*;
/*
 * Created by JFormDesigner on Sat Nov 26 02:33:45 KST 2022
 */



/**
 * @author unknown
 */
public class LeftChatPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private SimpleDateFormat dateFormat = new SimpleDateFormat(" aa kk:mm");
    private int imageSize = 30; // 프로필 사진 크기
    private ImageIcon ori_img;
    public String UserName;
    public LeftChatPanel(ChatObject cm) {

        initComponents();
        this.ori_img = cm.ori_imgData;
        this.UserName = cm.UserName;
        setProfile(cm);
        name.setText(cm.UserName);
        chat.setText(cm.data);
        time.setText(dateFormat.format(cm.date));
        if (cm.code.equals("300")) {
            remove(chat);
            System.out.println(cm.imgData);
            image.setIcon(new ImageIcon(cm.imgData.getImage()));
        } else {
            remove(image);
            chat.setText(cm.data);
        }
    }

    public void setProfile(ChatObject cm) {
        Image img = cm.img.getImage();
        Image resizedImg = img.getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
        profile.setIcon(new ImageIcon(resizedImg));
    }

    private void imageMouseClicked(MouseEvent e) { // imageViewer 생성
        // TODO add your code here
        JDialog imageViewer = new JDialog();
        imageViewer.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // 닫으면 다이얼로그 없애기
        imageViewer.setTitle("Image Viewer");
        double ratio;
        ImageIcon new_icon = ori_img;
        int width = ori_img.getIconWidth();
        int height = ori_img.getIconHeight();
        // Image가 너무 크면 최대 가로 또는 세로 200 기준으로 축소시킨다.
        if (width > 800 || height > 800) {
            if (width > height) { // 가로 사진
                ratio = (double) height / width;
                width = 800;
                height = (int) (width * ratio);
            } else { // 세로 사진
                ratio = (double) width / height;
                height = 800;
                width = (int) (height * ratio);
            }
            Image new_img = ori_img.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            new_icon = new ImageIcon(new_img);
        }
        imageViewer.setSize(800, 800);
        imageViewer.add(new JLabel(new ImageIcon(new_icon.getImage())));
        //imageViewer.pack();
        imageViewer.setVisible(true);
    }


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        profile = new JLabel();
        name = new JLabel();
        chat = new JTextPane();
        image = new JLabel();
        time = new JLabel();

        //======== this ========
        setBackground(new Color(0xbacee0));
        setLayout(new FlowLayout(FlowLayout.LEFT));
        add(profile);

        //---- name ----
        name.setBackground(Color.white);
        add(name);

        //---- chat ----
        chat.setBackground(Color.white);
        chat.setBorder(new EmptyBorder(5, 5, 5, 5));
        chat.setEditable(false);
        add(chat);

        //---- image ----
        image.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                imageMouseClicked(e);
            }
        });
        add(image);

        //---- time ----
        time.setFont(new Font("\ub9d1\uc740 \uace0\ub515", Font.PLAIN, 10));
        add(time);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JLabel profile;
    private JLabel name;
    private JTextPane chat;
    private JLabel image;
    private JLabel time;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
