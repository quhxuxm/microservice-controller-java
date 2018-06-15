package com.igt.interactivegame.rgs.tool.msc;

import javax.swing.*;

public class GUI {
    public static final GUI INSTANCE = new GUI();
    private JFrame mainFrame;

    private GUI() {
        this.mainFrame = new JFrame();
        this.mainFrame.setTitle("Micro Service Controller");
        this.mainFrame.pack();
    }

    public void start() {
        this.mainFrame.setVisible(true);
    }
}
