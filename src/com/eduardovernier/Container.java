package com.eduardovernier;

import java.awt.*;
import java.awt.geom.Rectangle2D;

//  ---------
//  | C |   |
//  |---| R |
//  | B |   |
//  ---------


public class Container {

    String id;
    Double weight;
    Rectangle rectangle = new Rectangle(0,0,0,0);
    Container central; // Container may contain another central inside it
    Container right;
    Container bottom;

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
            // graphics.drawString(String.format("%.2f", rectangle.getAspectRatio()), (int) rectangle.x + 1, (int) rectangle.y + 10);
            // graphics.drawString(id, (int) rectangle.x + 1, (int) rectangle.y + 20);
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
        if (central == null) {
            return weight;
        } else {
            return central.getFullWeight();
        }
    }

    public void computeTreemap() {

        if (right != null && bottom != null) {

            double baseWidth = this.rectangle.width;
            double baseHeight = this.rectangle.height;
            // C coordinates
            this.rectangle.width = ((this.getCentralWeight() + bottom.getFullWeight()) / (this.getCentralWeight() + bottom.getFullWeight() + right.getFullWeight())) * baseWidth;
            this.rectangle.height = (this.getCentralWeight() / (this.getCentralWeight() + bottom.getFullWeight())) * baseHeight;

            // B coordinates
            bottom.rectangle.x = this.rectangle.x;
            bottom.rectangle.width = this.rectangle.width;
            bottom.rectangle.y = this.rectangle.y + this.rectangle.height;
            bottom.rectangle.height = baseHeight - this.rectangle.height;

            // R coordinates
            right.rectangle.x = this.rectangle.x + this.rectangle.width;
            right.rectangle.width = baseWidth - this.rectangle.width;
            right.rectangle.y = this.rectangle.y;
            right.rectangle.height = baseHeight;

            right.computeTreemap();
            bottom.computeTreemap();

        } else if (right != null) {

            double baseWidth = this.rectangle.width;

            // C coordinates - Only the width changes
            this.rectangle.width = (this.getCentralWeight() / (this.getCentralWeight() + right.getFullWeight())) * baseWidth;

            // R coordinates
            right.rectangle.x = this.rectangle.x + this.rectangle.width;
            right.rectangle.width = baseWidth - this.rectangle.width;
            right.rectangle.y = this.rectangle.y;
            right.rectangle.height = this.rectangle.height;

            right.computeTreemap();

        } else if (bottom != null) {

            double baseHeight = this.rectangle.height;

            // C coordinates - Only the height changes
            this.rectangle.height = (this.getCentralWeight() / (this.getCentralWeight() + bottom.getFullWeight())) * baseHeight;

            // B coordinates
            bottom.rectangle.x = this.rectangle.x;
            bottom.rectangle.width = this.rectangle.width;
            bottom.rectangle.y = this.rectangle.y + this.rectangle.height;
            bottom.rectangle.height = baseHeight - this.rectangle.height;

            bottom.computeTreemap();
        }


        if (central != null) {
            central.rectangle = new Rectangle(this.rectangle.x, this.rectangle.y, this.rectangle.width, this.rectangle.height);
            central.computeTreemap();
        }

    }
}
