package com.eduardovernier;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static JFrame frame;
    private static Rectangle canvas = new Rectangle(0, 0, 1000, 1000);
    private static TreemapPanel panel;

    public static void main(String[] args) throws InterruptedException {

        if (args.length > 0) {
            // Normal mode (read dataset from disk)
            List<List<Entity>> revisionList = Parser.parseCSVs(args[0]);
            TreemapManager treemapManager = new TreemapManager(canvas, revisionList);
            initFrame(treemapManager);

//            while (true) {
//                TimeUnit.MILLISECONDS.sleep(100);
//                treemapManager.advanceOneRevision();
//                panel.repaint();
//            }
        } else {
            // Interactive mode
            Scanner scanner = new Scanner(System.in);
            TreemapManager treemapManager = new TreemapManager(canvas);
            initFrame(treemapManager);
            while (true) {
                String id = scanner.next();
                if (id.equals("-")) {
                    break;
                }
                Double weight = scanner.nextDouble();
                treemapManager.addEntity(new Entity(id, weight), true);
            }
        }
    }

    private static void initFrame(TreemapManager treemapManager) {
        panel = new TreemapPanel(treemapManager);
        panel.setBackground(new Color(200, 200, 200, 255));
        frame = new JFrame("Insertion Treemap - Revision 0");
        frame.getContentPane().add(panel);
        frame.addKeyListener(panel);
        frame.setSize((int) canvas.width, (int) canvas.height);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
