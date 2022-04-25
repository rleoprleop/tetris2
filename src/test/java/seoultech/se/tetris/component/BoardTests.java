package seoultech.se.tetris.component;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import seoultech.se.tetris.blocks.Block;
import seoultech.se.tetris.blocks.IBlock;
import seoultech.se.tetris.component.model.DataManager;
import seoultech.se.tetris.component.model.ScoreDataManager;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class BoardTests {

    Board board;

    @BeforeEach
    void setUp() throws IOException {
        board = new Board(500,500, ScoreDataManager.getInstance().getNormKey());

    }

    @Test
    @DisplayName("설정이 normal인 경우")
    void getRandomBlock() throws IOException {
        board.settingLevel("normal");
        int iblockNum = 0, othersblockNum = 0;
        for(int i=0; i<70000; i++) {
            Block random = board.getRandomBlock();
            if (random.width() == new IBlock().width())
                iblockNum++;
            else
                othersblockNum++;

        }
        othersblockNum /= 6;
        System.out.println("IBlock is more generate " + (float) (iblockNum - othersblockNum)/othersblockNum * 100 + "% than otherblock\n");
    }

    @Test
    @DisplayName("설정이 easy인 경우")
    void getRandomBlock2() throws IOException {
        board.settingLevel("easy");
        int iblockNum = 0, othersblockNum = 0;
        for(int i=0; i<70000; i++) {
            Block random = board.getRandomBlock();
            if (random.width() == new IBlock().width())
                iblockNum++;
            else
                othersblockNum++;

        }
        othersblockNum /= 6;
        System.out.println("IBlock is more generate " + (float) (iblockNum - othersblockNum)/othersblockNum * 100 + "% than otherblock\n");

    }
    @Test
    @DisplayName("설정이 hard인 경우")
    void gerRandomBlock3() throws  IOException {
        board.settingLevel("hard");
        int iblockNum = 0, othersblockNum = 0;
        for(int i=0; i<70000; i++) {
            Block random = board.getRandomBlock();
            if (random.width() == new IBlock().width())
                iblockNum++;
            else
                othersblockNum++;

        }
        othersblockNum /= 6;
        System.out.println("IBlock is more generate " + (float) (iblockNum - othersblockNum)/othersblockNum * 100 + "% than otherblock\n");

    }
}