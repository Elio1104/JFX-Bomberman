package technofutur.gamejfx;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RoomHost {
    private final VBox gameRoom;
    private final GridPane playerGrid;
    private final List<ProfileController> profileControllers = new ArrayList<>();

    public RoomHost() {
        Text roomStatus = new Text("Salle en attente de joueurs...");

        playerGrid = new GridPane();
        playerGrid.setAlignment(Pos.CENTER);
        playerGrid.setHgap(20);
        playerGrid.setVgap(20);

        try {
            for (int i = 0; i < 4; i++) {
                StackPane playerSlot = createPlayerSlot();
                Player waitingPlayer = new Player("En attente...", 0, 0, "/technofutur/gamejfx/default-profile.jpg");
                profileControllers.get(i).setPlayer(waitingPlayer);
                playerGrid.add(playerSlot, i % 2, i / 2);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        gameRoom = new VBox(20, roomStatus, playerGrid);
        gameRoom.setAlignment(Pos.CENTER);
        gameRoom.setVisible(false);
    }

    private StackPane createPlayerSlot() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("profile.fxml"));

        // Séparation en 2 étapes explicites pour éviter ambiguïté :
        HBox playerHBox = loader.load(); // <— Charge précisément un HBox
        StackPane playerPane = new StackPane(playerHBox); // <— Passe explicitement comme enfant unique (OK ✅)

        playerPane.setPrefSize(250, 140);

        ProfileController controller = loader.getController();
        profileControllers.add(controller);

        return playerPane;
    }


    public void afficherPseudoHote(String hostPseudo) {
        profileControllers.get(0).setPlayer(Player.playerFromJson("src/main/resources/save.json"));
    }

    public void updatePlayerSlot(int playerIndex, Player player) {
        if (playerIndex >= 0 && playerIndex < profileControllers.size()) {
            profileControllers.get(playerIndex).setPlayer(player);
        }
    }

    public void setSlotToWaiting(int playerIndex) {
        if (playerIndex >= 0 && playerIndex < profileControllers.size()) {
            profileControllers.get(playerIndex).setPlayer(new Player("En attente...", 0, 0, "/technofutur/gamejfx/default-profile.jpg"));
        }
    }

    // Getter
    public VBox getGameRoom() {
        return gameRoom;
    }
}