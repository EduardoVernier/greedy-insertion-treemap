package com.eduardovernier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        Package rootPackage = new Package("");
        rootPackage.setCanvas(0, 0, 700, 700);
//
//        Treemap treemap = new Treemap();
//        treemap.setCanvas(0, 0, 700, 700);

        Scanner scanner = new Scanner(System.in);
        while (true) {

            String id = scanner.next();
            if (id.equals("-")) {
                break;
            }
            Double weight = scanner.nextDouble();
            rootPackage.addOrUpdateItem(new ArrayList<>(Arrays.asList(id.split("/"))),  weight);
        }

        rootPackage.treemap.drawTreemap();
        TimeUnit.SECONDS.sleep(1);

        rootPackage.setCanvas(20, 20, 500, 200);
        rootPackage.treemap.drawTreemap();
    }
}
