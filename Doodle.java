package doodlejump;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * This Doodle class handles the appearance and movement of the Doodle object that is controlled by the user.
 * It is contained by the Game class and is associated with the PlatformHandler class. The class is a
 * wrapper of the Rectangle class.
 * The class contains a constructor in which the Doodle's appearance is set up, an accessor method to return the
 * Rectangle object that is the graphical representation of the Doodle (getDoodleRect), accessor and mutator
 * methods to return or change the Doodle's y-location (getYLoc and setYLoc, respectively), a method to update
 * the position of the Doodle according to the Timeline and physics formulas (updatePosition),
 * methods to move the Doodle left and right (moveLeft and moveRight), a method to let the Doodle wrap
 * back around the screen when it moves offscreen left or right (checkWrap), and a method to check if the Doodle
 * has fallen through the bottom of the screen (checkOffscreen).
 */
public class Doodle {

    private Rectangle doodleRect;
    private double currentVelocity;
    private double yLoc;
    private PlatformHandler platformHandler;

    /**
     * This Doodle constructor is called in the Game class's constructor. The Doodle is associated with
     * the PlatformHandler that's passed in as an argument, and the Rectangle that the Doodle class wraps is
     * constructed. The value of the current velocity is set to zero, and the original x and y coordinates
     * to the middle of the bottom of the screen. The method then sets the x- and y-coordinates of the
     * Rectangle object itself so that they match the Doodle object's coordinates.
     */
    public Doodle(PlatformHandler platformHandler) {
        this.doodleRect = new Rectangle(Constants.DOODLE_WIDTH, Constants.DOODLE_HEIGHT, Color.PINK);
        this.currentVelocity = 0;
        double xLoc = Constants.DOODLE_STARTING_XLOC;
        this.yLoc = Constants.DOODLE_STARTING_YLOC;
        this.doodleRect.setX(xLoc);
        this.doodleRect.setY(yLoc);
        this.platformHandler = platformHandler;
    }

    /**
     * This accessor method is called 3 times: once in the Game constructor to add the Rectangle object to gamePane,
     * once in Game's endGame method to graphically remove the Rectangle, and once in checkIntersection to check if the
     * Rectangle collides with a Platform. The method returns the Rectangle doodleRect.
     */
    public Rectangle getDoodleRect() {
        return this.doodleRect;
    }

    /**
     * This mutator method changes the Doodle object and wrapped Rectangle's y-coordinates to the passed in double
     * value. The method is called twice: once in this Doodle class's updatePosition method to set the
     * Doodle and doodle's Rectangle to a new position based on physics equations and gravity; and
     * another time in the PlatformHandler's scrollPlatforms method to reset the Doodle's y-location
     * back to the middle of the screen when it goes higher than that point.
     */
    public void setYLoc(double yLoc) {
        this.yLoc = yLoc;
        this.doodleRect.setY(yLoc);
    }

    /**
     * This accessor method returns the double value of the Doodle's y-location. The method is
     * called twice: one to check whether the Doodle has fallen offscreen in Doodle's checkOffscreen, and once to
     * check whether the Doodle is above the midpoint of the screen so that it can be reset
     * to the middle of the screen in PlatformHandler.
     */
    public double getYLoc() {
        return this.yLoc;
    }

    /**
     * This method is called in the Timeline in Game. It updates the Doodle's velocity and position to new
     * hypothetical positions and velocities based on the physical calculation. To check if this position
     * and velocity should actually be used in game, the updated velocity is passed into checkIntersection and
     * the velocity returned depends on whether there was an intersection, in which case a rebound velocity
     * will be reassigned to the currentVelocity instance variable. The method then calls PlatformHandler's
     * scrollPlatforms method using the new yLoc.
     */
    public void updatePosition() {
        double updatedVelocity = this.currentVelocity + Constants.GRAVITY * Constants.DURATION;
        double updatedPosition = this.yLoc + updatedVelocity * Constants.DURATION; //velocity and position are updated
                                                                                   //based on gravity
        this.setYLoc(updatedPosition);

        this.currentVelocity = this.platformHandler.checkIntersection(updatedVelocity); // current velocity either returns
                                                                                        // updatedVelocity, rebound velocity,
                                                                                        // or bouncy rebound velocity
        this.platformHandler.scrollPlatforms(this.yLoc);
    }

    /**
     * This method is called in the Game class in order to graphically handle what happens when the
     * right key is pressed. When called, the Doodle's x-coordinate is shifted a set offset to the right
     * by adding the offset value to the current x-coordinate.
     */
    public void moveRight() {
        this.doodleRect.setX(this.doodleRect.getX() + Constants.DOODLE_MOVE_OFFSET);
    }

    /**
     * This method is called in the Game class's onKeyPress method in order to graphically handle what happens
     * when the left key is pressed. When called, the Doodle's x-coordinate is shifted a set offset to the left
     * by subtracting the offset value from the current x-coordinate.
     */
    public void moveLeft() {
        this.doodleRect.setX(this.doodleRect.getX() - Constants.DOODLE_MOVE_OFFSET);
    }

    /**
     * This method is called in the onKeyPress method of the Game class after the switch statement
     * in which the doodle is moved left or right; after being moved, this method checks whether
     * or not the Doodle has moved offscreen to the left or right. If the Doodle moves offscreen to the
     * left, the Doodle's x-coordinate is set to the very far right of the screen. If the doodle moves offscreen
     * to the right, the Doodle's x-coordinate is set to the very far left of the screen. This gives the Doodle
     * wrapping capabilities.
     */
    public void checkWrap() {
        if (this.doodleRect.getX() < 0) {
            this.doodleRect.setX(Constants.SCENE_WIDTH);
        } else if (this.doodleRect.getX() + Constants.DOODLE_WIDTH > Constants.SCENE_WIDTH) {
            this.doodleRect.setX(0);
        }
    }

    /**
     * This method is called in the update method of the Game class, which is called every set duration
     * of the Timeline. When called, the method returns true if the Doodle has fallen off the bottom of the screen,
     * meaning the player has lost if the Doodle's y-coordinate is greater than the scene's height. Otherwise,
     * the method returns false. If returning true, the update method then calls the endGame method.
     */
    public boolean checkOffScreen() {
        return this.getYLoc() > Constants.SCENE_HEIGHT;
    }

}

