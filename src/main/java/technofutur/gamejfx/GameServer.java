package technofutur.gamejfx;

import javafx.application.Platform;
import javafx.scene.control.ListView;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class GameServer {
    private static final int PORT = 5000;
    private List<Socket> clients = new ArrayList<>();
    private ListView<String> playerListView;

    public GameServer(ListView<String> playerListView) {
        this.playerListView = playerListView;
    }

    public void start() {
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(PORT)) {
                System.out.println("Serveur en attente de connexions...");
                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    clients.add(clientSocket);
                    Platform.runLater(() -> {
                        playerListView.getItems().add("Joueur " + clients.size());
                    });
                    System.out.println("Nouveau joueur connecté !");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
