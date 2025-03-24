package technofutur.gamejfx;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class MenuHost {
    private VBox menu;
    private Button hostBtn;
    private Button joinBtn;

    public MenuHost() {
        hostBtn = new Button("📡 Héberger");
        joinBtn = new Button("🌍 Rejoindre");

        hostBtn.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #ff5722, #bf360c);" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 18px;" +
                        "-fx-padding: 10px 30px;" +
                        "-fx-background-radius: 8px;" +
                        "-fx-border-color: white;" +
                        "-fx-border-width: 2px;"
        );

        joinBtn.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #2196f3, #0d47a1);" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 18px;" +
                        "-fx-padding: 10px 30px;" +
                        "-fx-background-radius: 8px;" +
                        "-fx-border-color: white;" +
                        "-fx-border-width: 2px;"
        );

        menu = new VBox(20, hostBtn, joinBtn);
        menu.setAlignment(Pos.CENTER);
    }

    public VBox getMenu() {
        return menu;
    }

    public Button getHostBtn() {
        return hostBtn;
    }

    public Button getJoinBtn() {
        return joinBtn;
    }
}
