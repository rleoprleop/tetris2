package seoultech.se.tetris.component.setting;

import seoultech.se.tetris.component.Setting;
import seoultech.se.tetris.component.model.Data;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ColorWeakSetting extends JFrame {
    private Container container;
    private JPanel backButtonPanel, menuPanel;
    private JButton backButton;
    private JButton on, off;
    private Data data;

    public ColorWeakSetting(int x, int y, Data settingdata) {
        this.setSize(500, 600);
        this.setLocation(x, y);
        this.setLayout(new BorderLayout(25, 25));
        this.data = settingdata;

        setbackButtonPanel();
        setMenuPanel();

        this.add(backButtonPanel, BorderLayout.NORTH);
        this.add(menuPanel, BorderLayout.CENTER);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }
    private void setbackButtonPanel(){
        backButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backButton = new JButton("back");
        backButton.addActionListener(listner);
        backButtonPanel.add(backButton);
    }

    private void setMenuPanel(){
        menuPanel = new JPanel(new GridLayout(5,1,5,0));

        JPanel onPanel = new JPanel();
        on = new JButton("on");
        on.setPreferredSize(new Dimension(180, 60));
        on.addActionListener(listner);
        onPanel.add(on);

        JPanel offPanel = new JPanel();
        off = new JButton("off");
        off.setPreferredSize(new Dimension(180, 60));
        off.addActionListener(listner);
        offPanel.add(off);


        menuPanel.add(onPanel);
        menuPanel.add(offPanel);

    }
    ActionListener listner = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (backButton.equals(e.getSource())) {
                go_back();
            }
            else if (on.equals(e.getSource())) { // restartButton pressed
                try {
                    data.setColor_weak("on");
                    data.saveSetting();
                    go_back();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

            }
            else if (off.equals(e.getSource())) { // restartButton pressed
                try {
                    data.setColor_weak("off");
                    data.saveSetting();
                    go_back();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
    };

    private void go_back(){
        new Setting(getThis().getLocation().x, getThis().getLocation().y);
        getThis().dispose();
    }


    private JFrame getThis() {return this;}
}
