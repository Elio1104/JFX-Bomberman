package technofutur.gamejfx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Game extends Application {
    int number_of_players = 4;
    private static final int TILE_SIZE = 64;
    private static String MAP_FILE;
    private static String GROUND;
    private static String WALL;

    public Game() {
    }

    @Override
    public void start(Stage stage) {
        loadTiles();
        GridPane root = loadMap();

        Scene scene = new Scene(root ,21 * 64, 7 * 64);

        stage.setTitle("JFX-Bomberman");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }

    private void loadTiles() {
        MAP_FILE = "src/main/resources/map.txt";
        GROUND = "Grass.png";
        WALL = "Tree.png";
    }

    private GridPane loadMap() {
        GridPane grid = new GridPane();
        try (BufferedReader br = new BufferedReader(new FileReader(MAP_FILE))) {
            String line;
            int row = 0;
            while ((line = br.readLine()) != null) {
                for (int col = 0; col < line.length(); col++) {
                    char tile = line.charAt(col);
                    ImageView imageView = new ImageView(new Image(tile == '1' ? WALL : GROUND));
                    imageView.setFitWidth(TILE_SIZE);
                    imageView.setFitHeight(TILE_SIZE);
                    grid.add(imageView, col, row);
                }
                row++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return grid;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
