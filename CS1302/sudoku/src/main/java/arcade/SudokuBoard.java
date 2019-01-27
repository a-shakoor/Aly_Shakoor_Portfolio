package cs1302.arcade;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * contains all of the instance variables and methods
 * for a single instance of a Sudoku game
 */
public class SudokuBoard {

    GridPane gridPane = new GridPane();
    HBox buttonRow = new HBox();
    HBox scoreRow = new HBox();
    Text currentScore;
    Square[][] boardVisual = new Square[9][9];
    int[][] boardValues = new int[9][9];
    int[][] preValues = new int[9][9];
    Stage stage;
  
    /** Constructor. Starts a new game given a stage and an initial score */
    public SudokuBoard(Stage stage, int currentScore) {
	this.stage = stage;
	this.currentScore = new Text(String.valueOf(currentScore));
	newGame();
    }

    /** Constructs the graphics and the backend for a new game */
    public void newGame() {
	getPreValues();
	fillBoardWithPreValues();
	constructSquareObjects();
	constructGridPane();
	constructButtons();
	constructScoreRow();
    }


    /////////////////////////////////////////////////////////////////
    ///////////         Methods for                   //////////////
    //////////      backend SETUP                    //////////////
    //////////////////////////////////////////////////////////////

    /** gets an array of preValues from the SudokuTemplates class */
    public void getPreValues() {
	preValues = SudokuTemplates.returnTemplate();
    }

    /** uses preValues array to fill the current board */
    public void fillBoardWithPreValues() {
	for(int i = 0; i < 9; i++) {
	    for(int j = 0; j < 9; j++) {
		if(preValues[i][j] != 0) {
		    boardValues[i][j] = preValues[i][j];
		}
	    } //inner for
	} //outer for
    }

    /////////////////////////////////////////////////////////////////
    ///////////  Methods for constructing and getting  /////////////
    //////////      VISUAL elements                  //////////////
    //////////////////////////////////////////////////////////////

    /** constructs the row that tells you the current number of puzzles solved */
    public void constructScoreRow() {
	Text label = new Text("Puzzles Solved: ");
	scoreRow.getChildren().addAll(label, currentScore);
	
    }

    /**
     * fills the boardVisual with 
     * a) Square objects if no prevalue is present in the given coordinate
     * b) UneditableSquare if a prevalue is present (and stores the value inside)
     */
    public void constructSquareObjects() {
	for(int i = 0; i < 9; i++) {
	    for(int j = 0; j < 9; j++) {
		if(boardValues[i][j] != 0) {
		    boardVisual[i][j] = new UneditableSquare(boardValues[i][j]);
		}
		else {
		    boardVisual[i][j] = new Square();
		}
	    }
	}
    }

    /** 
     * constructs the gridPane using Square[] boardVisual 
     * also assigns borders to the appropriate squares
     */
    public void constructGridPane() {
	for(int i = 0; i < 9; i++) {
	    for(int j = 0; j < 9; j++) {
		gridPane.add(boardVisual[i][j], j, i); //GridPane reverses columns/rows
		if((i == 2 || i == 5) && (j == 2 || j == 5)) {
		    boardVisual[i][j].setId("border-down-right");
		}
		else if(i == 2 || i == 5) {
		    boardVisual[i][j].setId("border-down");
		}
		else if(j == 2 || j == 5) {
		    boardVisual[i][j].setId("border-right");
		}		 
	    } //inner for
	} //outer for
    } //constructGridPane()

    /** constructs and sets actions for the Solve and Exit to Start Screen */        
    public void constructButtons() {
	Button solve = new Button("Solve");
	solve.setOnAction(e -> solve());
	Button exit = new Button("Exit to Start Screen");
	exit.setOnAction(e -> exit());
	buttonRow.getChildren().addAll(solve, exit);

    }

    /** returns the instance variable gridPane */
    public GridPane getGridPane() {
	return gridPane;
    }

    /** returns the instance variable HBox */
    public HBox getButtons() {
	return buttonRow;
    }

    /** returns the instance variable scoreRow */
    public HBox getScoreRow() {
	return scoreRow;
    }


    /////////////////////////////////////////////////////////////////
    ///////////  Methods for handling gameplay        //////////////
    //////////                                       //////////////
    //////////////////////////////////////////////////////////////

    /** updates Squares[][] boardVisual with values from int[][] boardValues */
    public void updateBoardValues() {
	for(int i = 0; i < 9; i++) {
	    for(int j = 0; j < 9; j++) {
		int num = Integer.parseInt(boardVisual[i][j].getText().trim());
		boardValues[i][j] = num;
	    }
	}
    }

    /**
     * called upon clicking Solve
     * checks the board and either presents a win message and restarts,
     * or presents an error and continues
     */ 
    public void solve() {
	if(!boardIsFull()) {
	    presentErrorMessage();
	    return;
	}
	updateBoardValues(); //update int[][] boardValues with values from the visual board
	boolean solved = SudokuBoardChecker.checkBoard(boardValues);
	if(solved) { endGameAndRestart(); }
	else { presentErrorMessage(); }
    }

    /**
     * called upon winnning the game
     * increments the score.
     * presents a win message
     * restarts the game by calling the start method on Sudoku
     */
    public void endGameAndRestart() {
	int score = Integer.parseInt(currentScore.getText());
	score++;
	presentWinMessage();
	Sudoku.start(stage, score);
    }

    /** returns to the main screen */
    public void exit() {
	ArcadeApp.restartApp(stage); //back to the Start Screen
    }

    /** checks to see if every Square in the board is filled */
    private boolean boardIsFull() {
	for(Square[] smallerArray : boardVisual) {
	    for(Square square : smallerArray) {
		if(square.getText().length() == 0) {
		    return false;
		}
	    }
	}
	return true;
    }

    /** win message */
    public void presentWinMessage() {
	Alert alert = new Alert(AlertType.INFORMATION);
	alert.setTitle("Congratulations!");
	alert.setHeaderText("You won. Play again, or click 'Exit to Start Screen'");
	alert.showAndWait();
    }

    /** generic error message */
    public void presentErrorMessage() {
	Alert alert = new Alert(AlertType.INFORMATION);
	alert.setTitle("Invalid Board");
	alert.setHeaderText("Not a valid board. Try again!");
	alert.showAndWait();
    }

    /** method used to print a 2 dimensional int[][]. used for testing */
    public static void printArray(int[][] inputArray) {
	for(int[] smallerArray : inputArray) {
	    for(int element : smallerArray) {
		System.out.printf("%s ", element);
	    }
	    System.out.println();
	}
	System.out.println();
	
    }



}
