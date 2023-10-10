import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.*;
/*
 * Created by JFormDesigner on Mon Nov 21 22:32:36 KST 2022
 */



/**
 * @author unknown
 */
public class RightChatPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private SimpleDateFormat dateFormat = new SimpleDateFormat(" aa kk:mm");
    private ImageIcon ori_img;
    public RightChatPanel(ChatObject cm) {
        initComponents();
        this.ori_img = cm.ori_imgData;
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

    private void imageMouseClicked(MouseEvent e) {
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
        time = new JLabel();
        chat = new JTextPane();
        image = new JLabel();

        //======== this ========
        setBackground(new Color(0xbacee0));
        setLayout(new FlowLayout(FlowLayout.RIGHT));

        //---- time ----
        time.setFont(new Font("\ub9d1\uc740 \uace0\ub515", Font.PLAIN, 10));
        add(time);

        //---- chat ----
        chat.setBackground(new Color(0xffeb33));
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
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JLabel time;
    private JTextPane chat;
    private JLabel image;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
