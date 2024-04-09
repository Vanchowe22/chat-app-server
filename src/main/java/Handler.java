import java.io.*;
import java.net.Socket;

public class Handler implements Runnable {
    private BufferedReader br;
    private PrintWriter pr;
    private Socket client;
    private UserList userList;
    private final Logger logger = new Logger();

    private final Helper helper = new Helper();

    public Handler(Socket client, UserList userList) {
        this.client = client;
        this.userList = userList;
    }


    @Override
    public void run() {
        try {
            this.br = new BufferedReader(new InputStreamReader(client.getInputStream()));
            this.pr = new PrintWriter(client.getOutputStream(), true);
            String id_string = Thread.currentThread().getName();
            int id = (int) id_string.charAt(id_string.length() - 1);

            String nickname = br.readLine();
            if (!nickname.startsWith("C")) {
                this.endSession();
                return;
            }
            logger.info(Thread.currentThread().getName() + " started");

            nickname = nickname.substring(2);
            userList.add(id, nickname);

            logger.info(nickname + " is added");
            helper.updateList(userList.getUserList());

            logger.info(nickname + " is added to the list");

            helper.someoneJoined(nickname);
            helper.sendAllMessages(pr, MessageList.getMessages());

            String text;
            while ((text = br.readLine()) != null) {
                if (text.startsWith("T")) {
                    logger.info(text);
                    helper.sendMessage(text.substring(2));
                } else if (text.startsWith("L")) {
                    userList.removeUser(nickname);
                    logger.info(nickname + " disconnected");

                    helper.updateList(userList.getUserList());
                    helper.someoneLeft(text.substring(2));
                    this.endSession();
                }
            }
        } catch (AlreadyHereException e) {
            logger.warn("N:Already added");
            pr.println("N:Already added");
        } catch (IOException e) {
            logger.severe(e.getMessage());
        } finally {
            this.endSession();
        }
    }

    private void endSession() {
        try {
            this.client.close();
            this.br.close();
            this.pr.close();

        } catch (Exception e) {
            logger.severe(e.getMessage());
        }
    }

    public PrintWriter getPr() {
        return pr;
    }
}

