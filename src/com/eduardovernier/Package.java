package com.eduardovernier;

import java.util.ArrayList;
import java.util.List;

public class Package {

    String id; // Package name
    List<Package> packageList = new ArrayList<>(); // Children package
    List<Entity> entityList = new ArrayList<>(); // Used to check whether a change is a insertion, modification or deletion
    Treemap treemap = new Treemap(); // Each package holds a treemap

    public Package(String id) {
        this.id = id;
    }

    public void setCanvas(double x, double y, double width, double height) {
        this.treemap.setCanvas(x, y, width, height);
    }

    public void addOrUpdateItem(List<String> path, Double weight) {

        if (path.size() == 1) {
            // Adding a leaf
            String entityName = path.get(0);
            // Look for entity in entity list
            for (Entity entity : entityList) {
                if (entity.id.equals(entityName)) { // If found, update its value and recompute treemap
                    entity.weight = weight;
                    treemap.updateItem(entityName, weight);
                    updateTreemapCoords();
                    return;
                }
            }
            // Else add it to entity list and treemap
            entityList.add(new Entity(entityName, weight));
            treemap.addItem(entityName, weight);
            updateTreemapCoords();
        } else {
            // Adding a package
            String packageName = path.remove(0);
            // Package already exists
            for (Package childPackage : packageList) {
                if (childPackage.id.equals(packageName)) {
                    childPackage.addOrUpdateItem(path, weight);
                    treemap.updateItem(packageName, childPackage.getWeight()); // Recompute treemap with updated value
                    updateTreemapCoords(); // Update this.children size
                    return;
                }
            }
            // Needs to be created
            Package newPackage = new Package(packageName);
            packageList.add(newPackage);
            treemap.addItem(packageName, weight); // Recompute treemap with new value
            updateTreemapCoords(); // Update this.children size
            newPackage.addOrUpdateItem(path, weight);
        }
    }


    public void removeItem(List<String> path) {
        if (path.size() == 1) {
            // First remove from entityList
            String entityName = path.get(0);
            for (int i = 0; i < entityList.size(); ++i) {
                if (entityList.get(i).id.equals(entityName)) {
                    entityList.remove(i);
                    break;
                }
            }
            // Find and rearrange container
            Container container = treemap.findContainer(treemap.root, entityName);

            if (container.bottom == null && container.right == null){
                container = null;
                return;
            }

            if (container.right == null && container.bottom != null){
                container.central = container.bottom;
                container.bottom = null;
                return;
            }

            if (container.bottom == null && container.right != null) {
                container.central = container.right;
                container.right = null;
                return;
            }

            if (container.bottom != null && container.right != null) {
                if (container.rectangle.width >= container.rectangle.height) {
                    container.central = container.bottom;
                    container.bottom = null;
                } else {
                    container.central = container.right;
                    container.right = null;
                }
                return;
            }
        } else {
            String packageName = path.remove(0);
            for (Package childPackage : packageList) {
                if (childPackage.id.equals(packageName)) {
                    childPackage.removeItem(path);
                }
            }
        }
    }

    private void updateTreemapCoords() {
        for (Package pack : packageList) {
            Rectangle canvas = treemap.findContainer(treemap.root, pack.id).rectangle;
            pack.setCanvas(canvas.x, canvas.y, canvas.width, canvas.height);
            pack.updateTreemapCoords();
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
