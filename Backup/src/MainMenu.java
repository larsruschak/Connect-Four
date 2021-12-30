import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class MainMenu implements ActionListener {
	
	static JFrame frame;

	JPanel panel = new JPanel();
	
	JLabel title = new JLabel("Connect Four");
	JLabel credits = new JLabel("Created by Lars Ruschak");
	
	JButton newgame;
	JButton exit;
	JButton[] difficulty;
	JButton playervplayer;
	JButton playervcomputer;
	JButton computervcomputer;
	JButton back;
	JButton reset;
	
	public static boolean pvp = false;
	public static boolean pvc = false;
	public static boolean cvc = false;
	public static boolean initialized = false;
	
	public MainMenu() {
		
		panel.setLayout(null);
		Border border = BorderFactory.createLineBorder(Color.BLACK,2);
		panel.setBorder(border);
		
		//creates the MainMenu interface
		menu();
		
		//initialize all Buttons
		difficulty = new JButton[4];
		for (int i = 0; i < 4; i++) {
			difficulty[i] = new JButton("Difficulty: " + (i+1));
			difficulty[i].setActionCommand("" + i);
			difficulty[i].addActionListener(this);
			difficulty[i].setFont(new Font("Arial", Font.PLAIN, 20));
		}
		playervplayer = new JButton("Player vs Player");
		playervplayer.addActionListener(this);
		playervplayer.setFont(new Font("Arial", Font.PLAIN, 20));
		playervcomputer = new JButton("Player vs Computer");
		playervcomputer.addActionListener(this);
		playervcomputer.setFont(new Font("Arial", Font.PLAIN, 20));
		computervcomputer = new JButton("Computer vs Computer");
		computervcomputer.addActionListener(this);
		computervcomputer.setFont(new Font("Arial", Font.PLAIN, 20));
		back = new JButton("Back");
		back.addActionListener(this);
		
		//settings for the MenuFrame
		frame = new JFrame("Connect Four Menu");
		frame.setSize(500, 600);
		frame.add(panel);
		frame.setContentPane(panel);
		frame.getContentPane().setBackground(new Color(0,0,170));
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	//everything on the first menu
	public void menu() {
		
		title.setForeground(new Color(255,215,0));
		title.setFont(new Font("Arial", Font.PLAIN, 40));
		title.setBounds(128, 20, 1000, 100);
		panel.add(title);
		credits.setForeground(Color.WHITE);
		credits.setFont(new Font("Arial", Font.PLAIN, 15));
		credits.setBounds(165, 450, 1000, 100);
		panel.add(credits);
		
		newgame = new JButton("New Game");
		newgame.setBounds(100, 130, 300, 100);
		newgame.addActionListener(this);
		newgame.setFocusPainted(false);
		newgame.setFont(new Font("Arial", Font.PLAIN, 20));
		panel.add(newgame);
		exit = new JButton("Exit");
		exit.setBounds(100, 330, 300, 100);
		exit.addActionListener(this);
		exit.setFont(new Font("Arial", Font.PLAIN, 20));
		panel.add(exit);
	}
	
	//sets the Bounds of each difficulty Button
	public void difficulty() {
		for (int i = 0; i < 4; i++) {
			difficulty[i].setBounds(100, (i * 120) + 50, 300, 100);
			panel.add(difficulty[i]);
		}
	}

	@Override
	//reactions if a Button is pressed
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == exit) {
			System.exit(0);
		}
		if (e.getSource() == newgame) {
			panel.removeAll();
			playervplayer.setBounds(100, 50,300,100);
			playervcomputer.setBounds(100, 170, 300, 100);
			computervcomputer.setBounds(100, 290, 300, 100);
			back.setBounds(100, 410, 300, 100);
			back.setFont(new Font("Arial", Font.PLAIN, 20));
			panel.add(playervplayer);
			panel.add(playervcomputer);
			panel.add(computervcomputer);
			panel.add(back);
			frame.setContentPane(panel);
		}
		if (e.getSource() == playervplayer) {
			frame.dispose();
			cvc = false;
			pvc = false;
			pvp = true;
			new GUI();
			Main.start_game();
		}
		if (e.getSource() == playervcomputer) {
			panel.removeAll();
			difficulty();
			cvc = false;
			pvp = false;
			pvc = true;
			frame.setContentPane(panel);
		}
		for (int i = 0; i < 4; i++) {
			if (e.getSource() == difficulty[i]) {
				if (cvc) {
					if (!initialized) {
						Main.set_depth1((i+1)*2+1);
						frame.setTitle("Difficulty for Computer 2 (Red):");
						difficulty();
						initialized = true;
					}
					else {
						initialized = false;
						Main.set_depth((i+1)*2);
						frame.dispose();
						new GUI();
						Main.start_game();
					}
				}
					
				else {
					Main.set_depth((i+1)*2);
					frame.dispose();
					new GUI();
					Main.start_game();
				}
			}
		}
		if(e.getSource() == computervcomputer) {
			panel.removeAll();
			cvc = true;
			pvp = false;
			pvc = false;
			frame.setTitle("Difficulty for Computer 1 (Yellow):");
			difficulty();
			frame.setContentPane(panel);
		}
		if (e.getSource() == back) {
			panel.removeAll();
			menu();
			frame.setContentPane(panel);
		}
	}
	
	public static boolean get_cvc() {
		return cvc;
	}
	
	public static boolean get_pvc() {
		return pvc;
	}
	
	public static boolean get_pvp() {
		return pvp;
	}
}
