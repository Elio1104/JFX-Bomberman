package technofutur.gamejfx;

import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class RoomHost {

    private final VBox gameRoom;
    private final ListView<String> playerList;

    public RoomHost() {
        playerList = new ListView<>();

        Text roomStatus = new Text("Salle en attente de joueurs...");

        gameRoom = new VBox(10, roomStatus, playerList);
        gameRoom.setAlignment(Pos.CENTER);
        gameRoom.setVisible(false);

        gameRoom.setMaxWidth(200);
        gameRoom.setMaxHeight(300);

        playerList.getItems().add("👑 Host");
    }

    // Getters
    public VBox getGameRoom() {
        return gameRoom;
    }

    public ListView<String> getPlayerList() {
        return playerList;
    }
}
