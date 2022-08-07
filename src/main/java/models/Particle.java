package main.java.models;

public class Particle {
    private static double INTERACT_RADIUS;
    private double radius;
    private Point position;
    private int id;

    public Particle(double radius, int id) {
        this.radius = radius;
        this.id = id;
    }

    public boolean isColliding(Particle other) {
        double realDistance = position.distanceTo(other.getPosition()) - radius - other.getRadius();
        return Double.compare(realDistance, INTERACT_RADIUS) <= 0;
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

    public void setRadius(double radius) {
        this.radius = radius;
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

    public void setId(int id) {
        this.id = id;
    }
}
