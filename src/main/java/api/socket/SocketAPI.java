package api.socket;

import constants.Branches;
import constants.Keys;
import entity.user.User;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import services.hibernate.dbDAO;
import services.hibernate.dbDAOService;
import utils.JsonParser;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by szpt_user045 on 17.02.2020.
 */
@ServerEndpoint(value = Branches.HOME + Branches.SOCKET_ADDRESS)
public class SocketAPI extends JsonParser implements Keys{

    final static ArrayList<Session> sessions = new ArrayList<>();
    final Logger logger = Logger.getLogger(SocketAPI.class);
    final SubscribeMaster subscribeMaster = SubscribeMaster.getMaster();
    final dbDAO dao = dbDAOService.getDao();

    @OnOpen
    public void OnOpen(Session session, EndpointConfig config){
        sessions.add(session);
    }

    @OnMessage
    public void OnMessage(Session session, String msg) throws ParseException, IOException {
        JSONObject json = parse(msg);
        if (json != null){
            System.out.println(json);
            if (json.containsKey(ACTION)){
                String action = String.valueOf(json.get(ACTION));
                switch (action){
                    case SUBSCRIBE:
                        if (json.containsKey(SUBSCRIBER)){
                            Subscribe subscribe = Subscribe.valueOf(String.valueOf(json.get(SUBSCRIBER)));
                            User user = dao.getObjectById(User.class, json.get(USER));
                            subscribeMaster.subscribe(subscribe, user, session);
                        }
                        break;
                    case UNSUBSCRIBE:
                        if (json.containsKey(SUBSCRIBER)){
                            Subscribe subscribe = Subscribe.valueOf(String.valueOf(json.get(SUBSCRIBER)));
                            User user = null;
                            subscribeMaster.unsubscribe(subscribe, user);
                        }
                        break;
                    default:
                        logger.info("Unknown action \'" + action + "\'");
                }
            }
        }
    }

    @OnClose
    public void onClose(Session session){
        sessions.remove(session);
    }
}
