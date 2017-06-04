package com.eduardovernier;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        Package rootPackage = new Package("");
        rootPackage.setCanvas(0, 0, 700, 700);

        JFrame frame = new JFrame("Insertion Treemap");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        TreemapPanel panel = new TreemapPanel(rootPackage);
        frame.getContentPane().add(panel);
        frame.setSize(700, 700);
        frame.setVisible(true);

        Scanner scanner = new Scanner(System.in);
        while (true) {

            String id = scanner.next();
            if (id.equals("-")) {
                break;
            }
            Double weight = scanner.nextDouble();
            rootPackage.addOrUpdateItem(new ArrayList<>(Arrays.asList(id.split("/"))),  weight);
            panel.repaint();
        }

        TimeUnit.SECONDS.sleep(1);
//
//        rootPackage.setCanvas(20, 20, 500, 200);
//        rootPackage.treemap.drawTreemap();
    }
}
