package message;

import com.google.gson.Gson;
import message.bean.TextMessage;

import java.util.HashSet;
import java.util.Set;

public class MessageConversation {
    private Set<MessageClient> clients = new HashSet<>();

    public MessageConversation() {
    }

    public void registerClient(MessageClient client) {
        clients.add(client);
    }

    public void unregisterClient(MessageClient client) {
        clients.remove(client);
    }

    //
    public MessageClient findClient(String clientId) {
        return clients.stream().filter(client -> client.id.equals(clientId)).findFirst().orElse(null);
    }

    //
    public void dispatchMessage(TextMessage textMessage) {
        clients.forEach(client -> {
            if (!textMessage.from.clientId.equals(client.id)) {
                client.session.getAsyncRemote().sendText(new Gson().toJson(textMessage));
            }
        });
    }
}
