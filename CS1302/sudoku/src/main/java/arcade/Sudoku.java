package cs1302.arcade;

import java.io.File;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Priority;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.scene.Node;
import javafx.stage.Stage;

/** class that starts the Sudoku part of the app */
public class Sudoku {
 
    /** 
     * Launches Sudoku part of the application.
     * Constructs paanes and stage 
     * Call this static method to start the Sudoku part of the app
     * Changes scene to Sudoku
     * 
     * @param stage 
     * @param currentScore is the amount of puzzles solved so far
     */
    public static void start(Stage stage, int currentScore) {
	
	VBox rootPane = new VBox();
	rootPane.setMinSize(800, 400);
	Scene scene = new Scene(rootPane);
	stage.setMaxWidth(640);
	stage.setMaxHeight(480);
	stage.setTitle("[XYZ] Gallery!");
	stage.setScene(scene);
	stage.sizeToScene();
	stage.show();

	SudokuBoard board = new SudokuBoard(stage, currentScore);
	rootPane.getChildren().add(returnTitle());
	rootPane.getChildren().add(board.getGridPane());
	rootPane.getChildren().add(board.getButtons());
	rootPane.getChildren().add(board.getScoreRow());
	addCSS(scene);
    } // start

    /** Sets up the Sudoku title *//
    public static Node returnTitle() {
	Text t = new Text();
	t.setText("Sudoku");
 	t.setFont(Font.font ("Verdana", 20));
	t.setFill(Color.RED);
	return t;
    }

    /** Adds the css file for sudoku */
    public static void addCSS(Scene scene) { 
	File f = new File("src/main/java/cs1302/arcade/sudoku.css");
	scene.getStylesheets().clear();
	scene.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
    }

}
