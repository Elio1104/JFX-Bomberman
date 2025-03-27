package technofutur.gamejfx;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Bombe {

    private ImageView imageView;  // L'élément graphique pour afficher l'image
    private Timeline timeline;    // L'animation pour faire défiler les images

    private int currentImageIndex = 0;  // L'index de l'image en cours d'affichage
    private Image[] images;    // Tableau des images de l'animation

    public Bombe(Pane pane, int posX, int posY) {
        // Charger les images pour l'animation
        images = new Image[]{
                new Image("/bombe1.png"),
                new Image("/bombe2.png"),
        };

        // Créer un ImageView pour afficher l'image de la bombe
        imageView = new ImageView(images[0]);
        imageView.setX(posX);  // Position de la bombe sur l'axe X
        imageView.setY(posY);  // Position de la bombe sur l'axe Y
        pane.getChildren().add(imageView);

        // Créer l'animation pour changer l'image à chaque intervalle
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.5), e -> updateImage(pane))
        );
        timeline.setCycleCount(Timeline.INDEFINITE);  // L'animation se répète une seule fois
    }

    // Méthode pour mettre à jour l'image affichée
    private void updateImage(Pane pane) {
        currentImageIndex++;
        if (currentImageIndex < images.length) {
            imageView.setImage(images[currentImageIndex]);
        } else {
            pane.getChildren().remove(imageView);  // Retirer la bombe du pane après l'explosion
            timeline.stop();  // Arrêter l'animation une fois l'explosion terminée
        }
    }

    // Lancer l'animation de la bombe
    public void start() {
        timeline.play();
    }

    // Arrêter l'animation de la bombe
    public void stop() {
        timeline.stop();
    }
}
