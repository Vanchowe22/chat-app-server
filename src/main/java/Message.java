import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {
    private String text;
    private Date date;

    public Message(String text) {
        this.text = text;
        this.date = new Date();
    }

    public String getText() {
        return text;
    }

    public String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(date);
    }
}
