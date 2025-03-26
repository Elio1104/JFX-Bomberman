package technofutur.gamejfx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class ProfileController {

    @FXML private ImageView profileImage;
    @FXML private Label pseudoLabel;
    @FXML private Label winLabel;
    @FXML private Label looseLabel;

    private Player player;

    public void setPlayer(Player player) {
        this.player = Objects.requireNonNull(player, "Le Player chargé est null !");
        updateUI();
    }

    private void updateUI() {
        pseudoLabel.setText(player.getPseudo());
        winLabel.setText("Victoires : " + player.getWin());
        looseLabel.setText("Défaites : " + player.getLoose());

        try {
            String imagePath = player.getProfilePic();
            Image profilePicImage = new Image(Objects.requireNonNull(
                    getClass().getResourceAsStream(imagePath),
                    "Impossible de trouver l'image : " + imagePath
            ));
            profileImage.setImage(profilePicImage);
        } catch (Exception e) {
            System.err.println("Impossible de charger l'image du profil : " + e.getMessage());
            e.printStackTrace();
        }
    }
}