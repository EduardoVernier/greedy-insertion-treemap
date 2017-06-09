package com.eduardovernier;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {

    public static int revision = 0;
    public static Package rootPackage;
    private static List<List<Entity>> revisionList;
    private static TreemapPanel panel;
    static KeyListener keyListener = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent keyEvent) {
        }

        @Override
        public void keyPressed(KeyEvent keyEvent) {
        }

        @Override
        public void keyReleased(KeyEvent keyEvent) {
            if (keyEvent.getKeyCode() == KeyEvent.VK_X) {
                advanceOneRevision();
            } else if (keyEvent.getKeyCode() == KeyEvent.VK_Z) {
                returnOneRevision();
            }
        }
    };

    private static void returnOneRevision() {
        if (revision > 0) {
            revision--;
            for (Entity entity : revisionList.get(revision)) {
                addEntity(entity, false);
            }
            panel.repaint();
        }
    }

    private static void advanceOneRevision() {
        if (revision < revisionList.size() - 1) {
            revision++;
            for (Entity entity : revisionList.get(revision)) {
                addEntity(entity, true);
            }
            panel.repaint();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        rootPackage = new Package("");
        rootPackage.setCanvas(0, 0, 700, 700);

        JFrame frame = new JFrame("Insertion Treemap");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new TreemapPanel(rootPackage);
        frame.getContentPane().add(panel);
        frame.setSize(700, 700);
        frame.setVisible(true);

        if (args.length > 0) {
            frame.addKeyListener(Main.keyListener);
            revisionList = Parser.parseCSVs(args[0]);
            for (Entity entity : revisionList.get(0)) {
                addEntity(entity, true);
//                    TimeUnit.MILLISECONDS.sleep(300);
                panel.repaint();
            }

            for (int i = 1; i < revisionList.size() - 1; ++i) {
                advanceOneRevision();
                TimeUnit.SECONDS.sleep(1);
            }

        } else {
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

    private static void addEntity(Entity entity, boolean forward) {
        if (entity.weight > 0) {
            rootPackage.addOrUpdateItem(new ArrayList<>(Arrays.asList(entity.id.split("/"))), entity.weight);
        } else {
            if (forward) {
                if (entity.weight == 0.0 && revision > 0) {
                    int index = revisionList.get(revision - 1).indexOf(entity);
                    Entity entityInLastRev = revisionList.get(revision - 1).get(index);
                    if (entityInLastRev.weight > 0) { // deletion
                        rootPackage.removeItem(new ArrayList<>(Arrays.asList(entity.id.split("/"))));
                        // rootPackage.addOrUpdateItem(new ArrayList<>(Arrays.asList(entity.id.split("/"))), entity.weight);
                    }
                }
            } else {
                if (entity.weight == 0.0 && revision < revisionList.size()) {
                    int index = revisionList.get(revision + 1).indexOf(entity);
                    Entity entityInLastRev = revisionList.get(revision + 1).get(index);
                    if (entityInLastRev.weight > 0) { // deletion backwards -- addition
                        rootPackage.removeItem(new ArrayList<>(Arrays.asList(entity.id.split("/"))));
                        // rootPackage.addOrUpdateItem(new ArrayList<>(Arrays.asList(entity.id.split("/"))), entity.weight);
                    }
                }
            }
        }
    }
}
