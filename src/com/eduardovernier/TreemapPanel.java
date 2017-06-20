package com.eduardovernier;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;

public class TreemapPanel extends JPanel implements ActionListener, KeyListener, MouseMotionListener {

    private float progress = 0.0f;
    private TreemapManager treemapManager;

    public TreemapPanel(TreemapManager treemapManager) {
        this.treemapManager = treemapManager;
        // Timer used for screen refreshing -- 30ms
        Timer timer = new Timer(30, this);
        timer.start();
        addMouseMotionListener(this);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D g = (Graphics2D) graphics;

        paintTreemapEntities(treemapManager.getRootPackage(), g);
        paintTreemapBorders(treemapManager.getRootPackage(), g, 0);

        // Improves graphics on Linux
        Toolkit.getDefaultToolkit().sync();
    }

    private void paintTreemapEntities(Package pack, Graphics2D g) {

        if (pack.treemap.root != null) {
            pack.treemap.root.paint(g, progress);
        }

        for (Package childPackage : pack.packageList) {
            paintTreemapEntities(childPackage, g);
        }
    }

    private void paintTreemapBorders(Package pack, Graphics2D graphics, int level) {

        if (pack.treemap.oldCanvas == null) {
            pack.treemap.oldCanvas = new Rectangle(pack.treemap.canvas.x + pack.treemap.canvas.width / 2, pack.treemap.canvas.y + pack.treemap.canvas.height / 2, 1, 1);
        }

        if (pack.getWeight() > 0 && pack.treemap.canvas != null) {
            graphics.setColor(new Color(0, 0, 0, 255));
            level = (level > 3) ? 3 : level;
            graphics.setStroke(new BasicStroke(4 - level, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));
            double x = (1.0 - progress) * pack.treemap.oldCanvas.x + progress * pack.treemap.canvas.x;
            double y = (1.0 - progress) * pack.treemap.oldCanvas.y + progress * pack.treemap.canvas.y;
            double width = (1.0 - progress) * pack.treemap.oldCanvas.width + progress * pack.treemap.canvas.width;
            double height = (1.0 - progress) * pack.treemap.oldCanvas.height + progress * pack.treemap.canvas.height;
            graphics.draw(new Rectangle2D.Double(x, y, width, height));
        }

        for (Package childPackage : pack.packageList) {
            paintTreemapBorders(childPackage, graphics, level + 1);
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (progress < 0.98) {
            progress += 0.02;
            repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {}

    @Override
    public void keyPressed(KeyEvent keyEvent) {}

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_X) {
            treemapManager.advanceOneRevision();
        } else if (keyEvent.getKeyCode() == KeyEvent.VK_Z) {
            treemapManager.returnOneRevision();
        }
        Main.frame.setTitle("Insertion Treemap - Revision " + treemapManager.getRevision());
        progress = 0;
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

    }
}
