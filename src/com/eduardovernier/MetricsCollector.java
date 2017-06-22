package com.eduardovernier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MetricsCollector {

    static List<HashMap<String, Rectangle>> rectangles = new ArrayList<HashMap<String, Rectangle>>();

    public static void init(int nRevisions) {
        for (int i = 0; i < nRevisions; ++i) {
            rectangles.add(new HashMap<String, Rectangle>());
        }
    }

    public static void saveAspectRatios(Package pack, int revision) {

        if (pack.treemap != null) {
            saveAspectRatios(pack.treemap.root, revision);
        }

        for (Package childPackage : pack.packageList) {
            saveAspectRatios(childPackage, revision);
        }
    }

    private static void saveAspectRatios(Container container, int revision) {

        if (container.rectangle != null && container.kind == Kind.LEAF && !container.id.endsWith("_") && !container.id.equals("")) {
            rectangles.get(revision).put(container.id, container.rectangle);
        }

        if (container.central != null) {
            saveAspectRatios(container.central, revision);
        }

        if (container.right != null) {
            saveAspectRatios(container.right, revision);
        }

        if (container.bottom != null) {
            saveAspectRatios(container.bottom, revision);
        }
    }

    public static void saveRectangle(String id, Rectangle rectangle, int revision) {
        rectangles.get(revision).put(id, rectangle);
    }

    public static void getAspectRatioAverage(int revision) {
        double sum = 0;
        for (Map.Entry<String, Rectangle> entry : rectangles.get(revision).entrySet()) {
            sum += entry.getValue().getAspectRatio();
        }
        System.out.println(sum/rectangles.get(revision).size());
    }

    public static void getMovementAverage(int revision) {
        if (revision == 0) {
            System.out.println("0");
        } else {
            double sum = 0;
            double n = 0;
            for (Map.Entry<String, Rectangle> entry : rectangles.get(revision).entrySet()) {
                Rectangle currentRectangle = entry.getValue();
                Rectangle pastRectangle = rectangles.get(revision-1).get(entry.getKey());
                if (pastRectangle != null) {
                    double currentX = currentRectangle.x + currentRectangle.width / 2;
                    double currentY = currentRectangle.y + currentRectangle.height / 2;
                    double pastX = pastRectangle.x + pastRectangle.width / 2;
                    double pastY = pastRectangle.y + pastRectangle.height / 2;
                    double dist = Math.sqrt(Math.pow(currentX - pastX, 2) + Math.pow(currentY - pastY, 2));
                    sum += dist;
                    n++;
                }
            }
            System.out.println(sum/n);
        }

    }
}
