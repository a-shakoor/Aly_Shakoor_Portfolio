package cs1302.arcade;

/** 
 * holds the templates 
 * and the methods for removing filled spots randomly
 * another class should call returnTemplate() to get one of 
 * these templates randomly
 */
public class SudokuTemplates {



    public static int[][] returnTemplate() {
	int[][][] templates = constructFullTemplates();
	int templateIndex = (int) Math.floor(Math.random() * 3); //random template
	int[][] template = templates[templateIndex];

	for(int i = 0; i < 15; i++) {
	    int index1 = (int) Math.floor(Math.random() * 9);
	    int index2 = (int) Math.floor(Math.random() * 9);
	    if(template[index1][index2] != 0) {
		template[index1][index2] = 0;
	    } else { i--; }
	}

	return template;

    }

    
    private static int[][][] constructFullTemplates() {
	int[][][] templates = new int[3][9][9];
	int[][] template0 =
	    {
		{2,9,5,7,4,3,8,6,1},
		{4,3,1,8,6,5,9,2,7},
		{8,7,6,1,9,2,5,4,3},
		{3,8,7,4,5,9,2,1,6},
		{6,1,2,3,8,7,4,9,5},
		{5,4,9,2,1,6,7,3,8},
		{7,6,3,5,2,4,1,8,9},
		{9,2,8,6,7,1,3,5,4},
		{1,5,4,9,3,8,6,7,2}
	    };
       	int[][] template1 =
	    {
		{6,3,9,5,7,4,1,8,2},
		{5,4,1,8,2,9,3,7,6},
		{7,8,2,6,1,3,9,5,4},
		{1,9,8,4,6,7,5,2,3},
		{3,6,5,9,8,2,4,1,7},
		{4,2,7,1,3,5,8,6,9},
		{9,5,6,7,4,8,2,3,1},
		{8,1,3,2,9,6,7,4,5},
		{2,7,4,3,5,1,6,9,8}
	    };
	int[][] template2 =
	    {
		{9,8,3,7,6,2,4,1,5},
		{6,5,4,9,1,3,7,8,2},
		{2,1,7,8,5,4,3,9,6},
		{3,9,5,4,8,1,6,2,7},
		{8,4,6,3,2,7,9,5,1},
		{1,7,2,5,9,6,8,4,3},
		{5,6,9,1,3,8,2,7,4},
		{7,2,8,6,4,5,1,3,9},
		{4,3,1,2,7,9,5,6,8},
	    };
	templates[0] = template0;
	templates[1] = template1;
	templates[2] = template2;
	return templates;
    } //constructTemplates()
    
}
