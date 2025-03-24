package technofutur.gamejfx;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GameMenu extends Application {

    @Override
    public void start(Stage primaryStage) {

        StackPane root = new StackPane();
        root.setAlignment(Pos.CENTER);

        root.setStyle(
                "-fx-background-image: url('Wallpaper3.jpg');" +
                        "-fx-background-size: cover;" +
                        "-fx-background-position: center center;"
        );

        MenuHost menuView = new MenuHost();
        root.getChildren().add(menuView.getMenu());

        RoomHost roomView = new RoomHost();
        root.getChildren().add(roomView.getGameRoom());

        menuView.getHostBtn().setOnAction(e -> {
            menuView.getMenu().setVisible(false);
            roomView.getGameRoom().setVisible(true);  // Affiche la salle

            // Lancer le serveur
            GameServer server = new GameServer(roomView.getPlayerList());
            server.start();
        });

        menuView.getJoinBtn().setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog("127.0.0.1");
            dialog.setTitle("Connexion");
            dialog.setHeaderText("Rejoindre une partie");
            dialog.setContentText("Entrez l'adresse IP du serveur:");

            dialog.showAndWait().ifPresent(ip -> {
                GameClient client = new GameClient(ip);
                client.connect();
            });
        });

        //Screen prep
        Scene scene = new Scene(root, 1400, 800);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Game Menu");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}