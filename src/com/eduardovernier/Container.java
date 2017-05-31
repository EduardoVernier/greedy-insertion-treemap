package com.eduardovernier;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Container {

    String id;
    private Double weight;
    Rectangle rectangle;
    Container central; // Container may contain another central inside it
    Container right;
    Container bottom;

    public boolean isLeaf() {
        return central == null && right == null && bottom == null;
    }

    public Container(String id, Double weight) {
        this.id = id;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Container{" +
                "id='" + id + '\'' +
                ", weight=" + weight +
                '}';
    }

    public void paint(Graphics2D graphics) {

        if (weight > 0) {
            graphics.setColor(new Color(0, 0, 0, 255));
            graphics.setStroke(new BasicStroke(2, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));
            graphics.draw(new Rectangle2D.Double(rectangle.x, rectangle.y, rectangle.width, rectangle.height));
            graphics.drawString(String.format("%.2f", rectangle.getAspectRatio()), (int) rectangle.x, (int) rectangle.y + 10);
        }

        if (central != null) {
            central.paint(graphics);
        }

        if (right != null) {
            right.paint(graphics);
        }

        if (bottom != null) {
            bottom.paint(graphics);
        }
    }

    public double getFullWeight() {

        double fullWeight = this.weight;
        if (central != null) {
            fullWeight += central.getFullWeight();
        }

        if (right != null) {
            fullWeight += right.getFullWeight();
        }

        if (bottom != null) {
            fullWeight += bottom.getFullWeight();
        }
        return fullWeight;
    }

    public double getCentralWeight() {

        if (weight != -1) {
            return weight;
        } else {
            double centralWeight = 0;
            if (central != null) {
                centralWeight += central.getFullWeight();
            }
            return centralWeight;
        }
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
}
