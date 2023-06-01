package doodlejump;
import javafx.animation.Timeline;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * This abstract Platform superclass, in the Constructor, handles the appearance of each Platform,
 * and the booleans that each Platform subclass corresponds to.
 * This class wraps the Rectangle class.
 * The class also handles accessor and mutator methods associated with the Platforms
 * (getXLocation, getYLocation, setYLocation, setXLocation, getPlatformRect)
 * and the Timeline that is used by the MovingPlatforms (getTimeline). DoodleJump does not
 * create an instance of Platform, but declares a Platform that is used
 * polymorphically in order to spawn random platforms. The class is contained by the Game
 * class and is associated with the Rectangle created in the Doodle class.
 */
public abstract class Platform {

    public boolean isBouncy;
    public boolean isDisappearing;
    public boolean isMoving;
    private Rectangle platformRect;
    private double xLocation;
    private double yLocation;

    /**
     * This Platform constructor is called in each of the Platform subclasses: MovingPlatform,
     * RegularPlatform, BouncyPlatform, and DisappearingPlatform, in order to create a new instance
     * of those subclasses using this super constructor--all of the platform subclasses are declared
     * polymorphically as Platforms but initializes as their subclass. The constructor creates a new Rectangle
     * that represents the Platform, making the Platform class wrap Rectangle. The Rectangle's visual effects are
     * set up, and the color, xLocation, and yLocation are set up using randomly-generated values from
     * the spawnPlatform and generatePlatforms methods. The boolean values are passed in from the subclass's
     * constructor based on whether the platform is Regular, Bouncy, Disappearing, or Moving. Finally,
     * the Platform's Rectangle is graphically added to the gamePane.
     */
    public Platform(Pane gamePane, Color color, double xLocation, double yLocation, boolean isBouncy, boolean isDisappearing, boolean isMoving) {
        this.platformRect = new Rectangle(xLocation, yLocation, Constants.PLATFORM_WIDTH, Constants.PLATFORM_HEIGHT);
        this.platformRect.setFill(color);
        DropShadow dropShadow = new DropShadow(BlurType.GAUSSIAN, Color.WHITE, Constants.DROPSHADOW_RADIUS, Constants.DROPSHADOW_SPREAD, 0, 0);
        this.platformRect.setEffect(dropShadow);
        this.platformRect.setStroke(Color.WHITE);

        this.xLocation = xLocation;
        this.yLocation = yLocation;

        this.isBouncy = isBouncy;
        this.isDisappearing = isDisappearing;
        this.isMoving = isMoving;

        gamePane.getChildren().add(this.platformRect);
    }

    /**
     * This accessor method returns the x-location of the current platform, and is called in both the
     * MovingPlatform and PlatformHandler classes; in MovingPlatform, it's used to move the platform
     * according to the Timeline as well as to check when the Platform hits the edges of the screen.
     * In PlatformHandler, it's used to check if the Platform collides with the Doodle.
     */
    public double getXLocation() {
        return this.xLocation;
    }


    /**
     * This accessor method returns the y-location of the current platform, and is called in
     * PlatformHandler; in the generatePlatforms method, it's used to check whether the top platform
     * is still onscreen, to determine whether new platforms should be generated, and to help generate
     * semi-random coordinates for the next platform. In PlatformHandler, it's used to check if the
     * Platform collides with the Doodle.
     */
    public double getYLocation() {
        return this.yLocation;
    }

    /**
     * This mutator method sets the Platform's y-coordinate instance variable and the Rectangle's
     * y-coordinate to the passed-in double value. The method is called in the scrollPlatforms method
     * in order to move the Platforms down by the same amount that the Doodle would have moved
     * above the midpoint of the screen.
     */
    public void setYLocation(double yLoc) {
        this.platformRect.setY(yLoc);
        this.yLocation = yLoc;
    }

    /**
     * This mutator method sets the Platform's x-coordinate instance variable and the Rectangle's
     * y-coordinate to the passed-in double value. The method is called in the MovingPlatform class
     * in order to move the Platform left or right with the Timeline. The method is here so that the
     * MovingPlatform can set the xLocation even if it does not have access to the platform's Rectangle
     * directly.
     */
    public void setXLocation(double xLoc) {
        this.platformRect.setX(xLoc);
        this.xLocation = xLoc;
    }

    /**
     * This accessor method returns the Rectangle object associated with the Platform. It's called
     * twice in PlatformHandler: once in checkIntersection to remove the Rectangle from the pane
     * graphically if the doodle and a MovingPlatform collide, and once in
     * checkOffscreen to help remove the platform if it falls offscreen after scrolling.
     */
    public Rectangle getPlatformRect() {
        return this.platformRect;
    }

    /**
     * This abstract accessor method returns the Timeline associated with the Platform, if the Platform
     * is a MovingPlatform. Otherwise, the method returns null. It is called in the MovingPlatform class,
     * and is located here so that the method can be called on the
     * currentPlatform in endGame in order to stop the Timelines of the visible MovingPlatforms.
     */
    public abstract Timeline getTimeline();

}

