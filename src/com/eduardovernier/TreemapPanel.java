package com.eduardovernier;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;


public class TreemapPanel extends JPanel {

    Package rootPackage;

    public TreemapPanel(Package rootPackage) {
        this.rootPackage = rootPackage;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D g = (Graphics2D) graphics;

        paintTreemap(rootPackage, g, 0);
    }

    private void paintTreemap(Package pack, Graphics2D g, int level) {
        if (pack.treemap.root != null) {
            pack.treemap.root.paint(g);
        }
        for (Package childPackage : pack.packageList) {
            paintTreemap(childPackage, g, level + 1);
        }
        if (pack.treemap.root != null) {
            paintTreemapBorder(g, pack.treemap.canvas, level);
        }
    }

    private void paintTreemapBorder(Graphics2D graphics, Rectangle rectangle, int level) {
        graphics.setColor(new Color(0, 0, 0, 255));
        level = (level > 2) ? 2 : level;
        graphics.setStroke(new BasicStroke(3 - level, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));
        graphics.draw(new Rectangle2D.Double(rectangle.x, rectangle.y, rectangle.width, rectangle.height));
    }
}
