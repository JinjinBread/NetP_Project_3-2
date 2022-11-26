import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;
import javax.swing.*;
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
        Image img = new ImageIcon("resources/logo.png").getImage().getScaledInstance(logo.getWidth(), logo.getHeight(), Image.SCALE_SMOOTH);
        logo.setIcon(new ImageIcon(img));
        MyAction action = new MyAction();
        connect.addActionListener(action);
        name.addActionListener(action);
        ip.addActionListener(action);
        port.addActionListener(action);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        panel1 = new JPanel();
        logo = new JLabel();
        name = new JTextField();
        ip = new JTextField();
        port = new JTextField();
        connect = new JButton();
        userName = new JLabel();
        ipAddress = new JLabel();
        portNumber = new JLabel();

        //======== this ========
        setResizable(false);
        var contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== panel1 ========
        {
            panel1.setLayout(null);

            //---- logo ----
            logo.setText("\ub85c\uace0\uc790\ub9ac");
            logo.setHorizontalAlignment(SwingConstants.CENTER);
            panel1.add(logo);
            logo.setBounds(75, 50, 230, 230);
            panel1.add(name);
            name.setBounds(160, 320, 155, 30);

            //---- ip ----
            ip.setText("127.0.0.1");
            panel1.add(ip);
            ip.setBounds(160, 370, 155, 30);

            //---- port ----
            port.setText("30000");
            panel1.add(port);
            port.setBounds(160, 420, 155, 30);

            //---- connect ----
            connect.setText("\ub85c\uadf8\uc778");
            panel1.add(connect);
            connect.setBounds(70, 480, 245, 35);

            //---- userName ----
            userName.setText("User Name");
            panel1.add(userName);
            userName.setBounds(75, 324, 80, 20);

            //---- ipAddress ----
            ipAddress.setText("IP Address");
            panel1.add(ipAddress);
            ipAddress.setBounds(75, 373, 80, 20);

            //---- portNumber ----
            portNumber.setText("Port Number");
            panel1.add(portNumber);
            portNumber.setBounds(75, 423, 80, 20);
        }
        contentPane.add(panel1);
        panel1.setBounds(0, 0, 385, 572);

        contentPane.setPreferredSize(new Dimension(385, 602));
        pack();
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
    private JLabel userName;
    private JLabel ipAddress;
    private JLabel portNumber;
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
