package message.bean;

public class TextMessage {
    public String text;
    public From from;
    public String time;

    public static class From {
        public String clientId;
        public String name;
    }
}
