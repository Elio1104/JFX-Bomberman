package technofutur.gamejfx;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class GameClient {
    private String serverIP;
    private static final int PORT = 5000;
    private Socket socket;
    private ObjectOutputStream output;

    public GameClient(String serverIP) {
        this.serverIP = serverIP;
    }

    public void connect(Player currentPlayer) {
        new Thread(() -> {
            try {
                socket = new Socket(serverIP, PORT);
                output = new ObjectOutputStream(socket.getOutputStream());
                output.writeObject(currentPlayer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void disconnect() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}