package main.java.ar.edu.itba.ss.models;

import java.util.*;

public class Particle {
    private static double INTERACT_RADIUS;
    private static long SEQ = 1;
    private final double radius;
    private Point position;
    private final double property;
    private final long id;
    private final Set<Particle> neighbours;

    public Particle(double radius, double property) {
        this.radius = radius;
        this.id = SEQ++;
        this.neighbours = new HashSet<>();
        this.property = property;
    }

    public boolean isColliding(Particle other, int spaceSize, int gridM) {
        if (this.equals(other))
            return false;
        double realDistance = position.distanceTo(other.getPosition(), spaceSize, gridM)
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

    public void setPosition(Point position) {
        this.position = position;
    }

    public long getId() {
        return id;
    }

    public double getProperty() {
        return property;
    }

    public Set<Particle> getNeighbours() {
        return neighbours;
    }

    @Override
    public String toString() {
        return "Particle{" + "id=" + id + '}';
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
