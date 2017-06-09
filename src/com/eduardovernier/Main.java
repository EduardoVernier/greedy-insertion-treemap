package com.eduardovernier;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static Package rootPackage;
    private static JFrame frame;
    private static TreemapPanel panel;

    public static void main(String[] args) throws InterruptedException {

        rootPackage = new Package("");
        rootPackage.setCanvas(0, 0, 700, 700);

        if (args.length > 0) {
            List<List<Entity>> revisionList = Parser.parseCSVs(args[0]);
            initFrame(revisionList);
        } else {
            // TODO Fix this later
            Scanner scanner = new Scanner(System.in);
            while (true) {

                String id = scanner.next();
                if (id.equals("-")) {
                    break;
                }
                Double weight = scanner.nextDouble();
                rootPackage.addOrUpdateItem(new ArrayList<>(Arrays.asList(id.split("/"))), weight);
                panel.repaint();
            }
        }
    }

    private static void initFrame(List<List<Entity>> revisionList) {
        panel = new TreemapPanel(rootPackage, revisionList);
        frame = new JFrame("Insertion Treemap");
        frame.getContentPane().add(panel);
        frame.addKeyListener(panel);
        frame.setSize(700, 700);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
