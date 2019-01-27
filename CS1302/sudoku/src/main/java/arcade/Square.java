package arcade;

import javafx.scene.control.TextField;
import java.util.regex.Pattern;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import java.util.function.UnaryOperator;


/** 
 * extension of TextField that only allows 
 * a single character from 1-9
 */
public class Square extends TextField {

    /** constructs the Square object by setting the TextFromatter that limits input */
    public Square() {
	super();
	Pattern pattern = Pattern.compile("[1-9]{0,1}");
	TextFormatter formatter =
	    new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
		if(pattern.matcher(change.getControlNewText()).matches()) {
		    return change;
		}
		return null;
	});
	setTextFormatter(formatter);
    }

    /** 
     * calls default constructor but also sets the initial value given the parameter
     * 
     * @param num is the initial value
     */
    public Square(int num) {
	this();
	setText(String.valueOf(num));
    }

    




}
