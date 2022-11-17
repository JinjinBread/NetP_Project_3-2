import java.util.Vector;

public class ChatRoom {
    private String room_id;
    private Vector<ChatClientHome> userlist;

    public ChatRoom(String room_id, Vector<ChatClientHome> userlist) {
        this.room_id = room_id;
        this.userlist = userlist;
    }
}
