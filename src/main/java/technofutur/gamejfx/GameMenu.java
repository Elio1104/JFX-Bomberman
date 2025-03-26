package technofutur.gamejfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

public class GameMenu extends Application {
    private Player player;

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("profile.fxml"));
        Parent profile = loader.load();

        player = Player.playerFromJson("src/main/resources/save.json");
        if (player == null) {
            System.err.println("Erreur de chargement du joueur à partir du JSON.");
            player = new Player("Joueur par défaut", 0, 0);
        }

        ProfileController controller = loader.getController();
        controller.setPlayer(player);

        // Structure propre : StackPane racine
        StackPane root = new StackPane();

        // BorderPane centre pour gérer précisément les composants principaux
        BorderPane mainContentPane = new BorderPane();
        mainContentPane.setStyle(
                "-fx-background-image: url('Wallpaper3.jpg');" +
                        "-fx-background-size: cover;" +
                        "-fx-background-position: center center;"
        );

        // Centre : boutons centrés parfaitement
        HBox mainMenuBox = new HBox(20);
        mainMenuBox.setAlignment(Pos.CENTER);

        MenuHost menuView = new MenuHost();
        RoomHost roomView = new RoomHost();
        mainMenuBox.getChildren().add(menuView.getMenu());

        mainContentPane.setCenter(mainMenuBox);

        // Solution claire idéale avec AnchorPane
        AnchorPane profileWrapper = new AnchorPane(profile);
        AnchorPane.setTopAnchor(profile, 15.0);
        AnchorPane.setRightAnchor(profile, 15.0);

// Ajout immédiat, précis et explicite
        root.getChildren().addAll(mainContentPane, profileWrapper);

        // Événements inchangés (Host / Join)
        menuView.getHostBtn().setOnAction(e -> {
            menuView.getMenu().setVisible(false);
            roomView.getGameRoom().setVisible(true);
            if (!mainMenuBox.getChildren().contains(roomView.getGameRoom())) {
                mainMenuBox.getChildren().add(roomView.getGameRoom());
            }

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

        primaryStage.setOnCloseRequest(event -> {
            System.out.println("Fermeture du jeu...");
            System.exit(0);
        });

        Scene scene = new Scene(root, 1400, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Game Menu");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}