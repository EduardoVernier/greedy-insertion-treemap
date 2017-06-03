package com.eduardovernier;

import javax.swing.*;

public class TreemapManager {

    private static Container root;
    private static Rectangle canvas = new Rectangle(0, 0, 700, 700);

    public static void addOrUpdateItem(String id, double value) {

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
                        receiver.central = new Container(receiver.id + "_m", receiver.weight);
                        receiver.central.right = new Container(id, value);
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
                        receiver.central = new Container(receiver.id + "_m", receiver.weight);
                        receiver.central.bottom = new Container(id, value);
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


    public static void drawTreemap() {

        JFrame frame = new JFrame("Insertion Treemap");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        TreemapPanel panel = new TreemapPanel(root);
        frame.getContentPane().add(panel);
        frame.setSize(700, 700);
        frame.setVisible(true);
    }

    private static Container findWorstAspectRatioContainer(Container container) {

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
