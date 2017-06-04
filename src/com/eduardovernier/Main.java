package com.eduardovernier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

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
            rootPackage.treemap.drawTreemap();
        }

        int breakz = 1;

    }
}
