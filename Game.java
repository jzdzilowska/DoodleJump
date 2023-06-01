package doodlejump;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import java.util.ArrayList;

/**
 * This top-level logic class Game handles the user interaction and general
 * logic associated with running the DoodleJump game successfully. The
 * class is contained by the PaneOrganizer and contains its constructor, three
 * private helper methods that allow components of the game to move (startGame),
 * handle the logic of ending the game (endGame), and set up the "game over"
 * message when the game ends (setupLabel). The class also contains a method
 * to handle user key input (onKeyPress) and update the position of the
 * Doodle with the Timeline (update).
 */
public class Game {
    private Pane gamePane;
    private Doodle doodle;
    private ArrayList<Platform> platforms;
    private PlatformHandler platformHandler;
    private Timeline timeline;

    /**
     * This Game constructor is called in the PaneOrganizer constructor in order to let
     * the DoodleJump game logically begin. The Game is associated with the gamePane instantiated
     * in the PaneOrganizer, and the PaneOrganizer is also passed in as an arugment so that it
     * can be associated with the PlatformHandler. The method creates a new ArrayList instance to assign
     * to an instance variable, a PlatformHandler and Doodle that are assigned to an instance variable,
     * and a new RegularPlatform on which the Doodle starts bouncing on. This is declared as a
     * Platform so that the Platform declaration can be used polymorphically later
     * when generating new platforms. The platform is then added to the ArrayList, and the Doodle is
     * manually associated with the PlatformHandler using addDoodle. Finally, the doodle is graphically
     * added to the gamePane and the startGame helper method is called.
     */
    public Game(Pane gamePane, PaneOrganizer organizer) {
        this.gamePane = gamePane;
        this.platforms = new ArrayList<>();
        Platform platform = new RegularPlatform(gamePane, Constants.STARTING_PLATFORM_XLOC, Constants.STARTING_PLATFORM_YLOC);
        this.platforms.add(platform);
        this.platformHandler = new PlatformHandler(platform, this.platforms, this.gamePane, organizer);
        this.doodle = new Doodle(this.platformHandler);
        this.platformHandler.addDoodle(this.doodle);
        this.gamePane.getChildren().addAll(this.doodle.getDoodleRect());
        this.startGame();
    }

    /**
     * This helper method is called in the Game constructor and has no parameters. It creates the
     * Timeline and KeyFrame associated with the Doodle's vertical movement, calling the update helper
     * method every set duration. The timeline's cycle count is set to indefinite and the
     * PlatformHandler's generatePlatforms method is called to fill the screen with semi-randomly
     * generated platforms.
     */
   private void startGame() {
        KeyFrame kf = new KeyFrame(Duration.seconds(Constants.DURATION),
                (ActionEvent e) -> this.update());
       this.timeline = new Timeline(kf);
       this.timeline.setCycleCount(Animation.INDEFINITE);
       this.timeline.play();
       this.platformHandler.generatePlatforms();
   }

   /**
     * This method is called when setting up the KeyEvent using a lambda expression in gamePane,
    * within the PaneOrganizer constructor. Using a switch statement, the method handles the left
    * and right movement of the Doodle object by calling Doodle's moveLeft
    * and moveRight method corresponding with the key pressed. The method exits the switch statement
    * and calls the Doodle's checkWrap method to check for and handle the doodle moving offscreen
    * such that the doodle graphically wraps back around the screen.
    */
   public void onKeyPress(KeyEvent event) {
        KeyCode keyPressed = event.getCode();
        switch (keyPressed) {
            case LEFT:
                this.doodle.moveLeft();
                break;
            case RIGHT:
                this.doodle.moveRight();
                break;
            default:
                break;
        }
        this.doodle.checkWrap();

        event.consume();
   }

    /**
     * This method is called in the startGame method of the Game class while setting up the Timeline's
     * KeyFrame such that every set duration, this update method is called. This method calls Doodle's
     * updatePosition method to move the Doodle according to gravity/interactions with Platforms,
     * then checks whether the Doodle has fallen offscreen--if so, the helper method
     * endGame is called to graphically and logically end the game.
     */
   public void update() {
       this.doodle.updatePosition();
       if (this.doodle.checkOffScreen()) {
           this.endGame();
       }
   }

    /**
     * This private helper method is called in the Game class's update method above, and handles what
     * happens in the application when the player's Doodle falls offscreen. Firstly,
     * the Doodle's Rectangle is graphically removed from the gamePane such that it no longer
     * would appear, then the Doodle's timeline is stopped so it no longer reacts to gravity.
     * The method then uses a for-loop to iterate through the ArrayList of Platforms in order to check
     * for MovingPlatforms. When one is detected using the isMoving boolean that was passed in upon
     * the Platform's construction, the Timeline of that platform is stopped. Finally, the helper
     * method setupLabel is called to allow the game
     * over message to appear onscreen.
     */
   private void endGame() {
       this.gamePane.getChildren().remove(this.doodle.getDoodleRect());
       this.timeline.stop();
       for (Platform currentPlatform: this.platforms) {
           if (currentPlatform.isMoving) {
               currentPlatform.getTimeline().stop();
           }
       }
       this.setupLabel();
   }

    /**
     * This helper method is called in the endGame method, and displays the "Game Over"
     * message when the user's Doodle falls off the screen. The method creates a new instance of
     * Label with the game over text, and creates a new HBox in which the Label is added as a child after
     * setting the Label's appearance. Finally, the HBox is added to the gamePane, allowing it to
     * appear graphically.
     */
   private void setupLabel() {
      Label gameOver = new Label("Game Over!");
      HBox labelBox = new HBox();
      labelBox.setAlignment(Pos.CENTER);
      labelBox.setPrefWidth(this.gamePane.getWidth());
      labelBox.setPrefHeight(this.gamePane.getHeight());
      gameOver.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC, Constants.GAMEOVER_FONT_SIZE));
      gameOver.setTextFill(Color.WHITE);

      labelBox.getChildren().add(gameOver);
      this.gamePane.getChildren().add(labelBox);
   }

}

