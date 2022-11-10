import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
/*
 * Created by JFormDesigner on Thu Nov 10 13:39:34 KST 2022
 */



/**
 * @author unknown
 */
public class ChatServer extends JFrame {
    private ServerSocket socket;
    private Socket client_socket;
    private Vector UserVec = new Vector();
    public ChatServer() {
        initComponents();

    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        label1 = new JLabel();
        ip = new JTextField();
        label2 = new JLabel();
        port = new JTextField();
        startBtn = new JButton();
        scrollPane1 = new JScrollPane();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- label1 ----
        label1.setText("IP");
        contentPane.add(label1);
        label1.setBounds(new Rectangle(new Point(60, 355), label1.getPreferredSize()));
        contentPane.add(ip);
        ip.setBounds(145, 350, 150, 30);

        //---- label2 ----
        label2.setText("Port Number");
        contentPane.add(label2);
        label2.setBounds(60, 410, 80, 17);
        contentPane.add(port);
        port.setBounds(145, 405, 150, 30);

        //---- startBtn ----
        startBtn.setText("Server Start");
        contentPane.add(startBtn);
        startBtn.setBounds(new Rectangle(new Point(130, 485), startBtn.getPreferredSize()));
        contentPane.add(scrollPane1);
        scrollPane1.setBounds(20, 20, 325, 305);

        {
            // compute preferred size
            Dimension preferredSize = new Dimension();
            for(int i = 0; i < contentPane.getComponentCount(); i++) {
                Rectangle bounds = contentPane.getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = contentPane.getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            contentPane.setMinimumSize(preferredSize);
            contentPane.setPreferredSize(preferredSize);
        }
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JLabel label1;
    private JTextField ip;
    private JLabel label2;
    private JTextField port;
    private JButton startBtn;
    private JScrollPane scrollPane1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    class AcceptServer extends Thread {
        public void run() {
            while (true) { // 사용자 접속을 계속해서 받기 위해 while문
                try {
                    AppendText("Waiting new clients ...");
                    client_socket = socket.accept(); // accept가 일어나기 전까지는 무한 대기중
                    AppendText("새로운 참가자 from " + client_socket);
                    // User 당 하나씩 Thread 생성
                    UserService new_user = new UserService(client_socket);
                    UserVec.add(new_user); // 새로운 참가자 배열에 추가
                    new_user.start(); // 만든 객체의 스레드 실행
                    AppendText("현재 참가자 수 " + UserVec.size());
                } catch (IOException e) {
                    AppendText("accept() error");
                    // System.exit(0);
                }
            }
        }
    }

    class UserService extends Thread {
        public UserService(Socket client_socket) {

        }
    }

    public void AppendText(String str) {

    }
}
