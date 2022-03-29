package seoultech.se.tetris.component;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLOutput;
import java.util.Random;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.text.*;

import seoultech.se.tetris.blocks.Block;
import seoultech.se.tetris.blocks.IBlock;
import seoultech.se.tetris.blocks.JBlock;
import seoultech.se.tetris.blocks.LBlock;
import seoultech.se.tetris.blocks.OBlock;
import seoultech.se.tetris.blocks.SBlock;
import seoultech.se.tetris.blocks.TBlock;
import seoultech.se.tetris.blocks.ZBlock;



public class Board extends JFrame {

	private static final long serialVersionUID = 2434035659171694595L;
	
	public static final int HEIGHT = 20;
	public static final int WIDTH = 10;
	public static final int NEXT_WIDTH = 6;
	public static final int NEXT_HEIGHT = 3;
	public static String BORDER_CHAR = "X";
	public static String BLOCK_CHAR = "O";
	public static String BLANK_CHAR = " ";
	public static final String win_BORDER_CHAR = "X";
	public static final String win_BLOCK_CHAR = "O";
	public static final String win_BLANK_CHAR = "     ";
	public static final String mac_BORDER_CHAR = "X";
	public static final String mac_BLOCK_CHAR = "O";
	public static final String mac_BLANK_CHAR = " ";
	public static String os;

	private JTextPane pane;
	private JTextPane next_pane;
	private JTextPane score_pane;
	private JTextPane next_block_pane;
	private JPanel main_panel;
	private JPanel side_panel;
	private int[][] board;
	private int[][] next_board;
	private Color[][] color_board;
	private KeyListener playerKeyListener;
	private SimpleAttributeSet styleSet;
	private Timer timer;
	private Block curr;
	private Block next_block;
	private boolean ispaused = false;
	int x = 3; //Default Position.
	int y = 0;
	private static int score = 0;

	private static final int initInterval = 1000;
	private static final int curr_block = 1;
	private static final int completed_block = 2;
	private static final int empty_block = 0;

	int sprint=0;
	private static final int SPMAX=900;


