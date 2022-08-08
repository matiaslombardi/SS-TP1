package main.java.models;

public class Particle {
    private static double INTERACT_RADIUS;
    private final double radius;
    private Point position;
    private final int id;

    public Particle(double radius, int id) {
        this.radius = radius;
        this.id = id;
    }

    public boolean isColliding(Particle other, boolean isPeriodic, double spaceSize, double gridM) {
        double cellSize = spaceSize / gridM;

        // TODO pasar a particle.distanceTo

        double dx = Math.abs(position.getX() - other.getPosition().getX());
        if (isPeriodic && dx > 2 * cellSize)
            dx = spaceSize - dx;

        double dy = Math.abs(position.getY() - other.getPosition().getY());
        if (isPeriodic && dy > 2 * cellSize)
            dy = spaceSize - dy;

        double realDistance = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2)) - radius - other.getRadius();

//        double realDistance = position.distanceTo(other.getPosition()) - radius - other.getRadius();
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

    public Point getPosition() {
        return position;
    }

    public void setPosition(double x, double y) {
        this.position = new Point(x, y);
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Particle{" +
                "id=" + id +
                '}';
    }
}
