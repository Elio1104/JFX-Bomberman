package technofutur.gamejfx.menu;

import javafx.scene.control.Button;

public class ButtonStyle {

    // Applique le style aux boutons
    public static void applyButtonStyle(Button button, String colorStart, String colorEnd) {
        button.setStyle(
                "-fx-background-color: linear-gradient(to bottom, " + colorStart + ", " + colorEnd + ");" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 18px;" +
                        "-fx-padding: 10px 30px;" +
                        "-fx-background-radius: 8px;" +
                        "-fx-border-color: white;" +
                        "-fx-border-width: 2px;"
        );
    }
}

