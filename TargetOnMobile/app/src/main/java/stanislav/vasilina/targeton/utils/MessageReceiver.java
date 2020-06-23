package stanislav.vasilina.targeton.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class MessageReceiver implements Runnable {

    private Socket socket;
    private InputStream inputStream;
    private MessageHandler handler;
    private boolean isOpen;

    public MessageReceiver(Socket socket) {
        this.socket = socket;
        handler = MessageHandler.getInstance();
        isOpen = true;
    }

    @Override
    public void run() {
        try {
            inputStream = socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (isOpen){
            try {
                int bufferSize = 1024;
                byte[] buffer = new byte[bufferSize];
                int count = inputStream.read(buffer, 0, bufferSize);

                if (count > 0){
                    String msg = new String(buffer, 0, count);
                    handler.handleMessage(msg);
                } else if (count == -1){
                    socket.close();
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop(){
        isOpen = false;
    }
}
