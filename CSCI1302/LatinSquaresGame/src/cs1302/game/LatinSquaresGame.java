
package cs1302.game;

import java.util.Scanner;
import java.io.File;

public class LatinSquaresGame {

    int numChar, blockLength, predeterminedCounter;
    char[] charList;
    int[][] square;
    int[][] predetermined;
    Scanner keyboard = new Scanner(System.in);
    
    /**
     * Constructor for the class. This constructor:
     * 1) scans through a config file
     * 2) initializes a 1-d array of characters (a "key" to match with the ints from the square arrays)
     * 3) initializes and fills 2 2-d int arrays ('square' and 'predetermined')
     */
    public LatinSquaresGame(String config) {
	try {
	    File configFile = new File(config);
	    Scanner configScanner = new Scanner(configFile);
	    numChar = Integer.parseInt(configScanner.next());
	    charList = new char[numChar];
	    for(int i = 0; i < numChar; i++) { charList[i] = configScanner.next().charAt(0); };
	    blockLength = (numChar / 10 + 1) + 2;  //lowest power of 10 >= numChar, + 2

	    square = new int[numChar][numChar];
	    predetermined = new int[numChar][numChar];
	    for(int i = 0; i < numChar; i++) {      //fills with Square and Predetermined arrays with -1
		for(int j = 0; j < numChar; j++) {
		    square[i][j] = -1;
		    predetermined[i][j] = -1;
		}
	    }
    
	    predeterminedCounter = Integer.parseInt(configScanner.next()); // fills predetermined coordinates
	    for(int j = 0; j < predeterminedCounter; j++) {
		int thisRow = Integer.parseInt(configScanner.next());
		int thisColumn = Integer.parseInt(configScanner.next());
		char thisChar = configScanner.next().charAt(0);
		predetermined[thisRow][thisColumn] = indexOf(thisChar, charList);
		square[thisRow][thisColumn] = indexOf(thisChar, charList);
	    }
	} catch(Exception e) {
	    System.out.print("Exception occurred: " + e);
	    System.exit(0);
	}
    }
    
    /**
     *The play method is called by the driver class.
     *Upon being called, it runs the game after an instance object of this class is created.
     */
    public void play() {
	printWelcome();
	while(true) {
	    displaySquare();
	    promptUser();
	    if(isLatinSquare()) break;
	}
	displaySquare();
	printWin();
	System.exit(0);
    }

    /** Prints the Welcome banner */
    public void printWelcome() {
	System.out.println(" _           _   _        ____                                   ");
	System.out.println("| |         | | (_)      / ____|                                 ");
	System.out.println("| |     __ _| |_ _ _ __ | (___   __ _ _   _  __ _ _ __ ___  ___  ");
	System.out.println("| |    / _` | __| | '_ \\ \\___ \\ / _` | | | |/ _` | '__/ _ \\/ __| ");
	System.out.println("| |___| (_| | |_| | | | |____) | (_| | |_| | (_| | | |  __/\\__ \\");
	System.out.println("|______\\__,_|\\__|_|_| |_|_____/ \\__, |\\__,_|\\__,_|_|  \\___||___/ ");
	System.out.println("                         CSCI 1302 | | v2018.fa                  ");
	System.out.println("                                   |_|                           ");
	System.out.print("n = " + charList.length + " " + "{ " + charList[0]);
	for(int i = 1; i < charList.length; i++) System.out.print(", " + charList[i]);
	System.out.println(" }");
	System.out.println("k = " + predeterminedCounter);
    }

    /** Prints the celebratory Winning banner */
    public void printWin() { 
	System.out.println("                                    .''.                ");
	System.out.println("      .\'\'.             *\'\'*    :_\\/_:     .         ");
	System.out.println("      :_\\/_:   .    .:.*_\\/_*   : /\\ :  .'.:.'.         ");
	System.out.println("  .''.: /\\ : _\\(/_  ':'* /\\ *  : '..'.  -=:o:=-         ");
	System.out.println(" :_\\/_:'.:::. /)\\*''*  .|.* '.\'/.'_\\(/_\'.\':\'.\'   ");
	System.out.println(" : /\\ : :::::  \'*\\/_* | |  -= o =- /)\\    '  *"      );
	System.out.println("\'..\'  \':::\'   * /\\ * |'|  .\'/.\\\'.  \'._____\\    ");
	System.out.println("      *        __*..* |  |     :      |.   |' .---\"|   ");
	System.out.println("       _*   .-'   '-. |  |     .--'|  ||   | _|    |    ");
	System.out.println("    .-'|  _.|  |    ||   '-__  |   |  |    ||      |    ");
	System.out.println("    |' | |.    |    ||       | |   |  |    ||      |    ");
	System.out.println(" ___|  \'-\'     \'    \"\"       \'-\'   \'-.\'    \'`      |____");
	System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	System.out.println("    CONGRATULATIONS! YOU COMPLETED THE LATIN SQUARE!");
    }
   
