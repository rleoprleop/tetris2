package seoultech.se.tetris.component;

import org.junit.jupiter.api.Test;
import seoultech.se.tetris.component.model.ScoreDataManager;

import static org.junit.jupiter.api.Assertions.*;

class EndGameTest {
    EndGame endgame;
    @Test
    void print(){
        endgame = new EndGame(500,500, 30, ScoreDataManager.getInstance().getNormKey());
        System.out.println("test");
    }

}