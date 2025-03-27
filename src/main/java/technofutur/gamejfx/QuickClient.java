package technofutur.gamejfx;

public class QuickClient {
    public static void main(String[] args) {
        String ip = "127.0.0.1";

        Player testPlayer = new Player("Testeur", 10, 7, "/Wallpaper2.jpg"); // Utilisez une image existante.

        GameClient quickClient = new GameClient(ip);
        quickClient.connect(testPlayer);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            quickClient.disconnect();
            System.out.println("Client déconnecté proprement à la fermeture.");
        }));

        synchronized (QuickClient.class) {
            try {
                QuickClient.class.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Interruption du thread QuickClient !");
            }
        }
    }
}
