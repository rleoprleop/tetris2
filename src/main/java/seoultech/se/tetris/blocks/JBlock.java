package seoultech.se.tetris.blocks;

import seoultech.se.tetris.component.Board;

import java.awt.Color;
import java.io.IOException;

public class JBlock extends Block {
	
	public JBlock() throws IOException {
		shape = new int[][] { 
				{1, 2, 1},
				{0, 0, 1}
		};
		if(color_weak)
			color= new Color(0x0072b1);
		else
			color = Color.BLUE;
		if(item_mode && Board.getScore()>1){
			setLineCleaner();
		}
	}
}
