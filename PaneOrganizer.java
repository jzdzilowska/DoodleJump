package doodlejump;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * This PaneOrganizer class sets up and organizes the panes of our game
 * such that the GUI appears as desired. The PaneOrganizer is contained
 * by the App class. It has a constructor to set up the main game Pane,
 * and two helper methods that set up the buttonPane and labelPane
 * (createButtonPane and createLabelPane respectively). The class also
 * has a method to return the root BorderPane (getRoot) and a method that
 * sets the text of the score label to a given value.
 */
public class PaneOrganizer {
    private BorderPane root;
    private Label score;

    /**
     * This is the PaneOrganizer constructor. This constructor has no parameters,
     * and is called in the App class. The constructor initializes the BorderPane root
     * instance variable, then instantiates a new Pane that serves as the main game Pane.
     * The constructor then creates new Game instance and uses a lambda expression to
     * set the KeyEvent. Finally, the constructor calls its helper methods to set up
     * the buttonPane and labelPane.
     */
    public PaneOrganizer() {
        this.root = new BorderPane();

        Pane gamePane = new Pane();
        gamePane.setStyle("-fx-background-image: url(https://i.imgur.com/D0PYBoN.jpg)"); // sets background image of game
        this.root.setCenter(gamePane); // gamePane set to center of BorderPane
        gamePane.setFocusTraversable(true);

        Game game = new Game(gamePane, this);
        gamePane.setOnKeyPressed((KeyEvent e) -> game.onKeyPress(e));

        this.createButtonPane();
        this.createLabelPane();
    }

    /**
     * This helper method is called in the PaneOrganizer constructor and
     * has no parameters. It creates a new instance of VBox adds a new instance
     * of a Label (the score) to the VBox graphically. The labelPane is then
     * positioned at the top of the BorderPane root.
     */
    private void createLabelPane() {
        VBox labelPane = new VBox();
        this.score = new Label("Score: 0");
        labelPane.getChildren().add(score);
        this.root.setTop(labelPane);
    }

    /**
     * This helper method is called in the PaneOrganizer constructor
     * and has no parameters. The method initializes an HBox and the Button
     * quitButton, whose ActionEvent is set to exit the program when pressed using a lambda
     * expression. The Button is then graphically added to the HBox.
     * The buttonPane is positioned at the bottom of the BorderPane and its
     * contents are centered.
     */
    private void createButtonPane() {
        HBox buttonPane = new HBox();
        Button quitButton = new Button("Quit!");
        quitButton.setOnAction((ActionEvent e) -> System.exit(0));
        buttonPane.getChildren().add(quitButton);

        this.root.setBottom(buttonPane);
        buttonPane.setAlignment(Pos.CENTER);
    }

    /**
     * This method is called in the PlatformHandler class and takes
     * in a String argument that represents the player's score. The method
     * sets the score Label instance variable's text to the passed-in string.
     */
    public void setText(String s) {
        this.score.setText(s);
    }
    /**
     * This accessor method is called when instantiating a Scene in the App class so
     * that the Scene can know about the BorderPane (associated). The method
     * has no parameters and returns the instance variable BorderPane root.
     */
    public BorderPane getRoot() {
        return this.root;
    }
}
