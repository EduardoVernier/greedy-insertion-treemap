package com.eduardovernier;

import javax.swing.*;
import java.awt.*;


public class TreemapPanel extends JPanel {

    Container root;

    public TreemapPanel(Container root) {
        this.root = root;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D g = (Graphics2D) graphics;

        root.paint(g);
    }
}
