package main.java.ar.edu.itba.ss.models;

public class PeriodicPoint extends Point {
    public PeriodicPoint(double x, double y) {
        super(x, y);
    }
    @Override
    public double distanceTo(Point other, int spaceSize, int gridM) {
        double cellSize = (double) spaceSize / gridM;

        double dx = Math.abs(x - other.getX());
        if (dx > 2 * cellSize)
            dx = spaceSize - dx;

        double dy = Math.abs(y - other.getY());
        if (dy > 2 * cellSize)
            dy = spaceSize - dy;

        return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
    }
}
