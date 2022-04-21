package seoultech.se.tetris.blocks;

import seoultech.se.tetris.component.Board;
import seoultech.se.tetris.component.model.DataManager;

import javax.xml.crypto.Data;
import java.awt.Color;
import java.util.Arrays;
import java.util.Random;
import java.io.IOException;

public abstract class Block {
		
	protected int[][] shape;
	protected Color color;
	protected int centermoved_x;
	protected int centermoved_y;
	protected static boolean color_weak;
	protected static boolean item_mode;
	protected static boolean item;

	
	public Block() throws IOException {
		item_mode = isItem_mode();
		shape = new int[][]{ 
				{1, 1}, 
				{1, 1}
		};
		if(DataManager.getInstance().getColor_weak().equals("off"))
			color_weak = false;
		else
			color_weak = true;
		color = Color.YELLOW;
		centermoved_x = 0;
		centermoved_y = 0;
	}
	private boolean isItem_mode(){
		if(DataManager.getInstance().getMode().equals("item"))
			return true;
		else return false;
	}

	public int getCentermovedX() {
		return centermoved_x;
	}

	public int getCentermovedY() {
		return centermoved_y;
	}

	public int getShape(int x, int y) {
		return shape[y][x];
	}
	
	public Color getColor() {
		return color;
	}
	
	public void rotate() {
		int[][] rotate;
		rotate = new int[width()][height()];
		for(int i=0; i<rotate.length; i++){
			for(int j=0; j<rotate[i].length; j++)
			{
				rotate[i][j] = shape[height()-1-j][i];
				if(rotate[i][j] == 2 || rotate[i][j] == 12 || rotate[i][j] == 22){
					centermoved_y = (height() -1 -j -i);
					centermoved_x = (i - j);
				}
			}
		}
		shape = new int[rotate.length][rotate[0].length];
		for(int i=0; i<rotate.length; i++){
			for(int j=0; j<rotate[0].length; j++)
				shape[i][j] = rotate[i][j];
		}
	}
	
	public int height() {
		return shape.length;
	}
	
	public int width() {
		if(shape.length > 0)
			return shape[0].length;
		return 0;
	}

	public static void setColorWeak(boolean a){
		color_weak=a;
	}

	public static boolean getColorWeak(){
		return color_weak;
	}

	public void setItemBlock(){
		Random rn = new Random();
		int h=rn.nextInt(height());
		int w=rn.nextInt(width());
		while(shape[h][w]==0){
			h=rn.nextInt(height());
			w=rn.nextInt(width());
		}
		int a=rn.nextInt(2)+1;
		shape[h][w]+=(10*a);
//		System.out.println(Arrays.deepToString(shape));
	}
	public static void setItemMode(boolean a){item_mode=a;}

	public static boolean getItemMode(){return item_mode;}

	public static void setItem(boolean a){item=a;}

	public static boolean getItem(){return item;}
}
