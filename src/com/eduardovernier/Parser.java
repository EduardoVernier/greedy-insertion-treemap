package com.eduardovernier;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Parser {

    public static List<List<Entity>> parseCSVs(String directory) {

        File[] fileList = new File(directory).listFiles();
        List<String> fileNames = new ArrayList<>();

        if (fileList != null) {
            for (File file : fileList) {
                if (file.isFile()) {
                    try {
                        fileNames.add(file.getCanonicalPath());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            System.err.println("Invalid dir path.");
            System.exit(-1);
        }

        fileNames.sort(String::compareTo);
        fileNames.sort((o1, o2) -> Integer.valueOf(o1.length()).compareTo(Integer.valueOf(o2.length())));

        List<List<Entity>> revisionList = new LinkedList<>();
        try {

            int numberOfRevisions = fileNames.size();
            for (int revision = 0; revision < numberOfRevisions; ++revision) {

                List<Entity> currentRevision = new ArrayList<>();
                BufferedReader bufferedReader = new BufferedReader(new FileReader(fileNames.get(revision)));
                String currentLine = bufferedReader.readLine();
                String[] header = currentLine.split(",");

                if (!header[0].equals("id") || !header[1].equals("weight")) {
                    System.err.println("Error parsing header - " + fileNames.get(revision));
                    System.exit(-1);
                }

                while ((currentLine = bufferedReader.readLine()) != null) {

                    String[] split = currentLine.split(",");

                    if (split.length != 2) {
                        System.err.println("Error parsing csv file");
                        System.exit(-1);
                    } else {
                        String id = split[0];
                        double weight = Double.parseDouble(split[1]);
                        currentRevision.add(new Entity(id, weight));
                    }
                }

                if (revision > 0) {
                    for (Entity pastEntity : revisionList.get(revision - 1)) {
                        if (!currentRevision.contains(pastEntity)) {
                            currentRevision.add(new Entity(pastEntity.id, 0.0));
                        }
                    }
                    for (Entity currentEntity : currentRevision) {
                        for (int pastRevision = revision - 1; pastRevision >= 0; --pastRevision) {
                            if (!revisionList.get(pastRevision).contains(currentEntity)) {
                                revisionList.get(pastRevision).add(new Entity(currentEntity.id, 0.0));
                            }
                        }
                    }
                }

                revisionList.add(currentRevision);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return revisionList;
    }
}
