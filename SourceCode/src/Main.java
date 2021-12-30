import java.util.ArrayList;

public class Main {
	
	public static int height = 6, width = 7;
	public static int player = 1, computer = 2;
	public static int Depth = 4, Depth1 = 5, Depthset, Depthset1;
	private static int saved;
	public static int finish  = 2;
	
	public static int board[][] = new int[height][width];
	private static int[] valid_places;

	public static String winner = "NONE";

	private static boolean cvc, pvc, pvp;
	
	private static win w = new win();
	private static Evaluation eval = new Evaluation();
	private static ArrayList<Integer> valid_positions = new ArrayList<Integer>();
	
	//keeps the game going
	public static void main(String[] args) throws InterruptedException {

		
		//opens the menu
		new MainMenu();
		while (finish != -1) {
			switch(finish) {
			case 0:
				//while finish == 0 the game keeps going
				if((GUI.moves + 1) % 2 == 0 && (pvc || cvc)) {
					setTitle("Computers turn");
					max(Depth, Integer.MIN_VALUE, Integer.MAX_VALUE, computer, player, Depth);
					turn(saved, computer);
					GUI.moves += 1;
					if (cvc)
						Thread.sleep(1000);
				} else if (cvc) {
					setTitle("Computers turn");
					max(Depth1, Integer.MIN_VALUE, Integer.MAX_VALUE, player, computer, Depth1);
					turn(saved, player);
					GUI.moves += 1;
					Thread.sleep(500);
				} else if ((GUI.moves + 1) % 2 == 0 && pvp) {
					setTitle("Player 2's turn");
					int posX = GUI.get_player();
					turn(posX, 2);
				}
				else {
					if (GUI.pvp)
						setTitle("Player 1's turn");
					else
						setTitle("Players turn");
					int posX = GUI.get_player();
					turn(posX, 1);
				}
				//to increase the difficulty with a starting difficulty of 3 or higher
				if ((GUI.moves) % (2*Depthset - 2) == 0 && Depthset > 4) {
					Depth += 2;
				}
				if (cvc && (GUI.moves) % (2*Depthset1 - 2) == 0 && Depthset1 > 5) {
					Depth1 += 2;
				}
				//checks if the board is in an end situation
				if(check(computer)) {
					if (pvp) {
						endstate("Player 2");}
					else if (cvc) {
						endstate("RED");}
					else {
						endstate("Computer");}
				} else if (check(player)) {
					if (pvp) {
						endstate("Player 1");}
					else if (cvc) {
						endstate("YELLOW");}
					else {
						endstate("Player");}
				} else if (full()){
					endstate("Draw");
				}
				break;
			case 1:
				//after an end situation, gets if the game needs to stop or to restart 
				GUI.win(winner);
				if (GUI.get_finish()) {
					finish = -1;
				} else if (GUI.get_NewGame()) {
					finish = 2;
				}
				break;
			case 2:
				Thread.sleep(1000);
				break;
			}
			//to decrease the speed of a move by a depth lower than 5
			if (pvc && Depthset < 5)
				Thread.sleep(150);
		}
	}

	//takes the winner
	public static void endstate(String win) {
		winner = win;
		finish = 1;
	}
	
	//sets the title of the frame
	public static void setTitle(String title) {
		MainMenu.frame.setTitle(title);
	}
	
	//makes/sets all values of the board array
	public static void create_board() {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				board[i][j] = 0;
			}
		}
	}
	
	//to output the board in the console (for testing)
	public static void OutputBoard() {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				System.out.print(board[i][j]);
				System.out.print("|");
			}
			System.out.println();
			System.out.print("--------------");
			System.out.println();
		}
	}
	
	//saves all available positions on the board
	public static int[] valid_positions() {
		valid_positions.removeAll(valid_positions);
		
		for (int i = 0; i < width; i++) {
			if (board[0][i] == 0) {
				valid_positions.add(i);
			}
		}
		int[] arr = valid_positions.stream().mapToInt(i -> i).toArray();
		return arr;
	}
	
	//checks if the current position is an end situation
	public static boolean terminal() {
		return w.draw(board) || w.Win(computer) || w.Win(player);
	}
	
	//maximizing function of minimax with alpha-beta-pruning
	private static double max(int depth, double alpha, double beta, int player, int versus, int dmax) {
		if (depth == 0 || terminal()) {
			return eval.eval(player, versus);
		}
		double max = alpha;
		valid_places = valid_positions();
		for (int elem : valid_places) {
			int row = next_possible(elem);
			board[row][elem] = player;
			GUI.moves += 1;
			double value = min(depth-1, max, beta, player, versus, dmax);
			board[row][elem] = 0;
			GUI.moves -= 1;
			if (value > max) {
				max = value;
				//if the currentdepth is equal to the starting depth and the current value is higher than the previous, the position gets saved
				if (depth == dmax) 
					saved = elem;
				if (max >= beta)
					break;
			}
		}
		return max;
	}
	
	//minimizing function of minimax with alpha-beta-pruning
	private static double min(int depth, double alpha, double beta, int player, int versus, int dmax) {
		if (depth == 0 || terminal()) {
			return eval.eval(player, versus);
		}
		double min = beta;
		valid_places = valid_positions();
		for (int elem : valid_places) {
			int row = next_possible(elem);
			board[row][elem] = versus;
			GUI.moves += 1;
			double value = max(depth-1, alpha, min, player, versus, dmax);
			board[row][elem] = 0;
			GUI.moves -= 1;
			if (value < min) {
				min = value;
				if (min <= alpha)
					break;
			}
		}
		return min;
	}
	
	//Check where the next empty space is 
	public static int next_possible(int input) {
		for (int i = height -1; i > -1; i--) {
			if (board[i][input] == 0)
				return i;
		}
		return -1;
	}
	//Check if the column has still space and is valid
	public static boolean is_valid(int input) {
		if (board[0][input] == 0)
			return true;
		return false;
	}
	
	//checks if the board is full by calling a method in another class
	private static boolean full() {
		return w.draw(board);
	}
	
	//checks if someone won by calling a method in another class
	private static boolean check(int player) {
		return w.Win(player);
	}
	
	//returns the width of the board
	public static  int get_width() {
		return width;
	}
	
	//returns the height of the board
	public static  int get_height() {
		return height;
	}
	
	//sets the depth to a specific value
	public static void set_depth(int x) {
		Depth = x;
		Depthset = x;
		System.out.println("Depth set: " + Depth);
	}
	
	//sets the depth1 to a specific value
	public static void set_depth1(int x) {
		Depth1 = x;
		Depthset1 = x;
		System.out.println("Depth1 set: " + Depth1);
	}
	
	//places the piece on the board
	public static void turn(int x, int player) {
		int y = next_possible(x);
		if (y == -1) {
			GUI.moves -= 1;
			setTitle("INVALID COLUMN");
		}else {
			board[y][x] = player;
			GUI.Update_Board(y, x, player);
		}
	}
	
	//starts the game
	public static void start_game() {
		create_board();
		//get state
		pvp = MainMenu.get_pvp();
		pvc = MainMenu.get_pvc();
		cvc = MainMenu.get_cvc();
		//initializes the parameters of the evaluation function
		if (pvc || cvc) 
			eval.initialize();
		GUI.moves = 0;
		finish = 0;
		System.out.println("Game started");
	}
}
