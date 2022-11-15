import javax.swing.*;
import java.io.Serializable;

// ChatObject.java 채팅 메시지 ObjectStream 용.
public class ChatObject implements Serializable {
    private static final long serialVersionUID = 1L;
    public String code; // 100:로그인, 400:로그아웃, 200:채팅메시지, 300:Image, 500: Mouse Event
    public String UserName;
    public String data;
    public ImageIcon img;
    public String status = null;
    public boolean isOnline;


    public ChatObject(String UserName, String code, String msg) {
        this.code = code;
        this.UserName = UserName;
        this.data = msg;
    }


}