import java.util.logging.*;

public class Logger {
    private static final java.util.logging.Logger serverLogger = java.util.logging.Logger.getLogger("ServerLogger");
    private FileHandler serverFileHandler;
    private SimpleFormatter formatter;

    public Logger() {
        try {
            serverFileHandler = new FileHandler("server.log");
            formatter = new SimpleFormatter();
            serverFileHandler.setFormatter(formatter);
            serverLogger.addHandler(serverFileHandler);
        } catch (Exception e) {
            serverLogger.info(e.getMessage());
        }
    }

    public void info(String text) {
        serverLogger.info(text);
    }

    public void warn(String text) {
        serverLogger.warning(text);
    }

    public void severe(String text) {
        serverLogger.severe(text);
    }
}
