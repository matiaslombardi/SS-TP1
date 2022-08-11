package main.java.ar.edu.itba.ss.utils;

import main.java.ar.edu.itba.ss.models.Point;

@FunctionalInterface
public interface PointGetter {
    Point getPoint(double x, double y);
}
