package seoultech.se.tetris.component;

import seoultech.se.tetris.component.model.DataManager;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GameBoard extends JFrame {
    public GameBoard(int x, int y, String mode) throws IOException{
        super("SeoulTech SE Tetris");
        this.setSize(DataManager.getInstance().getWeight(), DataManager.getInstance().getHeight());
        this.setLayout(new GridLayout(1,2,10,0));

        this.add(new Board(this.getLocation().x, this.getLocation().y, mode,this));
        this.add(new Board(this.getLocation().x, this.getLocation().y, mode,this));

        this.setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
    public void GameBoardDispose() {
        this.dispose();
    }
}
