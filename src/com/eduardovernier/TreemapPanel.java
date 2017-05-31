package com.eduardovernier;

import javax.swing.*;
import java.awt.*;


public class TreemapPanel extends JPanel {

    Container root;
    private Rectangle canvas;

    public TreemapPanel(Container root, Rectangle canvas) {
        this.root = root;
        this.canvas = canvas;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D g = (Graphics2D) graphics;

        root.paint(g);
    }
}
