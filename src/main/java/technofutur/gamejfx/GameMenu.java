package technofutur.gamejfx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import technofutur.gamejfx.menu.GameRoomView;
import technofutur.gamejfx.menu.MenuView;

public class GameMenu extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Root container
        HBox root = new HBox();

        // Création des vues
        MenuView menuView = new MenuView();
        GameRoomView gameRoomView = new GameRoomView();

        // Ajout des vues au conteneur racine
        root.getChildren().addAll(menuView.getMenuLayout(), gameRoomView.getGameRoomLayout());

        // Actions pour les boutons
        menuView.getHostButton().setOnAction(e -> {
            menuView.getMenuLayout().setVisible(false);
            gameRoomView.getGameRoomLayout().setVisible(true);
            GameServer server = new GameServer(gameRoomView.getPlayerList());
            server.start();
        });

        menuView.getJoinButton().setOnAction(e -> {
            gameRoomView.getPlayerList().getItems().add("En attente...");
            // Connexion au serveur
            GameClient client = new GameClient("127.0.0.1");
            client.connect();
        });

        // Préparation de la scène
        Scene scene = new Scene(root, 700, 700);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Game Menu");
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


