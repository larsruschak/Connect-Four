
public class win extends Main {
	
	//Checks if the given player won
	public boolean Win(int player) {
		//horizontal
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width-3; j++) {
				if (board[i][j] == player && board[i][j+1] == player && board[i][j+2] == player && board[i][j+3] == player) {
					return true;
				}
			}
		}
		//vertical
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height-3; j++) {
				if (board[j][i] == player && board[j+1][i] == player && board[j+2][i] == player && board[j+3][i] == player) {
					return true;
				}
			}
		}
		//diagonal
		for (int i = 0; i < width-3; i++) {
			for (int j = 0; j < height-3; j++) {
				if (board[j][i] == player && board[j+1][i+1] == player && board[j+2][i+2] == player && board[j+3][i+3] == player) {
					return true;
				}
			}
		}
		
		for (int i = 0; i < width-3; i++) {
			for (int j = 3; j < height; j++) {
				if (board[j][i] == player && board[j-1][i+1] == player && board[j-2][i+2] == player && board[j-3][i+3] == player) {
					return true;
				}
			}
		}
		return false;
	}
	
	//is not every top position equal to 0, the board is full
	public boolean draw(int[][] board) {
		for (int k = 0; k < width; k++) {
			if (board[0][k] == 0)
				return false;
		}
		return true;
	}
	
}
