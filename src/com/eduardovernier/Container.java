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
    Rectangle oldRectangle;
    Rectangle rectangle = new Rectangle(0, 0, 0, 0);
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

    public void setRectangle(Rectangle newRectangle) {
        if (this.oldRectangle == null) {
            this.oldRectangle = newRectangle;
        } else {
            this.oldRectangle = new Rectangle(this.rectangle.x, this.rectangle.y, this.rectangle.width, this.rectangle.height);
        }
        this.rectangle = newRectangle;
    }

    public void paint(Graphics2D graphics, float animation) {

        graphics.setColor(new Color(200, 200, 200, 255));
        graphics.setStroke(new BasicStroke(1, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));
        graphics.draw(new Rectangle2D.Double(this.rectangle.x, this.rectangle.y, this.rectangle.width, this.rectangle.height));

        // graphics.drawString(String.format("%.2f", rectangle.getAspectRatio()), (int) rectangle.x + 1, (int) rectangle.y + 10);
        //  graphics.drawString(id, (int) rectangle.x + 1, (int) rectangle.y + 20);

        if (central != null) {
            central.paint(graphics, animation);
        }

        if (right != null) {
            right.paint(graphics, animation);
        }

        if (bottom != null) {
            bottom.paint(graphics, animation);
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
            double cWidth = ((this.getCentralWeight() + bottom.getFullWeight()) / (this.getCentralWeight() + bottom.getFullWeight() + right.getFullWeight())) * baseWidth;
            double cHeight = (this.getCentralWeight() / (this.getCentralWeight() + bottom.getFullWeight())) * baseHeight;
            if (Double.isNaN(cHeight) || Double.isInfinite(cHeight)) {
                cHeight = 0;
            }
            if (Double.isNaN(cWidth) || Double.isInfinite(cWidth)) {
                cWidth = 0;
            }
            this.setRectangle(new Rectangle(this.rectangle.x, this.rectangle.y, cWidth, cHeight));

            // B coordinates
            double bX = this.rectangle.x;
            double bWidth = this.rectangle.width;
            double bY = this.rectangle.y + this.rectangle.height;
            double bHeight = baseHeight - this.rectangle.height;
            bottom.setRectangle(new Rectangle(bX, bY, bWidth, bHeight));

            // R coordinates
            double rX = this.rectangle.x + this.rectangle.width;
            double rWidth = baseWidth - this.rectangle.width;
            double rY = this.rectangle.y;
            double rHeight = baseHeight;
            right.setRectangle(new Rectangle(rX, rY, rWidth, rHeight));


            right.computeTreemap();
            bottom.computeTreemap();

        } else if (right != null) {

            double baseWidth = this.rectangle.width;

            // C coordinates - Only the width changes
            double cWidth = (this.getCentralWeight() / (this.getCentralWeight() + right.getFullWeight())) * baseWidth;
            if (Double.isNaN(cWidth) || Double.isInfinite(cWidth)) {
                cWidth = 0;
            }
            this.setRectangle(new Rectangle(this.rectangle.x, this.rectangle.y, cWidth, this.rectangle.height));

            // R coordinates
            double rX = this.rectangle.x + this.rectangle.width;
            double rWidth = baseWidth - this.rectangle.width;
            double rY = this.rectangle.y;
            double rHeight = this.rectangle.height;
            right.setRectangle(new Rectangle(rX, rY, rWidth, rHeight));

            right.computeTreemap();

        } else if (bottom != null) {

            double baseHeight = this.rectangle.height;

            // C coordinates - Only the height changes
            double cHeight = (this.getCentralWeight() / (this.getCentralWeight() + bottom.getFullWeight())) * baseHeight;
            if (Double.isNaN(cHeight) || Double.isInfinite(cHeight)) {
                cHeight = 0;
            }
            this.setRectangle(new Rectangle(this.rectangle.x, this.rectangle.y, this.rectangle.width, cHeight));

            // B coordinates
            double bX = this.rectangle.x;
            double bWidth = this.rectangle.width;
            double bY = this.rectangle.y + this.rectangle.height;
            double bHeight = baseHeight - this.rectangle.height;
            bottom.setRectangle(new Rectangle(bX, bY, bWidth, bHeight));

            bottom.computeTreemap();
        }

        if (central != null) {
            central.setRectangle(new Rectangle(this.rectangle.x, this.rectangle.y, this.rectangle.width, this.rectangle.height));
            central.computeTreemap();
        }
    }
}
