package doodlejump;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * This MovingPlatform is a subclass of the superclass Platform, and therefore inherits its methods and implementations. This subclass represents the
 * Platform that constantly moves left and right according to its own Timeline. This class contains a constructor that calls the superclass constructor,
 * a method to set up the platform's Timeline (setupTimeline), a method that returns the Timeline, overridden from Platform, and a method that handles
 * the Platform's movement according to boolean values and when it hits the edge of the screen (movePlatform). The class is contained by the Game class.
 */
public class MovingPlatform extends Platform {
    private boolean isRight;
    private Timeline timeline;

    /**
     * This MovingPlatform constructor takes in the gamePane and semi-randomly-generated x- and y-
     * coordinates from the generatePlatforms method, and
     * passes these into the Platform superclass constructor along with the MovingPlatform color
     * and three boolean values that correspond to the MovingPlatform
     * (only isMoving is true). The MovingPlatform is set to begin by moving towards the right,
     * and the setupTimeline helper method is called.
     */
    public MovingPlatform(Pane gamePane, double xLocation, double yLocation) {
        super(gamePane, Constants.MOVING_PLATFORM_COLOR, xLocation, yLocation, false, false, true); // booleans are respectively isBouncy, isDisappearing,
                                                                                                    // and isMoving
        this.isRight = true;
        this.setupTimeline();
    }

    /**
     * This private helper method is called in the MovingPlatform constructor and sets up
     * the Timeline for the individual MovingPlatform instance.
     * A new KeyFrame is created such that every set duration, the movePlatform method will be called.
     * The KeyFrame is added to a new Timeline, whose cycle count is set to indefinite and set to
     * play, letting the platforms actually move.
     */
    private void setupTimeline() {
        KeyFrame kf = new KeyFrame(Duration.millis(Constants.MOVINGPLATFORM_DURATION),
                (ActionEvent e) -> this.movePlatform());
        this.timeline = new Timeline(kf);
        this.timeline.setCycleCount(Animation.INDEFINITE);
        this.timeline.play();
    }

    /**
     * This method returns the Timeline instance variable created in the setupTimeline method,
     * and is called in Game's endGame method to make all the MovingPlatform Timelines stop
     * so that the platforms no longer move.
     */
    @Override
    public Timeline getTimeline() {
        return this.timeline;
    }

    /**
     * This private helper method handles the left and right movement of the MovingPlatforms,
     * and is called every duration of the Timeline in the setupTimeline method. If the Platform's
     * boolean isRight is true, the Platform's x-location will be updated by adding an offset value, such that
     * the platform moves right. Otherwise, the platform moves left. If the Platform hits either edge of
     * the scene, the boolean value switches such that the Platform begins to move in the opposite direction.
     */
    private void movePlatform() {
        if (this.isRight) {
            this.setXLocation(this.getXLocation() + Constants.MOVINGPLATFORM_OFFSET);
        }
        else {
            this.setXLocation(this.getXLocation() - Constants.MOVINGPLATFORM_OFFSET);
        }

        if (this.getXLocation() + Constants.PLATFORM_WIDTH > Constants.SCENE_WIDTH) { // checks if platform is offscreen to the right
            this.isRight = false;
        }
        if (this.getXLocation() < 0) { // checks if platform is offscreen to the left
            this.isRight = true;
        }
    }

}