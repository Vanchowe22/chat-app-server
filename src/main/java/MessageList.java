import java.util.ArrayList;

public class MessageList {
    private static final ArrayList<Message> messageList = new ArrayList<>();

    public void add(Message message) {
        synchronized (messageList) {
            messageList.add(message);
        }
    }

    public static String getMessages() {
        StringBuilder userListString = new StringBuilder();
        synchronized (messageList) {
            for (Message message : messageList) {
                userListString.append("(" + message.getTime() + ") " + message.getText()).append("|");
            }
        }
        if (userListString.length() > 0) {
            userListString.deleteCharAt(userListString.length() - 1);
        }
        return userListString.toString();
    }
}
