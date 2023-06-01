package doodlejump;

import javafx.animation.Timeline;
import javafx.scene.layout.Pane;

/**
 * This DisappearingPlatform is a subclass of the superclass Platform, and therefore inherits its
 * methods and implementations. This subclass represents the
 * Platform that disappears after colliding with the Doodle. This class contains a constructor that
 * calls the superclass constructor and an overridden method that returns null (getTimeline),
 * as there is no Timeline in this class--only in MovingPlatform.
 */
public class DisappearingPlatform extends Platform {

    /**
     * This DisappearingPlatform constructor takes in the gamePane and semi-randomly-generated x-
     * and y- coordinates from the generatePlatforms method, and
     * passes these into the Platform superclass constructor along with the DisappearingPlatform
     * color and three boolean values that correspond to the DisappearingPlatform
     * (only isDisappearing is true).
     */
    public DisappearingPlatform(Pane gamePane, double xLocation, double yLocation) {
        super(gamePane, Constants.DISAPPEARING_PLATFORM_COLOR, xLocation, yLocation, false, true, false); // booleans are isBouncy, isDisappearing, isMoving
    }

    /**
     * This method is overridden from the Platform class and returns null because BouncyPlatform
     * does not contain a Timeline. It is here to make the polymorphism of Platform and MovingPlatform
     * function correctly.
     */
    @Override
    public Timeline getTimeline() {
        return null;
    }

}
