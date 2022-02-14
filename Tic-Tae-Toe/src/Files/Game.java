package Files;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Game extends JFrame implements ActionListener, ItemListener{
	JButton b[],startGame, exit;
	JPanel headingPanel, bodyPanel, dashPanel;
	JLabel l1, welcomeLabel, mode;
	String player = "X", winner = null;
	Box vertical, modeHorizontal;
	JComboBox choice;
	String level = "Easy", player1 = "X", player2 = "O";
	ArrayList<String> board;
	String ch[] = {"Easy", "Medium", "Captain", "MultiPlayer"};
	int wc1, wc2, wc3;
	int scoreX = 0, scoreO = 0;
	public Game() {
		headingPanel = new JPanel();
		l1 = new JLabel("Tic Tac Toe");
		headingPanel.add(l1);
		l1.setFont(new Font("Calibri", Font.BOLD, 50));
		l1.setForeground(Color.white);
		headingPanel.setBackground(Color.BLUE);
		headingPanel.setMaximumSize(new Dimension(2000, 70));
		
		// Showing which mode is selected..
		mode = new JLabel("Level : " + level);
		mode.setBackground(Color.WHITE);
		mode.setForeground(Color.BLUE);
		mode.setFont(new Font("Cambria",Font.CENTER_BASELINE, 50));
		modeHorizontal = Box.createHorizontalBox();
		modeHorizontal.add(mode);
		
		
		// dashPanel...
		dashPanel = new JPanel();
		dashPanel.setBackground(Color.WHITE);
		
		welcomeLabel = new JLabel("Start game");
		welcomeLabel.setFont(new Font("Cambria", Font.BOLD, 60));
		welcomeLabel.setBackground(Color.BLUE);
		
		choice = new JComboBox(ch);
		choice.addItemListener(this);
		for(int i=0; i<ch.length; i++) {
			if(ch[i] == level)
				choice.setSelectedIndex(i);
		}
		
		choice.setMaximumSize(new Dimension(300, 40));
		choice.setFont(new Font("calibri", Font.PLAIN, 25));
		choice.setBorder(new LineBorder(Color.BLUE, 0, true));
		
		JLabel l2 = new JLabel("Select mode");
		l2.setAlignmentX(CENTER_ALIGNMENT);
		l2.setFont(new Font("calibri", Font.ITALIC, 30));
		l2.setForeground(Color.BLUE);
		
		// Footer button works here
		startGame = new JButton("Start game");
		startGame.setBackground(Color.BLUE);
		startGame.setForeground(Color.WHITE);
		startGame.setFont(new Font("calibri", Font.TRUETYPE_FONT, 25));
		startGame.setBorder(new EmptyBorder(new Insets(10, 50, 5, 50)));
		startGame.addActionListener(this);
		
		exit = new JButton("Exit");
		exit.setBackground(Color.RED);
		exit.setForeground(Color.WHITE);
		exit.setFont(new Font("calibri", Font.TRUETYPE_FONT, 25));
		exit.setBorder(new EmptyBorder(new Insets(10, 50, 5, 50)));
		exit.addActionListener(this);
		
		Box vt = Box.createVerticalBox();
		vt.add(Box.createVerticalStrut(100));
		Box hh1 = Box.createHorizontalBox();
		hh1.add(welcomeLabel);
		vt.add(hh1);
		vt.add(Box.createVerticalStrut(50));
		vt.add(l2);
		vt.add(choice);
		vt.add(Box.createVerticalStrut(100));
		
		Box hh2 = Box.createHorizontalBox();
		hh2.add(startGame);
		hh2.add(Box.createHorizontalStrut(100));
		hh2.add(exit);
		vt.add(hh2);
		
		dashPanel.add(vt);
		
		// Arranging items
		Container container = getContentPane();
		vertical = Box.createVerticalBox();
		container.add(vertical);
		vertical.add(headingPanel);
		vertical.add(dashPanel);
		
		// JFrame activity...
		this.setVisible(true);
		this.setSize(800, 600);
		this.setMinimumSize(new Dimension(800, 600));
		this.setTitle("Game");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setExtendedState(MAXIMIZED_BOTH);
	}
	
	void newGame() {
		reset();
		board = new ArrayList<String>();
		for(int i=0; i<9; i++) {
			board.add(" ");
		}
		bodyPanel = new JPanel();
		bodyPanel.setBackground(Color.white);
		bodyPanel.setLayout(new GridLayout(3, 3, 2, 2));
		bodyPanel.setBorder(new EmptyBorder(new Insets(50, 200, 100, 200)));
		
		// Buttons works...
		b = new JButton[9];
		for(int i=0; i<9; i++) {
			b[i] = new JButton("");
			b[i].setBackground(Color.CYAN);
			b[i].setForeground(Color.BLUE);
			b[i].setFont(new Font("Calibri", Font.BOLD, 50));
			b[i].addActionListener(this);
			bodyPanel.add(b[i]);
		}
		if(level != "MultiPlayer") {
			int random = (int)(Math.random()*9);
			makeMove(random, "O", true);// Computer randomly make a move
			player1 = "You";
			player2 = "Computer";
		}
		// Setting level markup on playing panel
		mode.setText("Level : " + level);
		vertical.remove(dashPanel);
		vertical.add(modeHorizontal);
		vertical.add(bodyPanel);
		getContentPane().revalidate();// Important
		getContentPane().repaint();
	}
	
	boolean isWinner(int square,String player) {
		int row = square/3;
		if(board.get(row*3) == player && board.get(row*3 + 1) == player && board.get(row*3 + 2) == player) {
			wc1 = row*3;wc2=row*3 + 1; wc3 = row*3 + 2;
			return true;
		}
		int col = square%3;
		if(board.get(col) == player && board.get(col + 3) == player && board.get(col + 6) == player) {
			wc1 = col; wc2 = col + 3; wc3 = col + 6;
			return true;
		}
		if(square == 0 || square == 4 || square == 8) {
			if(board.get(0) == player && board.get(4) == player && board.get(8) == player) {
				wc1 = 0; wc2 = 4; wc3 = 8;
				return true;
			}
		}
		if(square == 2 || square == 4 || square == 6) {
			if(board.get(2) == player && board.get(4) == player && board.get(6) == player ) {
				wc1 = 2; wc2 = 4; wc3 = 6;
				return true;
			}
		}
		return false;
	}
	
	public void actionPerformed(ActionEvent ae) {
		JButton userPressed = (JButton)ae.getSource();
		// for multiplayer have to code 
		String st = userPressed.getText();
		if(st.equalsIgnoreCase("Start game")) {
			level = choice.getSelectedItem().toString();
//			JOptionPane.showMessageDialog(getRootPane(), level);
			newGame();
		}else
		if(st.equalsIgnoreCase("exit")) {
			System.exit(0);
		}else {
		int square = getSquare(userPressed);
		makeMove(square, player, true); // user will always make true move.
		if(isWinner(square, player)) {
			b[wc1].setBackground(Color.ORANGE);
			b[wc2].setBackground(Color.ORANGE);
			b[wc3].setBackground(Color.ORANGE);
			gameOver();
		}
		// check if computer is playing...
		if(level != "MultiPlayer") {
			player = "O";
			if(level == "Easy") {
				square = getRandomComputerMoves();
			}else if(level == "Medium") {
				int a = (int)(Math.random()*2);
				if(a == 0) {
					square = getRandomComputerMoves();
				}else {
					square = getSmartComputerMove(player);
				}
			}
			else {
				square = getSmartComputerMove(player);
			}
			makeMove(square, player, true);
			if(isWinner(square, player)) {
				b[wc1].setBackground(Color.ORANGE);
				b[wc2].setBackground(Color.ORANGE);
				b[wc3].setBackground(Color.ORANGE);
				gameOver();
			}
			player = "X";
		}else {
			player = (player == "X") ? "O" : "X";
		}
		
		if(availableMoves().size() == 0 && winner == null) {
			gameOver();
		}
		}
	}
	void gameOver() {
		JOptionPane.showMessageDialog(getRootPane(), "Game Over");
		try {
			Thread.sleep(500);
		}catch(InterruptedException e) {
			System.out.println(e);
		}
		
		if(winner == null) {
			welcomeLabel.setText("Game draw");
		}
		else {
			if(winner == "X") {
				scoreX += 1;
				welcomeLabel.setText("Winner is " + player1); 
			}
			else {
				scoreO += 1;
				welcomeLabel.setText("Winner is " + player2);
			}
		}
		disableAll();
		vertical.remove(bodyPanel);
		vertical.remove(modeHorizontal);
		vertical.add(dashPanel);
		getContentPane().revalidate();// Important
		getContentPane().repaint();
	}
	public void reset() {
		board = null;
		winner = null;
		b = null;
		bodyPanel = null;
		player1 = "X";
		player2 = "O";
	}
	int getSquare(JButton btn) {
		for(int i=0; i<9; i++) {
			if(btn == b[i]) return i;
		}
		return -1;
	}
	// sudo => for actually making a move or just assuming.
	void makeMove(int square, String player, boolean sudo) {
		if(board.get(square) == " ") {
			if(sudo) {
				 b[square].setText(player);
				 if(player == "O") {
					 b[square].setBackground(Color.RED);
				 }else {
					 b[square].setBackground(Color.BLUE);
				 }
				 b[square].setEnabled(false);
			}
			board.set(square, player);
			if(isWinner(square, player)) winner = player;
		}
	}
	void disableAll() { // Disable all button if no more chance is left.
		for(int i=0; i<9; i++) {
			b[i].setEnabled(false);
		}
	}
	ArrayList<Integer> availableMoves() {
		ArrayList<Integer> moves = new ArrayList<Integer>();
		for(int i=0; i<9; i++) {
			if(board.get(i) == " ")
				moves.add(i);
		}
		return moves;
	}
	int getRandomComputerMoves() {
		ArrayList<Integer> moves = availableMoves();
		int choice = (int)(Math.random()*(moves.size()));
		return moves.get(choice);
	}
	int getSmartComputerMove(String player) {
		return minimax(player, true)[0];
	}
	int[] minimax(String player,boolean isMax) {
		String other_player = (player == "X") ? "O" : "X";
		ArrayList<Integer> am = availableMoves();
		int as = am.size();
		if(winner == player) {
			if(isMax) {
				return new int[] {0, -1*(as +1)};
			}else {
				return new int[] {0, (as + 1)};
			}
		}else if(winner == other_player) {
			if(isMax) {
				return new int[] {0, -1*(as +1)};
			}else {
				return new int[] {0, (as + 1)};
			}
		}else if(as == 0) {
			return new int[] {0, 0};
		}
		
		if(isMax) {
			int bestScore[] = {0, Integer.MIN_VALUE};
			for(int i=0; i<as; i++) {
				makeMove(am.get(i), player, false);
				int score[] = minimax(other_player, false);
				board.set(am.get(i), " ");
				winner = null;
				score[0] = am.get(i);
				if(score[1] > bestScore[1])
					bestScore = score;
			}
			return bestScore;
		}else {
			int bestScore[] = {0, Integer.MAX_VALUE};
			for(int i=0; i<as; i++) {
				makeMove(am.get(i), player, false);
				int score[] = minimax(other_player, true);
				board.set(am.get(i), " ");
				winner = null;
				if(score[1] < bestScore[1])
					bestScore = score;
			}
			return bestScore;
		}
	}
	public static void main(String args[]) {
		new Game();
	}
	@Override
	public void itemStateChanged(ItemEvent e) {
		// empty
	}
}