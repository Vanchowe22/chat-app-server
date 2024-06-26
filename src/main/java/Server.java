
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Server {
    private static ServerSocket server;
    private static final UserList userList = new UserList();
    private static final MessageList messageList = new MessageList();
    private static final ArrayList<Handler> handlers = new ArrayList<>();

    static public void main(String args[]) throws IOException {
        Scanner scanner = new Scanner(System.in);
        server = new ServerSocket(Integer.parseInt(scanner.nextLine()));
        scanner.close();
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
            messageList.add(message);
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
