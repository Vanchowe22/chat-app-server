
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;

public class Server {
    private static ServerSocket server = null;
    private static int port = 8000;
    private static final UserList userList = new UserList();

    private static final MessageList messageList = new MessageList();
    private static final ArrayList<Handler> handlers = new ArrayList<>();

    static public void main(String args[]) throws IOException {
        server = new ServerSocket(port);
        while (true) {
            Socket socket = server.accept();
            Handler handler = new Handler(socket, userList);
            synchronized (handlers) {
                handlers.add(handler);
            }
            new Thread(handler).start();
        }
    }

    public static synchronized void broadcast(String message, String operation) {
        if (!Objects.equals(operation, "U:")) {
            messageList.add(new Message(message));
            for (Handler handler : handlers) {
                handler.getPr().println(operation + message);
            }
        } else {
            for (Handler handler : handlers) {
                handler.getPr().println(operation + message);
            }
        }
    }
}
