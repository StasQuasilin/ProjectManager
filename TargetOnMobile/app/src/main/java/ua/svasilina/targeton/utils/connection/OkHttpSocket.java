package ua.svasilina.targeton.utils.connection;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import ua.svasilina.targeton.utils.constants.API;

public class OkHttpSocket {

    public WebSocket connect(){
        final String address = API.SUBSCRIBER;
        System.out.println("Open socket on: " + address);
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(address)
                .build();

        final WebSocket webSocket = client.newWebSocket(request, new SocketListener());

        client.dispatcher().executorService().shutdown();
        return webSocket;
    }
}
