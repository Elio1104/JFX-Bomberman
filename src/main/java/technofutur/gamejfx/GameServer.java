package technofutur.gamejfx;

import javafx.application.Platform;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class GameServer {
    private static final int PORT = 5000;
    private final List<ClientHandler> clients = new ArrayList<>();
    private final RoomHost roomHost;
    private AtomicInteger playerIdCounter = new AtomicInteger(1);
    private final Player hostPlayer;

    public GameServer(RoomHost roomHost, Player hostPlayer) {
        this.roomHost = roomHost;
        this.hostPlayer = hostPlayer;
    }

    public void start() {
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(PORT)) {
                System.out.println("Serveur en attente de connexions...");
                while (clients.size() < 3) { // maximum 4 joueurs au total (Host + 3 joueurs)
                    Socket clientSocket = serverSocket.accept();
                    handleClientConnection(clientSocket);
                }
                System.out.println("Salle pleine (4 joueurs). Aucune autre connexion acceptée pour le moment.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void handleClientConnection(Socket socket) {
        int playerId = playerIdCounter.getAndIncrement();

        // Création d'un joueur par défaut avec pseudo et stats initiales
        Player newPlayer = new Player("Joueur " + (playerId + 1), 0, 0, "/technofutur/gamejfx/default-profile.jpg");

        ClientHandler clientHandler = new ClientHandler(socket, newPlayer, playerId);
        clients.add(clientHandler);
        clientHandler.start();

        Platform.runLater(() -> roomHost.updatePlayerSlot(playerId, newPlayer));
    }

    private void removeClient(ClientHandler clientHandler) {
        clients.remove(clientHandler);
        Platform.runLater(() -> {
            roomHost.setSlotToWaiting(clientHandler.playerId);
        });
        playerIdCounter.decrementAndGet();
    }

    // Classe interne pour gérer chaque joueur
    private class ClientHandler extends Thread {
        private final Socket socket;
        private final int playerId;
        private final Player player;

        ClientHandler(Socket socket, Player player, int playerId) {
            this.socket = socket;
            this.player = player;
            this.playerId = playerId;
        }

        @Override
        public void run() {
            System.out.println(player.getPseudo() + " connecté depuis : " + socket.getInetAddress());


            try {
                // Attend simplement que la connexion soit coupée par le joueur
                socket.getInputStream().read(); // bloquant jusqu'à déconnexion

            } catch (IOException e) {
                System.out.println(player.getPseudo() + " s'est déconnecté (erreur).");
            } finally {
                try {
                    socket.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                removeClient(this);
                System.out.println(player.getPseudo() + " a quitté la salle.");
            }
        }
    }
}
