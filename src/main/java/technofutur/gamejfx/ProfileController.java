package technofutur.gamejfx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;
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

        String imagePath = player.getProfilePic();
        InputStream imageStream = getClass().getResourceAsStream(imagePath);

        if (imageStream == null) {
            System.err.println("Image non trouvée (" + imagePath + "). Chargement de l'image par défaut.");
            imageStream = getClass().getResourceAsStream("/technofutur/gamejfx/default-profile.jpg");

            if (imageStream == null) {
                throw new IllegalStateException("L'image par défaut n'existe pas à l'emplacement '/technofutur/gamejfx/default-profile.jpg'");
            }
        }

        Image profilePicImage = new Image(imageStream);
        profileImage.setImage(profilePicImage);
    }
}