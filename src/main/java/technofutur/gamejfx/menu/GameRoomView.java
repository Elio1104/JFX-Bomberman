package technofutur.gamejfx.menu;

import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class GameRoomView {

    private VBox gameRoomLayout;
    private ListView<String> playerList;
    private Text roomStatus;

    public GameRoomView() {
        // Initialisation des éléments de la salle
        roomStatus = new Text("Salle en attente de joueurs...");
        playerList = new ListView<>();
        playerList.setPrefHeight(200);

        // Layout de la salle de jeu
        gameRoomLayout = new VBox(10, roomStatus, playerList);
        gameRoomLayout.setAlignment(Pos.CENTER);
        gameRoomLayout.setVisible(false); // Initialement cachée
    }

    // Getters
    public VBox getGameRoomLayout() {
        return gameRoomLayout;
    }

    public ListView<String> getPlayerList() {
        return playerList;
    }
}

