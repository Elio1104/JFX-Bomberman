package technofutur.gamejfx;

import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class RoomHost {

    private final VBox gameRoom;
    private final ListView<String> playerList;
    private Text roomStatus;

    public RoomHost() {
        // Initialisation des éléments de la salle
        playerList = new ListView<>();

        roomStatus = new Text("Salle en attente de joueurs...");

        gameRoom = new VBox(10, roomStatus, playerList);
        gameRoom.setAlignment(Pos.CENTER);
        gameRoom.setVisible(false);

        gameRoom.setMaxWidth(200);
        gameRoom.setMaxHeight(300);
    }

    // Getters
    public VBox getGameRoom() {
        return gameRoom;
    }

    public ListView<String> getPlayerList() {
        return playerList;
    }
}
