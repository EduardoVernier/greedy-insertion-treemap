package com.eduardovernier;

public class Entity {
    String id;
    Double weight;

    public Entity(String id, Double weight) {
        this.id = id;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "id='" + id + '\'' +
                ", weight=" + weight +
                '}';
    }
}
