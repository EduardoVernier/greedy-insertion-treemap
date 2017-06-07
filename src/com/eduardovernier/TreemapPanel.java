package com.eduardovernier;

import javax.swing.*;
import java.awt.*;


public class TreemapPanel extends JPanel {

    Package rootPackage;

    public TreemapPanel(Package rootPackage) {
        this.rootPackage = rootPackage;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D g = (Graphics2D) graphics;

        paintTreemap(rootPackage, g);
    }

    private void paintTreemap(Package pack, Graphics2D g) {
        if (pack.treemap.root != null) {
            pack.treemap.root.paint(g);
        }
        for (Package childPackage : pack.packageList) {
            paintTreemap(childPackage, g);
        }
    }
}
