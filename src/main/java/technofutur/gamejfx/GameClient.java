package technofutur.gamejfx;

import java.io.IOException;
import java.net.Socket;

public class GameClient {
    private String serverIP;
    private static final int PORT = 5000;
    private Socket socket;

    public GameClient(String serverIP) {
        this.serverIP = serverIP;
    }

    public void connect() {
        new Thread(() -> {
            try {
                socket = new Socket(serverIP, PORT);
                System.out.println("Connecté au serveur !");
            } catch (IOException e) {
                System.out.println("Impossible de se connecter au serveur !");
            }
        }).start();
    }

    // Ajout cette methode à appeler en cas de fermeture propre coté client:
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

