package main.java.ar.edu.itba.ss.models;

import java.util.List;

public class SpaceM extends Space{
    public SpaceM(int spaceSize, double interactionRadius, List<Particle> particles, int gridM) {
        super(spaceSize, interactionRadius, particles);
        this.gridM = gridM;
        this.cellSize = (double) spaceSize / gridM;
        this.cells = new Cell[gridM][gridM];
    }
}
