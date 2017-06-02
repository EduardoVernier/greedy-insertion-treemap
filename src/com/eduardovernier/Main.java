package com.eduardovernier;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {

    private static List<Double> items;
    private static Container root;
    private static Rectangle canvas = new Rectangle(0, 0, 500, 500);

    public static void main(String[] args) {

        items = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        while (true) {

            Double w = scanner.nextDouble();
            items.add(w);
            if (root == null) {
                root = new Container(String.valueOf(items.size()), w);
                root.rectangle = canvas;
            } else {

                Container receiver = findWorstAspectRatioContainer(root);

                if (receiver.rectangle.width >= receiver.rectangle.height) {
                    if (receiver.right == null) {
                        receiver.right = new Container(String.valueOf(items.size()), w);
                        System.out.println("Right insert " + receiver.right.id + " into " + receiver.id);
                    } else {
                        receiver.central = new Container(receiver.id + "_m", receiver.weight);
                        receiver.central.right = new Container(String.valueOf(items.size()), w);
                        System.out.println("Special Bottom insert " + receiver.central.right.id + " into " + receiver.central);
                    }
                } else {
                    if (receiver.bottom == null) {
                        receiver.bottom = new Container(String.valueOf(items.size()), w);
                        System.out.println("Bottom insert " + receiver.bottom.id + " into " + receiver.id);
                    } else {
                        receiver.central = new Container(receiver.id + "_m", receiver.weight + w);
                        receiver.central.bottom = new Container(String.valueOf(items.size()), w);
                        System.out.println("Special Bottom insert " + receiver.central.bottom.id + " into " + receiver.central);
                    }
                }


            }

            root.rectangle = new Rectangle(canvas.x, canvas.y, canvas.width, canvas.height);
            root.computeTreemap();

            drawTreemap();

//            SwingUtilities.invokeLater(() -> drawTreemap());
//            try {
//                TimeUnit.SECONDS.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            int breaaaak = 1;
        }
    }

    private static void updateTreeWeights(Container container) {
        // Make a insertion that updates whole tree later
        container.weight = container.getFullWeight();

        if (container.right != null) {
            updateTreeWeights(container.right);
        }

        if (container.bottom != null) {
            updateTreeWeights(container.bottom);
        }
    }

    private static void drawTreemap() {

        JFrame frame = new JFrame("Insertion Treemap");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        TreemapPanel panel = new TreemapPanel(root, canvas);
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


    private static void computeTreemap(Container container) {

//        Rectangle rectangle = container.rectangle;
//
//        if (container.central == null && container.right == null && container.bottom == null) {
//            return;
//        } else if (container.right != null && container.bottom != null) {
//
//            double leftCut = ((container.weight + container.bottom.getFullWeight()) / (container.weight + container.right.getFullWeight() + container.bottom.getFullWeight())) * rectangle.width;
//            double bottomCut = (container.weight / (container.weight + container.bottom.getFullWeight())) * rectangle.height;
//
//            container.rectangle = new Rectangle(rectangle.x, rectangle.y,
//                    leftCut, bottomCut);
//
//            container.right.rectangle = new Rectangle(rectangle.x + leftCut, rectangle.y,
//                    rectangle.width - leftCut, rectangle.height);
//
//            container.bottom.rectangle = new Rectangle(rectangle.x, rectangle.y + bottomCut,
//                    leftCut, rectangle.height - bottomCut);
//
//
//            if (container.central != null) {
//                container.central.rectangle = container.rectangle;
//                computeTreemap(container.central);
//            }
//
//            computeTreemap(container.right);
//            computeTreemap(container.bottom);
//        } else if (container.right != null && container.bottom == null) {
//
//            double leftWidth = (container.weight / (container.right.getFullWeight())) * rectangle.width;
//            container.rectangle = new Rectangle(rectangle.x, rectangle.y,
//                    leftWidth, rectangle.height);
//            container.right.rectangle = new Rectangle(rectangle.x + leftWidth, rectangle.y,
//                    rectangle.width - leftWidth, rectangle.height);
//
//            if (container.central != null) {
//                container.central.rectangle = container.rectangle;
//                computeTreemap(container.central);
//            }
//
//
//            computeTreemap(container.right);
//        }
//
//        if (container.bottom != null && container.right == null) {
//
//            double topHeight = (container.weight / (container.bottom.getFullWeight())) * rectangle.height;
//            container.rectangle = new Rectangle(rectangle.x, rectangle.y,
//                    rectangle.width, rectangle.y + topHeight);
//            container.bottom.rectangle = new Rectangle(rectangle.x, rectangle.y + topHeight,
//                    rectangle.width, rectangle.height - topHeight);
//
//            if (container.central != null) {
//                container.central.rectangle = container.rectangle;
//                computeTreemap(container.central);
//            }
//
//            computeTreemap(container.bottom);
//        }
    }
}
