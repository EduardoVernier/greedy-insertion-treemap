package com.eduardovernier;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Main {

    public static JFrame frame;
    private static Rectangle canvas = new Rectangle(0, 0, 700, 700);
    private static TreemapPanel panel;

    public static void main(String[] args) throws InterruptedException {


        if (args.length > 0) {
            List<List<Entity>> revisionList = Parser.parseCSVs(args[0]);
            TreemapManager treemapManager = new TreemapManager(canvas, revisionList);
            initFrame(treemapManager);
        } else {
            // TODO Fix this later
//            Scanner scanner = new Scanner(System.in);
//            while (true) {
//
//                String id = scanner.next();
//                if (id.equals("-")) {
//                    break;
//                }
//                Double weight = scanner.nextDouble();
//                rootPackage.addOrUpdateItem(new ArrayList<>(Arrays.asList(id.split("/"))), weight);
//                panel.repaint();
//            }
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
