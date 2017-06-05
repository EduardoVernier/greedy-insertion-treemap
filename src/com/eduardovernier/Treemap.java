package com.eduardovernier;

public class Treemap {

    public Container root;
    public Rectangle canvas;

    public void setCanvas(double x, double y, double width, double height) {

        Rectangle newCanvas = new Rectangle(x, y, width, height);
        // Transform all rectangles
        if (canvas != null) {
            Rectangle oldCanvas = new Rectangle(canvas.x, canvas.y, canvas.width, canvas.height);
            transformRectangles(root, oldCanvas, newCanvas);
        }
        // Update canvas
        this.canvas = newCanvas;
    }

    private void transformRectangles(Container container, Rectangle oldCanvas, Rectangle newCanvas) {

        container.rectangle.reposition(oldCanvas, newCanvas);
        if (container.central != null) {
            transformRectangles(container.central, oldCanvas, newCanvas);
        }
        if (container.right != null) {
            transformRectangles(container.right, oldCanvas, newCanvas);
        }
        if (container.bottom != null) {
            transformRectangles(container.bottom, oldCanvas, newCanvas);
        }
    }


    public void addItem(String id, double value) {

        if (root == null) {
            root = new Container(id, value);
        } else {

            Container receiver = findWorstAspectRatioContainer(root);

            if (receiver.rectangle.width >= receiver.rectangle.height) {
                if (receiver.right == null) {
                    receiver.right = new Container(id, value);
                    System.out.println("Right insert " + receiver.right.id + " into " + receiver.id);
                } else {
                    if (receiver.central == null) {
                        receiver.central = new Container(receiver.id, receiver.weight);
                        receiver.central.right = new Container(id, value);
                        receiver.id = receiver.id + "_";
                    } else {
                        if (receiver.central.right == null) {
                            receiver.central.right = new Container(id, value);
                        } else {
                            System.out.print("WEIRD CENTRAL RIGHT INSERT. ");
                            Container temp = receiver.central.right;
                            receiver.central.right = new Container(id, value);
                            receiver.central.right.right = temp;
                        }
                    }
                    System.out.println("Special Right insert " + receiver.central.right.id + " into " + receiver.central);
                }
            } else {
                if (receiver.bottom == null) {
                    receiver.bottom = new Container(id, value);
                    System.out.println("Bottom insert " + receiver.bottom.id + " into " + receiver.id);
                } else {
                    if (receiver.central == null) {
                        receiver.central = new Container(receiver.id, receiver.weight);
                        receiver.central.bottom = new Container(id, value);
                        receiver.id = receiver.id + "_";
                    } else {
                        if (receiver.central.bottom == null) {
                            receiver.central.bottom = new Container(id, value);
                        } else {
                            System.out.print("WEIRD CENTRAL BOTTOM INSERT. ");
                            Container temp = receiver.central.bottom;
                            receiver.central.bottom = new Container(id, value);
                            receiver.central.bottom.bottom = temp;
                        }
                    }
                    System.out.println("Special Bottom insert " + receiver.central.bottom.id + " into " + receiver.central);
                }
            }
        }

        root.rectangle = new Rectangle(canvas.x, canvas.y, canvas.width, canvas.height);
        root.computeTreemap();
    }

    public void updateItem(String id, Double weight) {
        Container container = findContainer(root, id);
        container.weight = weight;
        root.rectangle = new Rectangle(canvas.x, canvas.y, canvas.width, canvas.height);
        root.computeTreemap();
    }

    public Container findContainer(Container container, String itemId) {

        if (container.id.equals(itemId)) {
            return container;
        } else {
            Container found = null;
            if (container.central != null) {
                Container temp = findContainer(container.central, itemId);
                if (temp != null) {
                    found = temp;
                }
            }
            if (container.right != null) {
                Container temp = findContainer(container.right, itemId);
                if (temp != null) {
                    found = temp;
                }
            }
            if (container.bottom != null) {
                Container temp = findContainer(container.bottom, itemId);
                if (temp != null) {
                    found = temp;
                }
            }
            return found;
        }
    }

    private Container findWorstAspectRatioContainer(Container container) {

        Container bestCandidate = container;
        double worstAR = container.rectangle.getAspectRatio();

        if (container.central != null) {
            Container temp = findWorstAspectRatioContainer(container.central);
            if (temp != null && temp.rectangle.getAspectRatio() < worstAR) {
                bestCandidate = temp;
                worstAR = temp.rectangle.getAspectRatio();
            }
        }

        if (container.right != null) {
            Container temp = findWorstAspectRatioContainer(container.right);
            if (temp != null && temp.rectangle.getAspectRatio() < worstAR) {
                bestCandidate = temp;
                worstAR = temp.rectangle.getAspectRatio();
            }
        }

        if (container.bottom != null) {
            Container temp = findWorstAspectRatioContainer(container.bottom);
            if (temp != null && temp.rectangle.getAspectRatio() < worstAR) {
                bestCandidate = temp;
            }
        }
        return bestCandidate;
    }
}
