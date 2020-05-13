package stanislav.vasilina.targeton.utils.connection;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

public class SocketConnector extends IConnector{

    private WebSocket socket;
    @Override
    public void connect(final String address) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        SocketListener listener = new SocketListener();
        socket = client.newWebSocket(request, listener);

        client.dispatcher().executorService().shutdown();
    }

    @Override
    public void send(String message) {
        socket.send(message);
    }

    @Override
    public void close() {
        socket.close(1000, "Ololo");
    }
}
