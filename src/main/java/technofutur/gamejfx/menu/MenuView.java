package technofutur.gamejfx.menu;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class MenuView {

    private VBox menuLayout;
    private Button hostButton;
    private Button joinButton;

    public MenuView() {
        // Initialisation des boutons
        hostButton = new Button("📡 Héberger");
        joinButton = new Button("🌍 Rejoindre");

        // Application du style aux boutons
        ButtonStyle.applyButtonStyle(hostButton, "#ff5722", "#bf360c");
        ButtonStyle.applyButtonStyle(joinButton, "#2196f3", "#0d47a1");

        // Layout du menu principal
        menuLayout = new VBox(20, hostButton, joinButton);
        menuLayout.setAlignment(Pos.CENTER);
        menuLayout.setStyle("-fx-background-color: radial-gradient(radius 100%, #ffcc00, #ee1515, #900303);");
    }

    // Getters
    public VBox getMenuLayout() {
        return menuLayout;
    }

    public Button getHostButton() {
        return hostButton;
    }

    public Button getJoinButton() {
        return joinButton;
    }
}

