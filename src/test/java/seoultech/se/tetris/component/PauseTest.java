package seoultech.se.tetris.component;

import org.junit.jupiter.api.Test;
import seoultech.se.tetris.component.model.ScoreDataManager;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class PauseTest {
    Pause pause;
    @Test
    void pauseTest() throws IOException {
        //given
        Board board = new Board(100, 100, "normalScore");
        pause = new Pause(board);

        //when


        //then
    }

}