package com.eduardovernier;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TreemapPanel extends JPanel implements ActionListener, KeyListener {

    Package rootPackage;
    List<List<Entity>> revisionList;
    int revision = 0;

    private Timer timer;
    private int DELAY = 30;
    private float progress = 0.0f;


    public TreemapPanel(Package rootPackage, List<List<Entity>> revisionList) {
        this.rootPackage = rootPackage;
        this.revisionList = revisionList;

        for (Entity entity : revisionList.get(0)) {
            addEntity(entity, true);
        }

        timer = new Timer(DELAY, this);
        timer.start();
    }

    private void addEntity(Entity entity, boolean forward) {
        if (entity.weight > 0) {
            rootPackage.addOrUpdateItem(new ArrayList<>(Arrays.asList(entity.id.split("/"))), entity.weight);
        } else {
            if (forward) {
                if (entity.weight == 0.0 && revision > 0) {
                    int index = revisionList.get(revision - 1).indexOf(entity);
                    Entity entityInLastRev = revisionList.get(revision - 1).get(index);
                    if (entityInLastRev.weight > 0) { // deletion
                        rootPackage.addOrUpdateItem(new ArrayList<>(Arrays.asList(entity.id.split("/"))), entity.weight);
                    }
                }
            } else {
                if (entity.weight == 0.0 && revision < revisionList.size()) {
                    int index = revisionList.get(revision + 1).indexOf(entity);
                    Entity entityInLastRev = revisionList.get(revision + 1).get(index);
                    if (entityInLastRev.weight > 0) { // deletion backwards -- addition
                        rootPackage.addOrUpdateItem(new ArrayList<>(Arrays.asList(entity.id.split("/"))), entity.weight);
                    }
                }
            }
        }
    }

    private void returnOneRevision() {
        rootPackage.updateAnimationCoords();
        if (revision > 0) {
            revision--;
            for (Entity entity : revisionList.get(revision)) {
                addEntity(entity, false);
            }
        }
        progress = 0;
    }

    private void advanceOneRevision() {
        rootPackage.updateAnimationCoords();
        if (revision < revisionList.size() - 1) {
            revision++;
            for (Entity entity : revisionList.get(revision)) {
                addEntity(entity, true);
            }
        }
        progress = 0;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D g = (Graphics2D) graphics;

        paintTreemapEntities(rootPackage, g);
        paintTreemapBorders(rootPackage, g, 0);

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

        if (pack.getWeight() > 0 && pack.treemap.canvas != null && pack.treemap.oldCanvas != null) {
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
    public void keyTyped(KeyEvent keyEvent) {
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_X) {
            advanceOneRevision();
        } else if (keyEvent.getKeyCode() == KeyEvent.VK_Z) {
            returnOneRevision();
        }
    }
}
