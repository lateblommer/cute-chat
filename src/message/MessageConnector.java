package message;

import com.google.gson.Gson;
import message.bean.TextMessage;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ServerEndpoint("/message/{clientId}")
public class MessageConnector {

    private static MessageConversation conversation = new MessageConversation();
    private String clientId;

    @OnOpen
    public void onOpen(@PathParam("clientId") String clientId, Session session) throws IOException {
        this.clientId = clientId;
        conversation.unregisterClient(conversation.findClient(clientId));
        conversation.registerClient(new MessageClient(clientId, session));
        System.out.println("新的客户端已登入：" + clientId);
    }

    @OnClose
    public void onClose() throws IOException {
        conversation.unregisterClient(conversation.findClient(clientId));
        System.out.println("客户端已退出：" + clientId);
    }

    @OnMessage
    public void onMessage(String message) throws IOException {
        System.out.println("收到消息：" + message);
        TextMessage textMessage = new Gson().fromJson(message, TextMessage.class);
        textMessage.time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        conversation.dispatchMessage(textMessage);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        conversation.unregisterClient(conversation.findClient(clientId));
        System.out.println("客户端意外退出：" + clientId);
        error.printStackTrace();
    }
}
