package seoultech.se.tetris.blocks;

import seoultech.se.tetris.component.Board;

import java.awt.Color;
import java.io.IOException;

public class TBlock extends Block {
	
	public TBlock() throws IOException {
		shape = new int[][] { 
			{0, 1, 0},
			{1, 2, 1}
		};
		if(color_weak)
			color= new Color(0xd5933e);
		else
			color = Color.MAGENTA;
		if(item_mode && Board.getScore()>1){
			setLineCleaner();
		}
	}
}
