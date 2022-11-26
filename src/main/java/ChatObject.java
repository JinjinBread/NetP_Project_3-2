import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Vector;

// ChatObject.java 채팅 메시지 ObjectStream 용.
public class ChatObject implements Serializable {
    private static final long serialVersionUID = 1L;
    public String code; // 100:로그인, 400:로그아웃, 200:채팅메시지, 300:Image, 500: Mouse Event
    public String UserName;
    public String data;
    public ImageIcon img;
    public String status;
    public String statusMsg;
    public int room_id;
    //public boolean isOnline;
    public String userlist = "";
    public Date date;

    public ChatObject() {};
    public ChatObject(String UserName, String code, String msg) {
        this.code = code;
        this.UserName = UserName;
        this.data = msg;
    }
    public ChatObject(String UserName, String code, String msg, String user_list, int room_id) {
        this.code = code;
        this.UserName = UserName;
        this.data = msg;
        this.userlist = user_list;
        this.room_id = room_id;
    }
}