    /** 
     * Displays the square by printing the first row with one method, 
     * and looping through another method for the other rows 
     */
    public void displaySquare() {
	printFirstRow();
	for(int i = 0; i < charList.length; i++)
	    printNonFirstRow(i);
	System.out.println();
	System.out.println();
    }

    /**
     * 1) Prompts the user. 
     * 2) Checks their response and inserts into square if valid input.
     */
    public void promptUser() {
	int thisRow, thisColumn;
	char thisChar;
	boolean validRowColumn = false, validCharacter = false;
	while(true) {
	    System.out.print("latin-squares> ");
	    String[] command = keyboard.nextLine().trim().split(" ");
	    if(command[0].equals("q")) { System.exit(0); }
	    else if (command.length != 3) {
		System.out.println("error> invalid input!");
		System.out.println("command: " + command[0] + command[1]);
		continue;
	    } else {  //if 3 args entered
		thisRow = Integer.parseInt(command[0]);
		thisColumn = Integer.parseInt(command[1]);
		if(thisRow < charList.length && thisColumn < charList.length && predetermined[thisRow][thisColumn] == -1) {
		   { validRowColumn = true; }
		}
		validCharacter = isValidCharacter(command[2].charAt(0), thisRow, thisColumn);
		if(validCharacter && validRowColumn) { thisChar = command[2].charAt(0); }
		else {
		    System.out.println("error> invalid input!");
		    continue;
		}
		break;  // breaks out of while loop if it makes it this far
	    }
	}
	square[thisRow][thisColumn] = indexOf(thisChar, charList); //inserts character if valid

    }
    
    /** Runs checks on inputted character. Makes sure it wasn't predetermined. */
    public boolean isValidCharacter (char c, int row, int column) {
	boolean valid = false;
	for(char character : charList) {
	    if(c == character) {
		valid = true;
	    }
	}
	if(predetermined[row][column] < -1) {
	    valid = false;
	}
	return valid;
    }

    /** Checks to see if current square is a valid Latin Square */
    public boolean isLatinSquare() {
	boolean win = true;
	for(int i = 0; i < charList.length; i++) {
	    for(int j = 0; j < charList.length; j++) {
		if(square[i][j] == -1) {                        //spots must be filled
		    win = false; 
		}
		for(int k = 1; k < charList.length - j; k++) {  //no matching within rows or columns
		    if(square[i][j] == square[i][j + k] || 
		       square[j][i] == square[j + k][i] ) {
			win = false;
		    }
		}
	    }
	}
	return win;
    }

    /** Prints just the first row. Formats based on blockLength (based on power of 10) */
    public void printFirstRow() {
	System.out.println();
	System.out.println();
	System.out.print("   ");
	for(int i = 0; i < charList.length; i++) {
	    int numSpaces = blockLength - (i/10 + 1);
	    if(numSpaces == 1)
		System.out.print(" " + i);
	    else if(numSpaces == 2)
		System.out.print(" " + i + " ");
	    else if(numSpaces == 3)
		System.out.print(" " + i + " " + " ");
	    else if(numSpaces == 4)
		System.out.print("  " + i + "  ");
	    System.out.print(" "); //above the |'s
	}
	System.out.println();
    }

    /** Prints non-first rows. Formats based on blockLength (based on power of 10) */
    public void printNonFirstRow(int rowNumber) {
	System.out.print(rowNumber + " |");
	for(int i = 0; i < charList.length; i++) {
	    int numSpaces = blockLength - 1;     //char takes one space 
	    if(predetermined[rowNumber][i] > -1)
		numSpaces = numSpaces - 2;  
	    for(int j = 0; j < numSpaces/2; j++){
		System.out.print(' ');
		numSpaces--;
	    }
	    if(predetermined[rowNumber][i] > -1) {
		System.out.print("[" + charList[square[rowNumber][i]] + "]");
	    } else if(square[rowNumber][i] == -1) {
		System.out.print(' ');
	    } else {
		System.out.print(charList[square[rowNumber][i]]);
	    }
      	    for(int k = 0; k < numSpaces; k++)
		System.out.print(' ');
	    System.out.print('|');
	}
	System.out.println();
    }
    
    /** Checks the index of a char in a char[]. Used in displaySquare() */
    public int indexOf(char needle, char[] haystack) {
	for(int i = 0; i < haystack.length; i++)
	    if(needle == haystack[i]) return i;
	return -1;
    }
	
}
     