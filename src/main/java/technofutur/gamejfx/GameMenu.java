package technofutur.gamejfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

public class GameMenu extends Application {

    private Player player;
    private BorderPane mainPage, roomPage;
    private AnchorPane profileWrapper;
    private MenuHost menuView;
    private RoomHost roomView;
    private GameClient client;
    private GameServer server;

    @Override
    public void start(Stage primaryStage) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("profile.fxml"));
        Parent profile = loader.load();
        player = Player.playerFromJson("src/main/resources/save.json");
        if (player == null) {
            System.err.println("Erreur JSON player");
            player = new Player("Défaut", 0, 0, "/technofutur/gamejfx/default-profile.jpg");
        }
        loader.<ProfileController>getController().setPlayer(player);

        StackPane root = new StackPane();

        menuView = new MenuHost();
        mainPage = new BorderPane(new HBox(20, menuView.getMenu()));
        mainPage.setStyle(
                "-fx-background-image: url('Wallpaper3.jpg');" +
                        "-fx-background-size: cover;" +
                        "-fx-background-position: center;"
        );
        ((HBox) mainPage.getCenter()).setAlignment(Pos.CENTER);

        roomView = new RoomHost();
        roomPage = new BorderPane(new HBox(20, roomView.getGameRoom()));
        roomPage.setStyle(
                "-fx-background-image: url('Wallpaper3.jpg');" +
                        "-fx-background-size: cover;" +
                        "-fx-background-position: center;"
        );
        ((HBox) roomPage.getCenter()).setAlignment(Pos.CENTER);
        roomPage.setVisible(false);


        profileWrapper = new AnchorPane(profile);
        AnchorPane.setTopAnchor(profile, 15.0);
        AnchorPane.setRightAnchor(profile, 15.0);
        profileWrapper.setPickOnBounds(false);

        root.getChildren().addAll(mainPage, roomPage, profileWrapper);

        menuView.getHostBtn().setOnAction(e -> showRoomPage());

        menuView.getJoinBtn().setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog("127.0.0.1");
            dialog.setTitle("Connexion");
            dialog.setHeaderText("Rejoindre une partie");
            dialog.setContentText("Entrez l'adresse IP du serveur:");

            dialog.showAndWait().ifPresent(ip -> {
                client = new GameClient(ip);
                client.connect();
            });
        });

        primaryStage.setOnCloseRequest(event -> {
            if (client != null)
                client.disconnect();
            System.exit(0);
        });

        Scene scene = new Scene(root, 1400, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Game Menu");
        primaryStage.show();
    }

    private void showRoomPage() {
        mainPage.setVisible(false);
        roomPage.setVisible(true);
        profileWrapper.setVisible(false);
        roomView.getGameRoom().setVisible(true);

        roomView.afficherPseudoHote(player.getPseudo()); // player chargé depuis JSON au lancement du jeu
        server = new GameServer(roomView, player); // Si tu utilises un constructeur avec Player
        server.start();
    }


    public static void main(String[] args) {
        launch(args);
    }
}