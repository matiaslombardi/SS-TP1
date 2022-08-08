package main.java.models;

public class Point {
    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double distanceTo(Point other, boolean isPeriodic, double spaceSize, int gridM) {
        double cellSize = spaceSize / gridM;

        double dx = Math.abs(x - other.getX());
        if (isPeriodic && dx > 2 * cellSize)
            dx = spaceSize - dx;

        double dy = Math.abs(y - other.getY());
        if (isPeriodic && dy > 2 * cellSize)
            dy = spaceSize - dy;

        return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
