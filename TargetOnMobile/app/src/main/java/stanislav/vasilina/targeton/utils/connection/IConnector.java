package stanislav.vasilina.targeton.utils.connection;

import java.io.IOException;

public abstract class IConnector {
    public abstract void connect(String address) throws IOException;
    public abstract void send(String message);
    public abstract void close();
}
