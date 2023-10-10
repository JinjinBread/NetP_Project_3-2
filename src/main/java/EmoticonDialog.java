import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Vector;
import javax.swing.*;



public class EmoticonDialog extends JDialog {
    private Vector<ImageIcon> emoticons = new Vector<>();
    private ChatClientMainView mainview;
    private String UserName; // 이모티콘을 보내는 유저 네임
    private int Room_Id;
    private String UserList;

    public EmoticonDialog(ChatClientMainView mainview, int room_id, String uesrList) {
        initComponents();
        setVisible(true);
        setLocationRelativeTo(mainview);
        this.mainview = mainview;
        this.Room_Id = room_id;
        this.UserList = uesrList;

        addEmoticon();
        SendEmoticonAction action = new SendEmoticonAction();
        for (ImageIcon emoticon : emoticons) {
            JLabel img = new JLabel(new ImageIcon(emoticon.getImage()));
            img.addMouseListener(action);
            panel.add(img);
        }
    }

    private void addEmoticon() {
        File dir = new File("resources/emoticon");
        File files[] = dir.listFiles();

        for (File file : files) {
            ImageIcon temp1 = new ImageIcon(file.getPath());
            //Image temp2 = temp1.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            emoticons.add(new ImageIcon(temp1.getImage()));
        }
    }

    class SendEmoticonAction extends MouseAdapter // 내부클래스로 액션 이벤트 처리 클래스. 이모티콘 클릭 시 전송
    {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            ChatObject cm = new ChatObject(mainview.UserName, "300", "EMOTICON");
            cm.img = mainview.UserIcon; // 프로필
//            cm.userlist = UserList; // 방 유저리스트
            cm.room_id = Room_Id; // 방번호
            JLabel imgData = (JLabel) e.getSource();
//            ImageIcon temp1 = (ImageIcon)imgData.getIcon();
//            Image temp2 = temp1.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            cm.imgData = (ImageIcon)imgData.getIcon(); // 보내는 이미지 데이터
            cm.ori_imgData = (ImageIcon)imgData.getIcon();
            mainview.SendObject(cm);
            dispose(); // 이모티콘 클릭(선택)하면 다이얼로그 닫음
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        scrollPane = new JScrollPane();
        panel = new JPanel();

        //======== this ========
        setResizable(false);
        var contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== scrollPane ========
        {
            scrollPane.setBorder(null);

            //======== panel ========
            {
                panel.setLayout(new GridLayout(0, 4));
            }
            scrollPane.setViewportView(panel);
        }
        contentPane.add(scrollPane);
        scrollPane.setBounds(0, 0, 362, 320);

        contentPane.setPreferredSize(new Dimension(375, 350));
        setSize(375, 350);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JScrollPane scrollPane;
    private JPanel panel;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
