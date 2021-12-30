import java.util.ArrayList;
import java.util.Collections;

public class Evaluation extends Main{

	 //the value of each field represents the number of possible four in a rows
	private static int[][] positions_value = {{3, 4, 5 , 7, 5, 4, 3},
			   			  {4, 6, 8, 10, 8 , 6, 4},
			   			  {5, 8, 11, 13, 11, 8, 5},
			   			  {5, 8, 11, 13, 11, 8, 5},
			   			  {4, 6, 8, 10, 8 , 6, 4},
			   			  {3, 4, 5 , 7, 5, 4, 3}};
	
	//Position || Moves || Rows -> the parameters have been optimized
	private static double[][] params = {{0.6, 0.1, 0.3},
					    {0.1, 0.1, 0.8},
					    {0.3, 0.1, 0.6},
					    {0.1, 0.0, 0.9},
				            {0.5, 0.2, 0.3},
					    {0.3, 0.2, 0.5},
					    {0.5, 0.0, 0.5},
					    {0.6, 0.0, 0.4},
					    {0.3, 0.0, 0.7},
					    {0.5, 0.4, 0.1}};

	private static int score = 0;
	private static int four = 4;
	
	private static double[] currentparams;
	
	private static ArrayList<Integer> row_array = new ArrayList<Integer>();
	private static ArrayList<Integer> col_array = new ArrayList<Integer>();
	private static ArrayList<Integer> set = new ArrayList<Integer>();
	
	private static win w = new win();
	
	public double eval(int player, int versus) {
		//if anyone won return the value
		if (w.Win(player))
			return 1000000 - GUI.moves;
		else if (w.Win(versus))
			return -1000000 + GUI.moves;
		else if (w.draw(board))
			return 10;
		//else evaluate 
		else {
			int position_score = position_score(player, versus);
			score = connected_score(player, versus);
			return currentparams[0]*(position_score)  - currentparams[1]*(GUI.moves) + currentparams[2]*score;
		}
	}
	
	//evaluates positions
	private static int position_score(int player, int versus) {
		int start_value = 138;
		int sum = 0;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (board[i][j] == player) {
					sum += positions_value[i][j];
				}
				else if (board[i][j] == versus) {
					sum -= positions_value[i][j];
				}
			}
		}
		return start_value + sum;
	}
	
	//evaluates rows
	public static int connected_score(int player, int versus) {
		score = 0;
		row_array.removeAll(row_array);
		col_array.removeAll(col_array);
		set.removeAll(set);
		
		for (int j = height-1; j > -1; j--) {
			for (int i = width-1; i >= 0; i--) {
				row_array.add(board[j][i]);
			}
			if(Collections.frequency(row_array, 0) == width) {
				row_array.removeAll(row_array);
				continue;}
			for (int k = 0; k < width-3; k++) {
				for (int l = 0; l < four; l++) {
					set.add(row_array.get(k+l));
				}
				score = pieces(player, versus);
				set.removeAll(set);
			}
			row_array.removeAll(row_array);
		}
		
		//Vertical score
		for (int j = width-1; j > -1; j--) {
			for (int i = height-1; i > -1; i--) {
				col_array.add(board[i][j]);
			}
			if(Collections.frequency(col_array, 0) == width) {
				col_array.removeAll(col_array);
				continue;}
			for (int k = 0; k < height-3; k++) {
				for (int l = 0; l < four; l++) {
					set.add(col_array.get(k+l));
				}
				score = pieces(player, versus);
				set.removeAll(set);
			}
			col_array.removeAll(col_array);
		}
		//diagonal scores
		for (int i = 0; i < height-3; i++) {
			for (int j = 0; j < width-3; j++) {
				for (int k = 0; k < four; k++) {
					set.add(board[i+k][j+k]);
				}
				score = pieces(player, versus);
				set.removeAll(set);
			}
		}
		
		for (int i = 0; i < height-3; i++) {
			for (int j = 0; j < width-3; j++) {
				for (int k = 0; k < four; k++) {
					set.add(board[i+3-k][j+k]);
				}
				score = pieces(player, versus);
				set.removeAll(set);
			}
		}
		
		return score;
	}
	
	//counts the pieces in each set and adds a score
	public static int pieces(int player, int versus) {
		
		if (Collections.frequency(set, player) == 4) {
			score += 10;
		}else if (Collections.frequency(set, player) == 3 && Collections.frequency(set,0) == 1) {
			score += 30;
		}else if (Collections.frequency(set, player) == 2 && Collections.frequency(set,0) == 2) {
			score += 10;}
		
		if (Collections.frequency(set, versus) == 3 && Collections.frequency(set,0) == 1) {
			score -= 20;
		}else if (Collections.frequency(set, versus) == 2 && Collections.frequency(set,0) == 2) {
			score -= 10;}
			
		return score;
	}
	
	//initializes the parameters randomly
	public void initialize() {
		int leftlimit = 0;
		int rightlimit = params.length;
		int rnd = (int)(leftlimit + (Math.random() * (rightlimit - leftlimit)));
		currentparams = params[rnd];
		System.out.println(currentparams[0] + " " + currentparams[1] + " " + currentparams[2]);
	}
}
