package cs1302.game;
import java.io.PrintWriter;
import java.io.File;

public class LatinSquaresDriver {

    public static void main(String[] args) {
	try {
	    if(args.length == 1) {
		String config = args[0];
		LatinSquaresGame driver = new LatinSquaresGame(config);
		driver.play();
	    }
	    else if(args[0].equals("--gen")) {
		String file_path = args[1];
		int size = Integer.parseInt(args[2]);
		int predeterminedCounter = Integer.parseInt(args[3]);
		char[] charList = new char[size];
		for(int i = 4; i < args.length; i++) {
		    charList[i-4] = args[i].charAt(0);
		}
	    
		File new_file = new File(file_path);
		new_file.createNewFile();
		PrintWriter writer = new PrintWriter(new_file);
		
		writer.print(size);
		for(char character : charList) {  writer.print(" " + character); }
		writer.print(" " + predeterminedCounter);
		
		LatinSquaresDriver predeterminedWriter = new LatinSquaresDriver();
		predeterminedWriter.generateAndPrintPredetermined(size, predeterminedCounter, charList, writer);
	    
		writer.close();
		LatinSquaresGame driver = new LatinSquaresGame(new_file.toString());
		driver.play();

	    } else {
		System.out.println("usage: java LatinSquaresDriver path/to/some/file");
	    }
	} catch(Exception e) {
	    System.out.println("exception " + e);
	}
    }

    /** Generates predetermined values ranomdly
     * Random in this case means it randomly picks a row, column, and character
     * If this makes the square invalid then it repicks a row, column, and character.
     * This extra credit took me 2 hours on its own, please give me credit lol.
     */
    public void generateAndPrintPredetermined(int size, int predeterminedCounter, char[] charList, PrintWriter writer) {
	    int[][] square =new int[size][size];	    
	    int counter = 0;
	    while(counter < predeterminedCounter) {
		int locX = (int) Math.floor(Math.random() * size);
		int locY = (int) Math.floor(Math.random() * size);
		int charZ = (int) Math.floor(Math.random() * size);
		boolean restartWhile = false;
		for(int i = 0; i < size; i++) {
		    if(square[locX][i] == charZ) { restartWhile = true; }
		    else if(square[i][locY] == charZ) { restartWhile = true; }
		}
		if(restartWhile) { continue; }
		square[locX][locY] = charZ;
		writer.print(" " + locX + " " + locY + " " + charList[charZ]);
		counter++;
	    }
	    
    }
}