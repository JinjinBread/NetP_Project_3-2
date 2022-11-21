import panel.DialogFriendPanel;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import javax.swing.*;
/*
 * Created by JFormDesigner on Sun Nov 20 23:11:45 KST 2022
 */



/**
 * @author unknown
 */
public class FriendListDialog extends JDialog {
    private Vector<DialogFriendPanel> dialogFriendPanels = new Vector<>();
    private ChatClientHome mainview;
    private Vector<String> friendList;
    public FriendListDialog(ChatClientHome mainview, Vector<String> friendList) {
        super(mainview);
        initComponents();
        this.mainview = mainview;
        this.friendList = friendList;
        if (friendList.size() > 0) { // 자기 자신만 온라인인 경우는 목록에 아무도 띄우지 않음
            for (int i = 1; i < friendList.size(); i++) { // friendList.get(0)은 자기 자신이므로 1부터 시작
                DialogFriendPanel temp = new DialogFriendPanel(friendList.get(i));
                dialogFriendPanels.add(temp);
                textPane1.insertComponent(temp);
            }
        }
    }

    private void okMouseClicked(MouseEvent e) {
        // TODO add your code here
        String checkedClient = String.format("%s ", friendList.get(0)); // 자기 자신
        for(int i = 0; i < dialogFriendPanels.size(); i++) {
            if (dialogFriendPanels.get(i).isSelected()) {
                checkedClient += String.format("%s ", dialogFriendPanels.get(i).getName());
            }

            ChatObject obcm = new ChatObject("500", checkedClient);
            mainview.SendObject(obcm);
        }
        this.dispose();
    }

    private void cancelMouseClicked(MouseEvent e) {
        // TODO add your code here
        this.dispose();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        scrollPane1 = new JScrollPane();
        textPane1 = new JTextPane();
        ok = new JButton();
        cancel = new JButton();
        label1 = new JLabel();

        //======== this ========
        setBackground(Color.white);
        var contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== scrollPane1 ========
        {
            scrollPane1.setBorder(null);
            scrollPane1.setBackground(Color.white);

            //---- textPane1 ----
            textPane1.setBorder(null);
            scrollPane1.setViewportView(textPane1);
        }
        contentPane.add(scrollPane1);
        scrollPane1.setBounds(0, 50, 370, 440);

        //---- ok ----
        ok.setText("\ud655\uc778");
        ok.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                okMouseClicked(e);
            }
        });
        contentPane.add(ok);
        ok.setBounds(190, 505, 80, 40);

        //---- cancel ----
        cancel.setText("\ucde8\uc18c");
        cancel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cancelMouseClicked(e);
            }
        });
        contentPane.add(cancel);
        cancel.setBounds(275, 505, 80, 40);

        //---- label1 ----
        label1.setText("\uce5c\uad6c \ubaa9\ub85d");
        label1.setHorizontalAlignment(SwingConstants.CENTER);
        label1.setFont(label1.getFont().deriveFont(label1.getFont().getStyle() | Font.BOLD, 15f));
        label1.setBackground(Color.white);
        contentPane.add(label1);
        label1.setBounds(139, 10, 90, 30);

        contentPane.setPreferredSize(new Dimension(370, 590));
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JScrollPane scrollPane1;
    private JTextPane textPane1;
    private JButton ok;
    private JButton cancel;
    private JLabel label1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
