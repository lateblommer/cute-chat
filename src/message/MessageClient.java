package message;

import javax.websocket.Session;
import java.util.Objects;

public class MessageClient {
    public String id;
    public Session session;
    public String name;

    public MessageClient(String id, Session session) {
        this.id = id;
        this.session = session;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageClient that = (MessageClient) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
