package com.eduardovernier;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Main {

    public static JFrame frame;
    public static String outputDir;
    private static Rectangle canvas;
    private static TreemapPanel panel;

    public static void main(String[] args) throws InterruptedException {

        if (args.length == 4) { // As specified by Max

            String inputDir = args[0];
            int width = Integer.valueOf(args[1]);
            int height = Integer.valueOf(args[2]);
            outputDir = args[3];

            canvas = new Rectangle(0, 0, width, height);
            List<List<Entity>> revisionList = Parser.parseCSVs(inputDir);
            TreemapManager treemapManager = new TreemapManager(canvas, revisionList);
            initFrame(treemapManager);

            while (treemapManager.getRevision() < revisionList.size() - 1) {
                // TimeUnit.MILLISECONDS.sleep(100);
                treemapManager.advanceOneRevision();
                frame.setTitle("Insertion Treemap - Revision " + treemapManager.getRevision());
                // panel.repaint();
            }

            MetricsCollector.writeRectanglesToDir(outputDir);
            frame.dispose();
        } else {
            argsError();
        }
//        else if (args.length  == 1) {
//            // Normal mode (read dataset from disk)
//            canvas = new Rectangle(0, 0, 1000, 1000);
//            List<List<Entity>> revisionList = Parser.parseCSVs(args[0]);
//            TreemapManager treemapManager = new TreemapManager(canvas, revisionList);
//            initFrame(treemapManager);
//
//            while (treemapManager.getRevision() < revisionList.size() - 1) {
//                TimeUnit.MILLISECONDS.sleep(100);
//                treemapManager.advanceOneRevision();
//                panel.repaint();
//            }
//
//            MetricsCollector.writeRectanglesToFile(args[0].split("/")[args[0].split("/").length - 1]);
//
//        } else {
//            // Interactive mode
//            Scanner scanner = new Scanner(System.in);
//            TreemapManager treemapManager = new TreemapManager(canvas);
//            initFrame(treemapManager);
//            while (true) {
//                String id = scanner.next();
//                if (id.equals("-")) {
//                    break;
//                }
//                Double weight = scanner.nextDouble();
//                treemapManager.addEntity(new Entity(id, weight), true);
//            }
//        }
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

    private static void argsError() {
        System.out.println("Usage: java -cp ./bin com.eduardovernier.Main input_dir width height output_dir");
        System.out.println("\tWidth and Height are given in pixels (integers).");
    }
}
