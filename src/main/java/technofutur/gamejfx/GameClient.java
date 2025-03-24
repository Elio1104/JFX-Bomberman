package technofutur.gamejfx;

import java.io.IOException;
import java.net.Socket;

public class GameClient {
    private String serverIP;
    private static final int PORT = 5000;

    public GameClient(String serverIP) {
        this.serverIP = serverIP;
    }

    public void connect() {
        new Thread(() -> {
            try {
                Socket socket = new Socket(serverIP, PORT);
                System.out.println("Connecté au serveur !");
            } catch (IOException e) {
                System.out.println("Impossible de se connecter au serveur !");
            }
        }).start();
    }
}

