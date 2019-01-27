package cs1302.arcade;


/**
 * same as Square but uneditable and gray
 */
public class UneditableSquare extends Square {

    /** contructor that calls Square() constructor but makes uneditable and gray background */
    public UneditableSquare(int num) {
	super(num);
	setEditable(false);
	setStyle("-fx-background-color: gray");
    }
	

}
