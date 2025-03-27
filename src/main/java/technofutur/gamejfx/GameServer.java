package technofutur.gamejfx;

import javafx.application.Platform;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class GameServer {
    private static final int PORT = 5000;
    private final List<ClientHandler> clients = new ArrayList<>();
    private final RoomHost roomHost;
    private final AtomicInteger playerIdCounter = new AtomicInteger(1);
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
        new Thread(() -> {
            try {
                ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                Player receivedPlayer = (Player) input.readObject();
                int playerId = playerIdCounter.getAndIncrement();

                ClientHandler clientHandler = new ClientHandler(socket, receivedPlayer, playerId, input);
                clients.add(clientHandler);
                clientHandler.start();

                Platform.runLater(() -> roomHost.updatePlayerSlot(playerId, receivedPlayer));
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void removeClient(ClientHandler clientHandler) {
        clients.remove(clientHandler);
        Platform.runLater(() -> roomHost.setSlotToWaiting(clientHandler.playerId));
        playerIdCounter.decrementAndGet();
    }

    private class ClientHandler extends Thread {
        private final Socket socket;
        private final int playerId;
        private final Player player;
        private final ObjectInputStream input;

        ClientHandler(Socket socket, Player player, int playerId, ObjectInputStream input) {
            this.socket = socket;
            this.player = player;
            this.playerId = playerId;
            this.input = input;
        }

        @Override
        public void run() {
            System.out.println(player.getPseudo() + " connecté depuis : " + socket.getInetAddress());

            try {
                while (!socket.isClosed()) {
                    Object message = input.readObject();
                    System.out.println(player.getPseudo() + " a envoyé un message : " + message);
                }
            } catch (IOException e) {
                System.out.println(player.getPseudo() + " déconnecté (" + e.getMessage() + ")");
            } catch (ClassNotFoundException e) {
                System.err.println("Erreur de classe du message reçu : " + e.getMessage());
            } finally {
                removeClient(this);
                try {
                    input.close();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(player.getPseudo() + " a quitté la salle proprement.");
                Platform.runLater(() -> roomHost.setSlotToWaiting(playerId));
            }
        }
    }
}
