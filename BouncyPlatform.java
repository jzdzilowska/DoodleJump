package doodlejump;

import javafx.animation.Timeline;
import javafx.scene.layout.Pane;

/**
 * This BouncyPlatform is a subclass of the superclass Platform, and therefore inherits its methods and
 * implementations. This subclass represents thePlatform that makes the doodle rebound at a higher velocity when
 * collided with. This class contains a constructor that calls the superclass constructor an overridden
 * method that returns null (getTimeline), as there is no Timeline in this class--only in MovingPlatform.
 */
public class BouncyPlatform extends Platform {

    /**
     * This BouncyPlatform constructor takes in the gamePane and semi-randomly-generated x- and y-
     * coordinates from the generatePlatforms method, and passes these into the Platform superclass
     * constructor along with the BouncyPlatform color and three boolean values that correspond to
     * the BouncyPlatform (only isBouncy is true).
     */
    public BouncyPlatform(Pane gamePane, double xLocation, double yLocation) {
        super(gamePane, Constants.BOUNCY_PLATFORM_COLOR, xLocation, yLocation, true, false, false); // booleans are isBouncy, isDisappearing, isMoving
    }

    /**
     * This method is overridden from the Platform class and returns null because BouncyPlatform does not contain
     * a Timeline. It is here to make the polymorphism of Platform and MovingPlatform function correctly.
     */
    @Override
    public Timeline getTimeline() {
        return null;
    }
}
