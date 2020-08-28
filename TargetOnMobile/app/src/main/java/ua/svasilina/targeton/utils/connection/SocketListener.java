package ua.svasilina.targeton.utils.connection;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import ua.svasilina.targeton.utils.subscribes.Subscriber;

public class SocketListener extends WebSocketListener {

    private final Subscriber subscriber = Subscriber.getInstance();

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        System.out.println("SOCKET OPEN");
    }

    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
        subscriber.handle(text);
    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        super.onMessage(webSocket, bytes);
        System.out.println("Byte message " + bytes.toString());
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, @Nullable Response response) {
        t.printStackTrace();
    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
        super.onClosed(webSocket, code, reason);
        System.out.println("Socket is closed");
    }
}
