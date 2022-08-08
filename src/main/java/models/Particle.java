package main.java.models;

import java.util.*;

public class Particle {
    private static double INTERACT_RADIUS;
    private final double radius;
    private Point position;
    private final int id;
    private final Set<Particle> neighbours;

    public Particle(double radius, int id) {
        this.radius = radius;
        this.id = id;
        this.neighbours = new HashSet<>();
    }

    public boolean isColliding(Particle other, boolean isPeriodic, double spaceSize, int gridM) {
        double realDistance = position.distanceTo(other.getPosition(), isPeriodic, spaceSize, gridM)
                - radius - other.getRadius();

        return Double.compare(realDistance, INTERACT_RADIUS) <= 0;
    }

    public void addNeighbour(Particle neighbour) {
        neighbours.add(neighbour);
    }

    public void removeAllNeighbours() {
        neighbours.clear();
    }

    public static double getInteractRadius() {
        return INTERACT_RADIUS;
    }

    public static void setInteractRadius(double interactRadius) {
        INTERACT_RADIUS = interactRadius;
    }

    public double getRadius() {
        return radius;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(double x, double y) {
        this.position = new Point(x, y);
    }

    public int getId() {
        return id;
    }

    public Set<Particle> getNeighbours() {
        return neighbours;
    }

    @Override
    public String toString() {
        return "Particle{" +
                "id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Particle particle = (Particle) o;
        return getId() == particle.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
