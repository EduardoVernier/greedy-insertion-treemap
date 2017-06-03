package com.eduardovernier;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        while (true) {

            String id = scanner.next();
            if (id.equals("-")) {
                break;
            }
            Double w = scanner.nextDouble();
            TreemapManager.addOrUpdateItem(id, w);
        }

        TreemapManager.drawTreemap();

    }
}
