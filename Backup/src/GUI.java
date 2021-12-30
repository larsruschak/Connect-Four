import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI extends MainMenu implements MouseListener {
	
	private static JLabel[][] slots;
	private static ImageIcon image;
	
	private static ImageIcon Player = new ImageIcon("resources/Player.jpg");
	private static ImageIcon Computer = new ImageIcon("resources/Computer.jpg");

	private static int width = Main.get_width();
	private static int height = Main.get_height();
	private static int posX;
	public static int moves = 0;
	
	public static boolean newGame = false;
	private static boolean finish = false;
	public static boolean clicked = false;
	
	
	public GUI() {
		
		panel.removeAll();
		image = new ImageIcon("resources/Cell.jpg");
		
		panel.setLayout(new GridLayout(height, width));
		panel.addMouseListener(this);
		
		slots = new JLabel[height][width];
		
		for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                slots[i][j] = new JLabel();
                slots[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                slots[i][j].setIcon(image);
                panel.add(slots[i][j]);
            }
        }
		
		frame.setContentPane(panel);
		//the values 17 and 14 are width and height of the white borders at the top of the GUI
		frame.setSize(width*100+17,height*100+40);

	}
	
	//updates the board at the given position
	public static void Update_Board(int row, int column, int player) {
		if (player == 1)
			slots[row][column].setIcon(Player);
		else
			slots[row][column].setIcon(Computer);
	}
	
	//Pop up menu if a player won
	public static void win(String winner) {
		int n;
		if (winner != "Draw")
			n = JOptionPane.showConfirmDialog(frame, "Winner: " + winner + " ! Exit? ","", JOptionPane.YES_NO_OPTION);
		else 
			n = JOptionPane.showConfirmDialog(frame, "It's a Draw! Exit? ","", JOptionPane.YES_NO_OPTION);
		if (n < 1) {
            System.exit(0);
		}
		else {
			frame.dispose();
        	new MainMenu();
        	moves = 0;
        	newGame = true;
        }
	}
	
	public static boolean get_NewGame() {
		return newGame;
	}
	
	public static boolean get_finish() {
		return finish;
	}
	
	//waits till the player makes an input on the GUI, returns the x coordinate of the click
	public static int get_player() throws InterruptedException {
		while (!clicked) {
			Thread.sleep((long) 0.01);
		}
		clicked = false;
		return posX;
	}

	//Checks if the GUI is clicked
	@Override
	public void mouseClicked(MouseEvent e) {
		if (!cvc) {
			if (pvp || moves % 2 == 0) {
				posX = e.getX()/100;
				if (posX > 6)
					posX = 6;
				moves += 1;
				clicked = true;
			}
			else {
				frame.setTitle("COMPUTING");
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}