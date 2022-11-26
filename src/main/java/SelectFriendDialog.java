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
public class SelectFriendDialog extends JDialog {
    private static final long serialVersionUID = 1L;
    public Vector<SelectFriendDialogPanel> FriendListPanel = new Vector<>();
    private ChatClientMainView mainview;
    public boolean OkClicked = false;
    public String userlist;
    public Vector<SelectFriendDialogPanel> SelectVector = new Vector<>();
    public SelectFriendDialog(ChatClientMainView mainview) {
        // 모달로 설정 즉, 특정 선택을 해야 화면이 사라진다.
        initComponents();
        this.mainview = mainview;
        for (int i = 0; i < mainview.FriendVector.size(); i++) {
            FriendPanel friend1 = mainview.FriendVector.get(i);
            // 온라인 상태면 체크박스 활성화 checked(체크박스name).setEnabled(true);
            if (mainview.UserName.equals(friend1.UserName)) // 자기 자신은 친구 선택 다이얼로그에 띄우지 않음. (+ SelectVector에 넣지 않음)
                continue;
            if (friend1.UserStatus.equals("O")) {
                SelectFriendDialogPanel friend = new SelectFriendDialogPanel(friend1.UserIcon, friend1.UserName);
                //friend.setSelectable(true); // 체크박스 활성화
                listPane.setCaretPosition(listPane.getDocument().getLength());
                //listPane.setCaretPosition(0);
                listPane.insertComponent(friend);
                listPane.setCaretPosition(0);
                SelectVector.add(friend);
            }
        }
    }

    public String ShowDialog() {
        setVisible(true);
        if (OkClicked) { // 확인버튼을 누르면
            int sum = 0;
            String userlist = "";
            for (int i = 0; i < SelectVector.size(); i++) {
                SelectFriendDialogPanel tmpSf = SelectVector.get(i);
                if (tmpSf.checkBox.isSelected()) { // 선택되었다면,
                    userlist += String.format("%s ", tmpSf.UserName);
                    sum++;
                }
                tmpSf.checkBox.setSelected(false); // 선택 여부를 확인했으면 선택을 해제한다.
            }
            if (sum == 0) // 아무도 선택하지 않은 체 확인을 누름
                return null;

            userlist = mainview.UserName + " " + userlist; // 처음 위치에 자신을 넣음
            return userlist;
        }
        else
            return null;
    }

    private void okMouseClicked(MouseEvent e) {
        // TODO add your code here
        OkClicked = true;
        setVisible(false);
        //this.dispose();
    }

    private void cancelMouseClicked(MouseEvent e) {
        // TODO add your code here
        OkClicked = false;
        setVisible(false);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        scrollPane1 = new JScrollPane();
        listPane = new JTextPane();
        ok = new JButton();
        cancel = new JButton();
        panel1 = new JPanel();
        label1 = new JLabel();

        //======== this ========
        setBackground(Color.white);
        setModal(true);
        setResizable(false);
        var contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== scrollPane1 ========
        {
            scrollPane1.setBorder(null);
            scrollPane1.setBackground(Color.white);

            //---- listPane ----
            listPane.setBorder(null);
            listPane.setEditable(false);
            listPane.setBackground(Color.white);
            scrollPane1.setViewportView(listPane);
        }
        contentPane.add(scrollPane1);
        scrollPane1.setBounds(0, 50, 369, 440);

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

        //======== panel1 ========
        {
            panel1.setBackground(Color.white);
            panel1.setLayout(null);

            //---- label1 ----
            label1.setText("\ub300\ud654\uc0c1\ub300 \uc120\ud0dd ");
            label1.setHorizontalAlignment(SwingConstants.CENTER);
            label1.setFont(label1.getFont().deriveFont(label1.getFont().getStyle() | Font.BOLD, 15f));
            label1.setBackground(Color.white);
            label1.setEnabled(false);
            label1.setForeground(Color.black);
            panel1.add(label1);
            label1.setBounds(10, 10, 116, 30);

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
        panel1.setBounds(0, 0, 370, 560);

        contentPane.setPreferredSize(new Dimension(370, 590));
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JScrollPane scrollPane1;
    private JTextPane listPane;
    private JButton ok;
    private JButton cancel;
    private JPanel panel1;
    private JLabel label1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
