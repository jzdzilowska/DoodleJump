package doodlejump;

import javafx.scene.layout.Pane;
import java.util.ArrayList;

/**
 * This PlatformHandler class handles the logic involved with moving and making platforms
 * in our DoodleJump game. It is contained by the Game class and is associated with the
 * Doodle class, Platform class, and PaneOrganizer class . The class contains a constructor, a method to
 * manually associate the Doodle and PlatformHandler (addDoodle), methods to generate new
 * semi-random platforms as the game opens and then when it scrolls (generatePlatforms, spawnPlatforms),
 * a method that checks for collisions between the doodle and platform (checkIntersection),
 * a method to check for and handle platforms falling offscreen (checkOffscreen),
 * a method to scroll platforms such that the doodle appears to be moving upwards
 * (scrollPlatforms), and a method that updates the player's score based on how much the platforms scroll downwards.
 */
public class PlatformHandler {
    private Platform platform;
    private ArrayList<Platform> platforms;
    private Pane gamePane;
    private Doodle doodle;
    private int score;
    private PaneOrganizer organizer;

    /**
     * This PlatformHandler constructor is called in the Game class constructor and has 4 parameters:
     * the original RegularPlatform that the doodle begins bouncing on at the start of the game,
     * an ArrayList that will store all of the platforms visible in the game,
     * the Pane on which the doodle and platforms appear, and the PaneOrganizer
     * to handle the graphical logic of the platforms. All of the arguments that get passed in,
     * save for the PaneOrganizer, are initialized in the Game class to be associated here,
     * with the PlatformHandler. As such, all of the arguments are assigned to their
     * respective instance variables in this class. Additionally, the player's score is set to
     * zero to begin the game (this gets updated later in the class).
     */
    public PlatformHandler(Platform platform, ArrayList<Platform> platforms, Pane gamePane, PaneOrganizer organizer) {
        this.platform = platform;
        this.platforms = platforms;
        this.gamePane = gamePane;
        this.organizer = organizer;
        this.score = 0;
    }

    /**
     * This method helps to manually associate the Doodle and PlatformHandler after both instances
     * are constructed, because both of those classes end up needing to know about each other for the
     * game to function correctly. As such, this method is called in the Game class and takes in the
     * Doodle constructed in the Game class as an argument. The method then assigns that doodle to an
     * instance variable so it can be used in other methods in this class.
     */
    public void addDoodle(Doodle doodle) {
        this.doodle = doodle;
    }

    /**
     * This generatePlatforms method is called once in the Game class's startGame method to populate
     * the screen with platforms, and then once the platforms begin to scroll,
     * the method is called continuously in this class's scrollPlatforms method to keep
     * the screen full of Platforms as the player advances.
     * This method handles the generation of semi-randomly located platforms by constantly
     * reassigning our platform instance variable to a new platform; if in the while loop
     * (which checks if the top platform is still onscreen, i.e. the player hasn't fallen off),
     * the method uses high and low bounds based on the previous platform to determine semi-random
     * x- and y- coordinates based on what the doodle can feasibly reach from the last platform.
     * These random coordinates are passed into the spawnPlatform that returns an instance of a
     * Platform subclass, which is added to the gamePane graphically
     * upon construction. This new Platform is added to the ArrayList so that we can track its
     * movement/when it falls offscreen. The topPlatform and platform instance variable are both
     * reassigned to this new Platform so that we can continue generating new Platforms
     * based on the last new Platform, and the instance variable so that we can use the information
     * on the Platform's coordinates throughout the
     * class.
     */
    public void generatePlatforms() {
        Platform topPlatform = this.platform;
        while (topPlatform.getYLocation() > 0) {
            double lowX = Math.max(0, (topPlatform.getXLocation() - Constants.X_OFFSET));
            double highX = Math.min((Constants.SCENE_WIDTH - Constants.PLATFORM_WIDTH),
                                    (topPlatform.getXLocation() + Constants.X_OFFSET));
            double randomX = lowX + (int) ((highX - lowX) * Math.random()); // takes the minimum and maximum possible X coordinate based on the last platforms' location and
                                                                            // a predetermined offset; selects a random number within the range of the platform's possible X
                                                                            // coordinates.

            double lowY = topPlatform.getYLocation() - Constants.Y_OFFSET_MIN;
            double highY = topPlatform.getYLocation() - Constants.Y_OFFSET_MAX;
            double randomY = lowY + (int) ((highY - lowY) * Math.random()); // takes the minimum and maximum possible Y coordinate based on the last platform's location and
                                                                            // a predetermined offset; selects a random number within the range of the platform's possible Y
                                                                            // coordinates.

            Platform newPlatform = this.spawnPlatform(randomX, randomY); // spawnPlatform returns the Platform subclass that's instantiated
            this.platforms.add(newPlatform);
            topPlatform = newPlatform;
            this.platform = topPlatform;
        }
    }

    /**
     * This helper method is called in the generatePlatforms method above. Here, we handle the
     * random selection of the Platform subclass instance that will
     * next appear onscreen. The method takes in two arguments, double xLocation and double yLocation,
     * which are the semi-random coordinates generated by the generatePlatforms method that
     * represent where the Platform will spawn onscreen. Using a switch statement,
     * one of the four Platform subclasses have an instance created and is polymorphically
     * assigned to the Platform declaration. The Platform subclass is returned such that it can continue
     * to be used in the generatePlatforms method.
     */
    private Platform spawnPlatform(double xLocation, double yLocation) {
        int randInt = (int) (Math.random() * 4);
        Platform platform;
        switch (randInt) {
            case 0:
                platform = new RegularPlatform(this.gamePane, xLocation, yLocation);
                break;
            case 1:
                platform = new MovingPlatform(this.gamePane, xLocation, yLocation);
                break;
            case 2:
                platform = new DisappearingPlatform(this.gamePane, xLocation, yLocation);
                break;
            default:
                platform = new BouncyPlatform(this.gamePane, xLocation, yLocation);
                break;
        }
        return platform;
    }

