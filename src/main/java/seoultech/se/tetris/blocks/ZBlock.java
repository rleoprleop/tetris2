package seoultech.se.tetris.blocks;

import seoultech.se.tetris.component.Board;

import java.awt.Color;
import java.io.IOException;

public class ZBlock extends Block {
	
	public ZBlock() throws IOException {
		shape = new int[][] { 
			{1, 1, 0},
			{0, 1, 1}
		};
		if(color_weak)
			color= new Color(0xcc79a7);
		else
			color = Color.RED;
		if(getItem()){
			setItemBlock();
			Board.setCheckLine();
		}
	}
}
