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

public class ArcadeApp extends Application {
    
    /** 
     * Launches application. Constructs paanes and stage 
     * 
     * @param stage 
     */
    @Override
    public void start(Stage stage) {
	startScreen(stage);	
    } // start

    public static void startScreen(Stage stage) {
	VBox rootPane = new VBox();
	rootPane.setMinSize(800, 400);
	rootPane.setAlignment(Pos.CENTER);
    
	Scene scene = new Scene(rootPane);
	stage.setMaxWidth(640);
	stage.setMaxHeight(480);
	stage.setTitle("Arcade!");
	stage.setScene(scene);
	stage.sizeToScene();
	stage.show();

	Text title = new Text("CS-1302 ARCADE");
	Text subtitle = new Text("By Aly Shakoor and Adonnai Girma");
	Button snakeButton = new Button("Click here to play Snake");
	snakeButton.setOnAction(e -> playSnake(stage));
	Button sudokuButton = new Button("Click here to play Sudoku");
	sudokuButton.setOnAction(e -> playSudoku(stage));
	Button exit = new Button("Exit");
	exit.setOnAction(e -> exit());
	rootPane.getChildren().addAll(title, subtitle, snakeButton, sudokuButton, exit);
    }

    public static void exit() {
	System.exit(0);
    }
    
    public static void playSnake() {
	Snake snake = new Snake();
	snake.start(stage);
    }
    
    public static void playSudoku(Stage stage) {
	Sudoku sudoku = new Sudoku();
	sudoku.start(stage, 0);
    }

    public static void restartApp(Stage stage) {
	startScreen(stage);
    }
    
    /** main method. runs the app */
    public static void main(String[] args) {
	try {
	    Application.launch(args);
	} catch (UnsupportedOperationException e) {
	    System.out.println(e);
	    System.err.println("If this is a DISPLAY problem, then your X server connection");
	    System.err.println("has likely timed out. This can generally be fixed by logging");
	    System.err.println("out and logging back in.");
	    System.exit(1);
	} // try
    } // main

}



 
