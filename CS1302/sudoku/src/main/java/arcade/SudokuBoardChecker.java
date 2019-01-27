package cs1302.arcade;

/**
 * contains all the methods for checking the rows, columns, and boxes for validity
 * another class hould call the static method checkBoard to receive a boolean
 * as to whether the inputted board is valid or not
 * the methods each work by
 * 1) initializing an array that holds the values 1-9
 * 2) for every item in a particular row/column/box, remove the value from that array
 *    if it is found in the row/column/box
 * 3) checking if all items have been removed from the array
 * 4) repeating for every row, column, and box
 */
public class SudokuBoardChecker {

    /** calls methods to check rows, columns, boxes. returns true if valid. false if not */
    public static boolean checkBoard(int[][] board) {
	if(!(checkColumns(board))) { return false; }
	if(!(checkBoxes(board))) { return false; }
	if(!(checkRows(board))) { return false; }
	return true;
    }


    /** 
     * checks every row in the board for validty
     * @param board the 2d int[][] to check
     */
    public static boolean checkRows(int[][] board) {
	System.out.println("Checking rows...");
	for(int i = 0; i < 9; i++) {     //for a particular row...
	    int[] temp = produce0to9();  //0to9 array for this row
	    for(int j = 0; j < 9; j++) { //for every column in this row
		int num = board[i][j];   //remove # from 0to9 of this row
		temp[num - 1] = 0;
	    }
	    if(!(isEmpty(temp))) {
		System.out.println("Row " + i + " is not valid");
		return false;
	    }
	}
	return true;

    }

    /** 
     * checks every column in the board for validty
     * @param board the 2d int[][] to check
     */
    public static boolean checkColumns(int[][] board) {
	System.out.println("Checking columns...");
	for(int i = 0; i < 9; i++) {
	    int[] temp = produce0to9();
	    for(int j = 0; j < 9; j++) {
		int num = board[j][i];
		temp[num - 1] = 0;
	    }
	    if(!(isEmpty(temp))) { // if numbers still left in 0to9
		System.out.println("Column " + i + " is not valid");
		return false;
	    }
	}
	return true;
    }

    /** 
     * checks every box in the board for validty
     * @param board the 2d int[][] to check
     */
    public static boolean checkBoxes(int[][] board) {
	System.out.println("Checking boxes...");
	for(int startRow = 0; startRow < 9; startRow = startRow + 3) {
	    for(int startCol = 0; startCol < 9; startCol = startCol + 3) {
		//this is where the testing for a particular box starts
		int[] temp = produce0to9();
		for(int i = 0; i < 3; i++) {
		    for(int j = 0; j < 3; j++) {
			int num = board[startRow + i][startCol + j];
			temp[num - 1] = 0;
		    }
		}
		if(!isEmpty(temp)) {
		    System.out.println("Box starting at " + startRow +
				       ", " + startCol + " is not valid");
		    return false;
		}
		//this is where the testing for a particular box ends
	    }
	}
	return true;
    }


    /** produces an array with each of the numbers 1-9 inside */
    public static int[] produce0to9() {
	return new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
    }


    /** tests to see if the given array is all 0's */
    public static boolean isEmpty(int[] array) {
	if(array.length != 9) { return false; }
	for(int i = 0; i < 8; i++) {
	    if(array[i] != 0) {  return false; }
	}
	return true;
    }


}
