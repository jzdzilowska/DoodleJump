package doodlejump;

import javafx.scene.paint.Color;

/**
 * This is your Constants class. It defines some constants you will need
 * in DoodleJump, using the default values from the demo--you shouldn't
 * need to change any of these values unless you want to experiment. Feel
 * free to add more constants to this class!
 *
 * A NOTE ON THE GRAVITY CONSTANT:
 *   Because our y-position is in pixels rather than meters, we'll need our
 *   gravity to be in units of pixels/sec^2 rather than the usual 9.8m/sec^2.
 *   There's not an exact conversion from pixels to meters since different
 *   monitors have varying numbers of pixels per inch, but assuming a fairly
 *   standard 72 pixels per inch, that means that one meter is roughly 2800
 *   pixels. However, a gravity of 2800 pixels/sec2 might feel a bit fast. We
 *   suggest you use a gravity of about 1000 pixels/sec2. Feel free to change
 *   this value, but make sure your game is playable with the value you choose.
 */
public class Constants {

    public static final int GRAVITY = 1000; // acceleration constant (UNITS: pixels/s^2)
    public static final int REBOUND_VELOCITY = -600; // initial jump velocity (UNITS: pixels/s)
    public static final int BOUNCY_REBOUND_VELOCITY = -1200; //bouncy platform initial jump velocity (UNITS: pixels/s)
    public static final double DURATION = 0.016; // KeyFrame duration (UNITS: s)
    public static final double SCENE_WIDTH = 600;
    public static final double SCENE_HEIGHT = 800;
    public static final double SCENE_HALF_HEIGHT = 400;
    public static final int PLATFORM_WIDTH = 30; // (UNITS: pixels)
    public static final int PLATFORM_HEIGHT = 7; // (UNITS: pixels)
    public static final int DOODLE_WIDTH = 20; // (UNITS: pixels)
    public static final int DOODLE_HEIGHT = 40; // (UNITS: pixels)
    public static final double X_OFFSET = 100;
    public static final double Y_OFFSET_MIN = 20;
    public static final double Y_OFFSET_MAX = 40;

    public static final Color REGULAR_PLATFORM_COLOR = Color.rgb(244,144,172);
    public static final Color MOVING_PLATFORM_COLOR = Color.rgb(116,93,152);

    public static final Color DISAPPEARING_PLATFORM_COLOR = Color.rgb(114,145,123);

    public static final Color BOUNCY_PLATFORM_COLOR = Color.rgb(209,226,240);

    public static final double GAMEOVER_FONT_SIZE = 60;

    public static final double DROPSHADOW_RADIUS = 20;
    public static final double DROPSHADOW_SPREAD = 0.5;
    public static final double STARTING_PLATFORM_XLOC = 285;
    public static final double STARTING_PLATFORM_YLOC = 750;
    public static final double DOODLE_MOVE_OFFSET = 20;
    public static final double DOODLE_STARTING_XLOC = 300;
    public static final double DOODLE_STARTING_YLOC = 550;
    public static final double MOVINGPLATFORM_DURATION = 100;
    public static final double MOVINGPLATFORM_OFFSET = 10;
}
