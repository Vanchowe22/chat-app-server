import java.io.PrintWriter;

public class Controller {
    public void updateList(String users) {
        Server.broadcast(users, "U:");
    }

    public void someoneJoined(String user) {
        Server.broadcast(user + " is connected!", "J:");
    }

    public void someoneLeft(String user) {
        Server.broadcast(user + " has left.", "L:");
    }

    public void sendMessage(String text) {
        Server.broadcast(text, "T:");
    }

    public void sendAllMessages(PrintWriter pr, String texts) {
        pr.println("A:" + texts);
    }
}
