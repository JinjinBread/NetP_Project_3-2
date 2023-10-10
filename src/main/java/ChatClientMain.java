import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;
import javax.swing.*;
import javax.swing.border.*;
/*
 * Created by JFormDesigner on Thu Nov 10 23:43:53 KST 2022
 */



/**
 * @author unknown
 */
public class ChatClientMain extends JFrame {
    @Serial
    private static final long serialVersionUID = 1L;
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ChatClientMain frame = new ChatClientMain();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public ChatClientMain() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        Image img = new ImageIcon("resources/TALKLogo.png").getImage().getScaledInstance(logo.getWidth(), logo.getHeight(), Image.SCALE_SMOOTH);
        logo.setIcon(new ImageIcon(img));
        MyAction action = new MyAction();
        connect.addActionListener(action);
        name.addActionListener(action);
        ip.addActionListener(action);
        port.addActionListener(action);
    }

    private void nameFocusGained(FocusEvent e) { // name textField 포커스 획득
        // TODO add your code here
        name.setForeground(Color.black);
        if (name.getText().isEmpty() || name.getText().equals(" User Name")) {
            name.setText(""); // hint 없애기
        }
    }

    private void nameFocusLost(FocusEvent e) {
        // TODO add your code here
        if (name.getText().isEmpty()) { // 비어있으면 hint 출력
            name.setForeground(new Color(0x999999));
            name.setText(" User Name");
        }
    }

    private void ipFocusGained(FocusEvent e) {
        // TODO add your code here
        ip.setForeground(Color.black);
        if (ip.getText().isEmpty() || ip.getText().equals(" IP Address")) {
            ip.setText(""); // hint 없애기
        }
    }

    private void ipFocusLost(FocusEvent e) {
        // TODO add your code here
        if (ip.getText().isEmpty()) { // 비어있으면 hint 출력
            ip.setForeground(new Color(0x999999));
            ip.setText(" IP Address");
        }
    }

    private void portFocusGained(FocusEvent e) {
        // TODO add your code here
        port.setForeground(Color.black);
        if (port.getText().isEmpty() || port.getText().equals(" Port Number")) {
            port.setText(""); // hint 없애기
        }
    }

    private void portFocusLost(FocusEvent e) {
        // TODO add your code here
        if (port.getText().isEmpty()) { // 비어있으면 hint 출력
            port.setForeground(new Color(0x999999));
            port.setText(" Port Number");
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        panel1 = new JPanel();
        logo = new JLabel();
        name = new JTextField();
        ip = new JTextField();
        port = new JTextField();
        connect = new JButton();

        //======== this ========
        setResizable(false);
        var contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== panel1 ========
        {
            panel1.setBackground(new Color(0x1c3879));
            panel1.setLayout(null);

            //---- logo ----
            logo.setHorizontalAlignment(SwingConstants.CENTER);
            logo.setBackground(new Color(0xffeb3b));
            panel1.add(logo);
            logo.setBounds(76, 35, 230, 230);

            //---- name ----
            name.setBackground(Color.white);
            name.setForeground(Color.black);
            name.setBorder(new EmptyBorder(5, 5, 5, 5));
            name.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    nameFocusGained(e);
                }
                @Override
                public void focusLost(FocusEvent e) {
                    nameFocusLost(e);
                }
            });
            panel1.add(name);
            name.setBounds(72, 275, 240, 35);

            //---- ip ----
            ip.setText("127.0.0.1");
            ip.setBackground(Color.white);
            ip.setForeground(Color.black);
            ip.setBorder(new EmptyBorder(5, 5, 5, 5));
            ip.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    ipFocusGained(e);
                }
                @Override
                public void focusLost(FocusEvent e) {
                    ipFocusLost(e);
                }
            });
            panel1.add(ip);
            ip.setBounds(72, 315, 240, 35);

            //---- port ----
            port.setText("30000");
            port.setBackground(Color.white);
            port.setForeground(Color.black);
            port.setBorder(new EmptyBorder(5, 5, 5, 5));
            port.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    portFocusGained(e);
                }
                @Override
                public void focusLost(FocusEvent e) {
                    portFocusLost(e);
                }
            });
            panel1.add(port);
            port.setBounds(72, 355, 240, 35);

            //---- connect ----
            connect.setText("\ub85c\uadf8\uc778");
            connect.setBackground(new Color(0xf9f5eb));
            connect.setForeground(Color.black);
            connect.setBorder(null);
            panel1.add(connect);
            connect.setBounds(67, 423, 250, 45);
        }
        contentPane.add(panel1);
        panel1.setBounds(0, 0, 385, 572);

        contentPane.setPreferredSize(new Dimension(385, 602));
        setSize(385, 602);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel panel1;
    private JLabel logo;
    private JTextField name;
    private JTextField ip;
    private JTextField port;
    private JButton connect;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    class MyAction implements ActionListener // 내부클래스로 액션 이벤트 처리 클래스
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = name.getText().trim();
            String ip_addr = ip.getText().trim();
            String port_no = port.getText().trim();
            ChatClientMainView view = new ChatClientMainView(username, ip_addr, port_no);
            setVisible(false);
        }
    }
}
