package seoultech.se.tetris.component.model;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataManagerTest {
    @BeforeEach
    void init(){
        DataManager.getInstance().resetting();
    }
    @Test
    void testsetDatamanager(){

        int rotate = DataManager.getInstance().getRotate();
        DataManager.getInstance().setRotate(1);
        assertNotEquals(rotate, DataManager.getInstance().getRotate());
        int down = DataManager.getInstance().getDown();
        DataManager.getInstance().setDown(1);
        assertNotEquals(down, DataManager.getInstance().getDown());
        int left = DataManager.getInstance().getLeft();
        DataManager.getInstance().setLeft(1);
        assertNotEquals(left, DataManager.getInstance().getLeft());
        int right = DataManager.getInstance().getRight();
        DataManager.getInstance().setRight(1);
        assertNotEquals(right, DataManager.getInstance().getRight());
        int hd = DataManager.getInstance().getHarddrop();
        DataManager.getInstance().setHarddrop(1);
        assertNotEquals(hd, DataManager.getInstance().getHarddrop());
        int pause = DataManager.getInstance().getPause();
        DataManager.getInstance().setPause(1);
        assertNotEquals(pause, DataManager.getInstance().getPause());

        String level = DataManager.getInstance().getLevel();
        DataManager.getInstance().setLevel("easy");
        assertNotEquals(level, DataManager.getInstance().getLevel());
        String cw = DataManager.getInstance().getColor_weak();
        DataManager.getInstance().setColor_weak("asdf");
        assertNotEquals(cw, DataManager.getInstance().getColor_weak());
        String display = DataManager.getInstance().getDisplay();
        DataManager.getInstance().setDisplay("small");
        assertNotEquals(display, DataManager.getInstance().getDisplay());
        String mode = DataManager.getInstance().getMode();
        DataManager.getInstance().setMode("hard");
        assertNotEquals(mode, DataManager.getInstance().getMode());

        DataManager.getInstance().resetting();
    }

}