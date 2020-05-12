package api.socket.handlers;

import api.socket.Subscribe;
import entity.user.User;

import javax.websocket.Session;

/**
 * Created by szpt_user045 on 17.02.2020.
 */
public class MessageHandler extends ISocketHandler {

    public MessageHandler(Subscribe messages) {
        super(messages);
    }

    @Override
    public void onSubscribe(User user, Session session) {
        //todo return contacts, messages, etc.
    }
}
