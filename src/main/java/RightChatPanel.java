import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
/*
 * Created by JFormDesigner on Mon Nov 21 22:32:36 KST 2022
 */



/**
 * @author unknown
 */
public class RightChatPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    
    public static void main(String[] args) { // 말풍선이 어떻게 그려지는지 보기 위해 넣은 main. 후에 삭제.

    }
    private SimpleDateFormat dateFormat = new SimpleDateFormat("a hh:mm");
    public RightChatPanel(String msg, String curTime) {
        initComponents();
        this.chat.setText(msg);
        this.time.setText(curTime);
    }

    public void setTime() {
        Date date = new Date();
        String time = dateFormat.format(date);
        this.time.setText(time);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        chat = new JLabel();
        time = new JLabel();

        //======== this ========
        setLayout(null);
        add(chat);
        chat.setBounds(150, 40, 195, 30);
        add(time);
        time.setBounds(65, 50, 60, 20);

        setPreferredSize(new Dimension(365, 85));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JLabel chat;
    private JLabel time;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
