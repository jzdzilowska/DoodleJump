package doodlejump;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This is the main class where your DoodleJump game will start.
 * The main method of this application calls launch, a JavaFX method
 * which eventually calls the start method below. You will need to fill
 * in the start method to start your game!
 *
 * Class comments here...
 *
 * This App class allows our DoodleJump game to appear by initializing the PaneOrganizer which
 * holds all the GUI elements of our game. This class also instantiates a Scene set, associating
 * it with our BorderPane root, adding the scene to the stage, and letting it appear.
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        // Instantiate top-level object, set up the scene, and show the stage here.
        PaneOrganizer organizer = new PaneOrganizer();
        stage.setScene(new Scene(organizer.getRoot(), Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT));
        stage.setTitle("doodlejump");
        stage.show();
    }

    /*
     * Here is the mainline! No need to change this.
     */
    public static void main(String[] argv) {
        // launch is a static method inherited from Application.
        launch(argv);
    }
}
