package technofutur.gamejfx;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Bombe {

    private ImageView imageView;  // L'élément graphique pour afficher l'image
    private Timeline timeline;    // L'animation pour faire défiler les images

    private int currentImageIndex = 0;  // L'index de l'image en cours d'affichage
    private Image[] images;    // Tableau des images de l'animation

    public Bombe(GridPane grid, int col, int row) {
        // Charger les images pour l'animation
        images = new Image[]{
                new Image("/bombe1.png"),
                new Image("/bombe2.png"),
                new Image("/bombe3.png"),
                new Image("/explosionCentre1.png"),
                new Image("/explosionCentre2.png"),
                new Image("/explosionCentre3.png")
        };

        // Créer un ImageView pour afficher l'image de la bombe
        imageView = new ImageView(images[0]);
        imageView.setFitWidth(64);  // Ajuster la largeur de l'image
        imageView.setFitHeight(64); // Ajuster la hauteur de l'image

        // Ajouter l'ImageView au GridPane à la position spécifiée
        grid.add(imageView, col, row);  // col et row sont les indices de la cellule du GridPane

        // Créer l'animation pour changer l'image à chaque intervalle
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.3), e -> updateImage(grid))
        );
        timeline.setCycleCount(Timeline.INDEFINITE);  // L'animation se répète une seule fois
    }

    // Méthode pour mettre à jour l'image affichée
    private void updateImage(GridPane grid) {
        currentImageIndex++;
        if (currentImageIndex < images.length) {
            imageView.setImage(images[currentImageIndex]);
        } else {
            timeline.stop();  // Arrêter l'animation une fois l'explosion terminée
            grid.getChildren().remove(imageView);  // Retirer la bombe du grid après l'explosion
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
