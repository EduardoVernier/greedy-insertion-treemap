package com.eduardovernier;

import java.util.ArrayList;
import java.util.List;

public class Package {

    String id;
    List<Package> packageList = new ArrayList<>();
    List<Entity> entityList = new ArrayList<>(); // Not sure if necessary, we'll see
    Treemap treemap = new Treemap();
    Rectangle canvas;

    public Package(String id) {
        this.id = id;
    }

    public void setCanvas(double x, double y, double width, double height) {
        this.canvas = new Rectangle(x, y, width, height);
        this.treemap.setCanvas(x, y, width, height);
    }

    public void addOrUpdateItem(List<String> path, Double weight) {

        if (path.size() == 0) {
            System.out.println("Zero size path."); // Shouldn't happen
        } else if (path.size() == 1) {
            String entityName = path.get(0);
            // Look for entity in entity list
            for (Entity entity : entityList) {
                if (entity.id.equals(entityName)) {
                    entity.weight = weight;
                    treemap.updateItem(entityName, weight);
                    return;
                }
            }
            // Else add it to entity list and treemap
            entityList.add(new Entity(entityName, weight));
            treemap.addItem(entityName, weight);
        } else {
            String packageName = path.remove(0);
            // Package already exists
            for (Package childPackage : packageList) {
                if (childPackage.id.equals(packageName)) {
                    childPackage.addOrUpdateItem(path, weight);
                    treemap.updateItem(packageName, childPackage.getWeight());
                    updateTreemapCoords();
                    return;
                }
            }
            // Needs to be created
            Package newPackage = new Package(packageName);
            packageList.add(newPackage);
            treemap.addItem(packageName, weight);
            updateTreemapCoords();
            newPackage.addOrUpdateItem(path, weight);
        }
    }

    private void updateTreemapCoords() {
        for (Package pack : packageList) {
            Rectangle canvas = treemap.findContainer(treemap.root, pack.id).rectangle;
            pack.setCanvas(canvas.x, canvas.y, canvas.width, canvas.height);
        }
    }


    public double getWeight() {

        double weight = 0;
        for (Entity entity : entityList) {
            weight += entity.weight;
        }
        for (Package childPackage : packageList) {
            weight += childPackage.getWeight();
        }
        return weight;
    }

    @Override
    public String toString() {
        return "Package{" +
                "id='" + id + '\'' +
                ", packageList=" + packageList +
                ", entityList=" + entityList +
                '}';
    }
}
