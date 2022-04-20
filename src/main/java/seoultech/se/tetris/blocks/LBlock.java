package seoultech.se.tetris.blocks;

import seoultech.se.tetris.component.Board;

import java.awt.Color;
import java.io.IOException;

public class LBlock extends Block {
	
	public LBlock() throws IOException {
		shape = new int[][] { 
			{1, 2, 1},
			{1, 0, 0}
		};
		if(color_weak)
			color= new Color(0x730a71);
		else
			color = Color.ORANGE;
		if(item_mode && Board.getScore()>1){
			setLineCleaner();
		}
	}
}