    /**
     * This method is called in the Doodle class's updatePosition method to help check if the
     * Doodle and a Platform collide, and if so, updates the velocity to the
     * rebound value to let the Doodle appear to bounce off the Platform. When called, the method cycles through
     * the ArrayList of Platform and, if the Doodle is currently falling, checks if the Doodle's
     * Rectangle graphically intersects each Platform based on its current location.
     * If there is a collision, the method then checks the boolean values of the Platform;
     * if the Platform is a BouncyPlatform (isBouncy is true), the method returns the
     * Bouncy rebound velocity to Doodle's updateVelocity variable and exits the for-loop. Otherwise, if the
     * Platform is a DisappearingPlatform (isDisappearing is true), the Platform will
     * be removed from the game logically and graphically before returning
     * the normal rebound velocity to have the Doodle bounce upwards. If the Platform that
     * Doodle collides with is neither Bouncy nor Disappearing, the method will skip over
     * the if-statement bodies and simply return the constant rebound velocity such that
     * the Doodle bounces normally off the Platform. If the method exits the for-loop
     * without returning a value, this means that a) there was no intersection, or b) the Doodle wasn't
     * falling, so the updatedVelocity that was initially passed in is returned to the Doodle as normal.
     */
    public double checkIntersection(double updatedVelocity) {
        for (int i = 0; i < this.platforms.size(); i++) {
            if ((updatedVelocity > 0) &&
                    (this.doodle.getDoodleRect().intersects(this.platforms.get(i).getXLocation(),
                                                            this.platforms.get(i).getYLocation(), Constants.PLATFORM_WIDTH, Constants.PLATFORM_HEIGHT))) {
                if (this.platforms.get(i).isBouncy) {
                    return (Constants.BOUNCY_REBOUND_VELOCITY);
                } else if (this.platforms.get(i).isDisappearing) {
                    this.gamePane.getChildren().remove(this.platforms.get(i).getPlatformRect());
                    this.platforms.remove(i);
                }
                return Constants.REBOUND_VELOCITY;
            }
        }
        return updatedVelocity;
    }

    /**
     * This method is called in the Doodle class's updatePosition method in order to scroll the
     * Platforms downwards when the Doodle reaches the midpoint
     * of the screen, giving the illusion of the Doodle's upward movement. When called, the method
     * checks if the Doodle's updated potential y-coordinate (updatedPosition, the double argument
     * passed into the method) is higher than the midpoint of the screen. If so,
     * the difference between the halfway point and updatedPosition is calculated.
     * Using an enhanced for-loop, this difference is added to each of the Platforms'
     * y-coordinates to move them downwards by the same amount that the doodle would have moved up.
     * The Doodle's y-coordinate is reset to the middle of the screen. The player's score is increased
     * by 1 while the scrollPlatforms method is called such that the score only increments when
     * the Platforms scroll. The method then generates new Platforms
     * to fill the new space at the top of the screen, and the method calls checkOffscreen to
     * check for and remove Platforms that fall offscreen after
     * being moved downwards.
     */
    public void scrollPlatforms(double updatedPosition) {
        if (this.doodle.getYLoc() < Constants.SCENE_HALF_HEIGHT) {
            double difference = Constants.SCENE_HALF_HEIGHT - updatedPosition;
            for (Platform platform : this.platforms) {
                platform.setYLocation(platform.getYLocation() + difference);
            }
            this.doodle.setYLoc(Constants.SCENE_HALF_HEIGHT);
            this.increaseScore();
            this.generatePlatforms();
        }
        this.checkOffscreen();
    }

    /**
     * This helper method checks for and removes any platforms that fall offscreen
     * after being moved down to simulate the Doodle moving upwards.
     * The method is called in the scrollPlatforms method. It uses a for-loop to
     * cycle through the ArrayList of platforms; if any of the Platforms
     * have a y-coordinate greater than the scene's height, this means it has fallen
     * off the bottom of the screen and should be removed. As such,
     * the platform is removed graphically and logically.
     */
    private void checkOffscreen() {
        for (int i = 0; i < this.platforms.size(); i++) {
            if (this.platforms.get(i).getYLocation() > Constants.SCENE_HEIGHT) {
                this.gamePane.getChildren().remove(this.platforms.get(i).getPlatformRect());
                this.platforms.remove(i);
                i--; // so that the for-loop doesn't skip a platform mistakenly after removing one
            }
        }
    }

    /**
     * This helper method is called in the scrollPlatforms method and handles updating the
     * score Label's text with an updated player score. When called (which is whenever
     * the platforms scroll), the score increments and the PaneOrganizer's setText method
     * is called with the updated string passed in, so that the Label can be updated
     * accordingly in the PaneOrganizer class (since the Label is instantiated in the PaneOrganizer class).
     */
    private void increaseScore() {
        this.score++;
        String s = "Score: " + this.score;
        this.organizer.setText(s);
    }
}