	public Board() {
		super("SeoulTech SE Tetris");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(500, 600);

		this.setLayout(new GridLayout(1,2,10,0));
		main_panel = new JPanel();

		// readOS
		os = System.getProperty("os.name").toLowerCase();
		//System.out.println(os);
		if(os.contains("win")){
			BORDER_CHAR = win_BORDER_CHAR;
			BLOCK_CHAR = win_BLOCK_CHAR;
			BLANK_CHAR = win_BLANK_CHAR;
		}
		else
		{
			BORDER_CHAR = mac_BORDER_CHAR;
			BLOCK_CHAR = mac_BLOCK_CHAR;
			BLANK_CHAR = mac_BLANK_CHAR;
		}


		//Board display setting.
		pane = new JTextPane();
		pane.setEditable(false);
		pane.setBackground(Color.BLACK);
		CompoundBorder border = BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.GRAY, 10),
				BorderFactory.createLineBorder(Color.DARK_GRAY, 5));
		pane.setBorder(border);
		main_panel.add(pane);
		//this.getContentPane().add(game_pane, BorderLayout.WEST);

		next_pane = new JTextPane();
		next_pane.setEditable(false);
		next_pane.setBackground(Color.BLACK);
		next_pane.setBorder(border);

		score_pane = new JTextPane();
		score_pane.setEditable(false);
		score_pane.setBackground(Color.BLACK);
		score_pane.setBorder(border);

		side_panel = new JPanel();
		side_panel.add(score_pane, new GridLayout(4,1));
		side_panel.setLayout(new GridLayout(4,1,10,30));
		side_panel.add(next_pane);
		side_panel.add(score_pane);


		this.add(main_panel);
		this.add(side_panel);

		//Document default style.
		styleSet = new SimpleAttributeSet();
		StyleConstants.setFontSize(styleSet, 18);
		StyleConstants.setFontFamily(styleSet, "Courier");
		StyleConstants.setBold(styleSet, true);
		StyleConstants.setForeground(styleSet, Color.WHITE);
		StyleConstants.setAlignment(styleSet, StyleConstants.ALIGN_CENTER);

		this.setVisible(true);
		//Set timer for block drops.
		timer = new Timer(initInterval, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				moveDown();
				drawBoard();
				//System.out.println(timer.getDelay());
				if(sprint>SPMAX){
					sprint=SPMAX;
				}
				timer.setDelay(initInterval-sprint);
			}
		});


		//Initialize board for the game.
		board = new int[HEIGHT][WIDTH];
		next_board = new int[NEXT_HEIGHT][NEXT_WIDTH];
		color_board = new Color[HEIGHT][WIDTH];
		playerKeyListener = new PlayerKeyListener();
		addKeyListener(playerKeyListener);
		setFocusable(true);
		requestFocus();

		//Create the first block and draw.
		curr = getRandomBlock();
		next_block = getRandomBlock();
		placeBlock();
		drawBoard();
		timer.start();
	}

	public void tempTask(){
	}

	private Block getRandomBlock() {
		Random rnd = new Random();
		int block = rnd.nextInt(7);
		switch(block) {
		case 0:
			return new IBlock();
		case 1:
			return new JBlock();
		case 2:
			return new LBlock();
		case 3:
			return new ZBlock();
		case 4:
			return new SBlock();
		case 5:
			return new TBlock();
		case 6:
			return new OBlock();
		}
		return new LBlock();
	}

	private void placeBlock() {
		//System.out.println("width : " + curr.width() + " height : " + curr.height());
		for(int j=0; j<curr.height(); j++) {
			int rows = j;//y+j == 0 ? 0 : y+j-1;
			int offset = x;//rows * (WIDTH+3) + x + 1;
			for(int i=0; i<curr.width(); i++) {
				if(board[y+j][x+i] == 0) {//요게 히트!!! 보드에 0이 아니면 그대로 유지해야함
					board[y + j][x + i] = curr.getShape(i, j);
					color_board[y+j][x+i] = curr.getColor();
				}
			}
		}
		placeNextBlock();
	}

	private void placeNextBlock() {

		//System.out.println("width : " + curr.width() + " height : " + curr.height());
		for(int j=0; j<NEXT_HEIGHT; j++){
			for(int i=0; i<NEXT_WIDTH; i++){
				next_board[j][i] = 0;
			}
		}
		for(int j=1; j<next_block.height() + 1; j++){
			for(int i=1; i<next_block.width() + 1; i++){
				next_board[j][i] = next_block.getShape(i-1,j-1);
			}
		}
	}

	private void eraseCurr() {
		for (int i = y; i < y + curr.height(); i++) {
			for (int j = x; j < x + curr.width(); j++)
				if(curr.getShape(j-x,i-y) != 0) // 요것도 히트 무작정 지우면 안됨
					board[i][j] = 0;
		}
	}

	private boolean isBlocked(char move){ //블럭이 갈 수 있는지 확인하는 함수('d' : 아래, 'r' : 오른쪽, 'l' : 왼쪽)
		if(move == 'd') { //down
			if(y + curr.height() < HEIGHT) {
				for (int i = x; i < x + curr.width(); i++) {
					int lowest = y + curr.height() - 1;
					while(curr.getShape(i-x, lowest-y) == 0)
						lowest--;
					//System.out.println();
					if (board[lowest+1][i] != 0 && curr.getShape(i - x, lowest-y) != 0) {
						return true;
					}
				}
			}
			else return true;
		}
		else if(move == 'l') { //25일에 고쳐야함 회의하면서 ㄱㄱ
			if(x > 0) {
				for (int i = y; i < y + curr.height(); i++) {
					//System.out.print(x + " " + y + " ");
					int mostLeft = x;
					while(curr.getShape(mostLeft-x, i-y) == 0)
							mostLeft++;
						//if (board[i][x - 1] == 1 && curr.getShape(0, i - y) == 1) {
					if (board[i][mostLeft-1] != 0 && curr.getShape(mostLeft-x, i - y) != 0) {
						return true;
					}
				}
			}
			else return true;
		}
		else if(move == 'r') { //25일날 고쳐야함 회의하면서 ㄱㄱ
			if(x + curr.width() < WIDTH) {
				for (int i = y; i < y + curr.height(); i++) {
					//System.out.print(x + " " + y + " ");
					int mostRight = x + curr.width() - 1;
					while(curr.getShape(mostRight-x, i-y) == 0) mostRight--;
					if(board[i][mostRight + 1] != 0 && curr.getShape(mostRight-x, i-y) != 0){
						return true;
					}
//					if (board[i][x + curr.width()] == 1 && curr.getShape(curr.width() - 1, i - y) == 1) {
//						return true;
//					}
				}
			}
			else return true;
		}
		else if(move == 't') {
			curr.rotate();
			int tmpX = x + curr.getCentermovedX();
			int tmpY = y + curr.getCentermovedY();
			if(tmpX >= 0 && tmpX + curr.width()-1 < WIDTH && tmpY >= 0 && tmpY + curr.height() < HEIGHT){
				//System.out.println("IN!!");
				for(int i=tmpY; i<tmpY+curr.height(); i++) {
					for (int j = tmpX; j < tmpX + curr.width(); j++) {
						if (board[i][j] != 0 && curr.getShape(j - tmpX, i - tmpY) != 0) {
							curr.rotate();
							curr.rotate();
							curr.rotate();
							return true;
						}
					}
				}
				curr.rotate();
				curr.rotate();
				curr.rotate();
				return false;

			}
			else{
				curr.rotate();
				curr.rotate();
				curr.rotate();
				return true;
			}
		}
		//System.out.println();
		return false;
	}

	private void rotateblock() {
		eraseCurr();
		if(!isBlocked('t')) {
			curr.rotate();
			moveCenter();
		}
		placeBlock();
	}

	protected void eraseRow() {

		int lowest = y + curr.height() -1;
		for(int i = lowest; i>=y; i--){
			boolean canErase = true;
			for(int j = 0; j < WIDTH; j++){
				if(board[i][j] == 0)
				{
					canErase = false;
					break;
				}
			}
			if(canErase) {
				score += 1;
				sprint+=20;
				for(int j = 0; j<WIDTH; j++) {
					board[i][j] = 0;
				}
			}
		}
		//System.out.print(score);
		//System.out.println(sprint);

		for(int i = lowest; i>=0; i--){
			down(i);
		}


	}

	protected void down(int row) {
		boolean canDown = true;
		boolean haveBlock = false;
		int swapRow = row + 1;

		while(swapRow < HEIGHT) {
			for (int j = 0; j < WIDTH; j++) {
				if (board[swapRow][j] != 0) {
					canDown = false;
					break;
				}
				if(board[row][j] != 0)	haveBlock = true; // D없애도 되는 지 확인
			}
			if(canDown && haveBlock) swapRow++;
			else break;
		}

		swapRow--;
		for(int j = 0; j<WIDTH; j++){
			int temp = board[row][j];
			board[row][j] = board[swapRow][j];
			board[swapRow][j] = temp;
		}
	}

	protected void moveDown() { //구조를 조금 바꿈 갈수잇는지 먼저 확인후에 갈수있으면 지우고 이동
		if(!isBlocked('d')) {
			eraseCurr();
			y++;
		}
		else {
			placeBlock();
			eraseRow();
			curr = next_block;
			next_block = getRandomBlock();
			x = 3;
			y = 0;
			if(isBlocked('d')){
				reset();
			}
		}
		placeBlock();
		drawBoard();
	}

	protected void moveRight() { //갈수있는지 함수 추가해줌
		eraseCurr();
		if(x < WIDTH - curr.width() && !isBlocked('r')) x++;
		placeBlock();
	}

	protected void moveLeft() { //똑같음
		eraseCurr();
		if(x > 0 && !isBlocked('l')) {
			x--;
		}
		placeBlock();
	}

	protected void pause() {
		if(!ispaused){
			ispaused = true;
			timer.stop();
		}
		else{
			ispaused = false;
			timer.start();
		}
	}
	protected void harddrop(){
		pause();
		eraseCurr();
		while(!isBlocked('d'))
			y++;
		placeBlock();
		drawBoard();
		moveDown();
		timer.start();
	}

	public void drawBoard() {
		int win_extra_border = 4;
		int mac_extra_border = 2;
		int extra_border;
		if(os.contains("win"))
			extra_border = win_extra_border;
		else
			extra_border = mac_extra_border;

		StringBuffer sb = new StringBuffer();
		StyledDocument doc = pane.getStyledDocument();
		StyleConstants.setForeground(styleSet, Color.WHITE);
		pane.setText("");
		try {
			for (int t = 0; t < WIDTH + extra_border; t++) {
				doc.insertString(doc.getLength(), BORDER_CHAR, styleSet);//sb.append(BORDER_CHAR);
			}
			//sb.append("\n");
			doc.insertString(doc.getLength(), "\n", styleSet);
			for (int i = 0; i < board.length; i++) {
				//sb.append(BORDER_CHAR);
				doc.insertString(doc.getLength(), BORDER_CHAR, styleSet);
				for (int j = 0; j < board[i].length; j++) {
					if (board[i][j] != 0) {
						StyleConstants.setForeground(styleSet, color_board[i][j]);
						doc.insertString(doc.getLength(), BLOCK_CHAR, styleSet);
						//sb.append(BLOCK_CHAR);
						StyleConstants.setForeground(styleSet, Color.WHITE);
					} else {
						doc.insertString(doc.getLength(), BLANK_CHAR, styleSet);
						//sb.append(BLANK_CHAR);
					}
				}
				doc.insertString(doc.getLength(), BORDER_CHAR + "\n", styleSet);
			}
			for (int t = 0; t < WIDTH + extra_border; t++) {
				doc.insertString(doc.getLength(), BORDER_CHAR, styleSet);
			}
		} catch (BadLocationException e) {
			System.out.println(e);
		}
		//pane.setText(sb.toString());
		pane.setStyledDocument(doc);
		draw_next();
		draw_score();
	}

	public void draw_score() {
		StyledDocument doc = score_pane.getStyledDocument();
		StyleConstants.setForeground(styleSet, Color.WHITE);
		doc.setParagraphAttributes(0, doc.getLength(), styleSet, false);
		StringBuffer sb = new StringBuffer();
		sb.append("\nScore : ");
		sb.append(score);
		score_pane.setText(sb.toString());
		score_pane.setStyledDocument(doc);
	}

	public void draw_next(){
		StringBuffer sb = new StringBuffer();
		StyledDocument doc = next_pane.getStyledDocument();
		StyleConstants.setForeground(styleSet, next_block.getColor());
		doc.setParagraphAttributes(0, doc.getLength(), styleSet, false);
		for(int i=0; i < NEXT_HEIGHT; i++) {
			for(int j=0; j < NEXT_WIDTH; j++) {
				if(next_board[i][j] != 0) {
					sb.append(BLOCK_CHAR);
				} else {
					sb.append(BLANK_CHAR);
				}
			}
			sb.append("\n");
		}
		next_pane.setText(sb.toString());
		next_pane.setStyledDocument(doc);
	}

	protected void moveCenter() {
		x = x + curr.getCentermovedX();
		y = y + curr.getCentermovedY();
	}

	public void reset() {
		this.board = new int[20][10];
		score = 0;
		sprint = 0;
		drawBoard();
	}

	public class PlayerKeyListener implements KeyListener {
		@Override
		public void keyTyped(KeyEvent e) {

		}

		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_DOWN:
				moveDown();
				drawBoard();
				break;
			case KeyEvent.VK_RIGHT:
				moveRight();
				drawBoard();
				break;
			case KeyEvent.VK_LEFT:
				moveLeft();
				drawBoard();
				break;
			case KeyEvent.VK_UP:
				rotateblock();
				//System.out.println("width : " + curr.width() + " height : " + curr.height());
				drawBoard();
				break;
			case 68:
				harddrop();
				break;
			case KeyEvent.VK_ESCAPE:
				pause();
				break;
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {

		}
	}
	
}
