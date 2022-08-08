package main.java.models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Space {
    private final Cell[][] cells;
    private int spaceSize;
    private int gridM;
    // private double interactionRadius;

    public Space(int spaceSize, int gridM, double interactionRadius, List<Particle> particles) {
        if ((double) spaceSize / gridM <= interactionRadius)
            throw new RuntimeException("L/M");

        this.cells = new Cell[gridM][gridM];
        this.spaceSize = spaceSize;
        this.gridM = gridM;
        this.positionParticles(particles);
//        this.interactionRadius = interactionRadius;
    }

    private void positionParticles(List<Particle> particles) {
        double cellSize = (double) this.spaceSize / this.gridM;
        for (Particle particle : particles) {
            Point position = particle.getPosition();
            int row = (int) (position.getX() / cellSize);
            int col = (int) (position.getY() / cellSize);
            cells[row][col].addParticle(particle);
        }
    }

    public List<Particle> getParticlesInRange(Particle particle, boolean isPeriodic) {
        List<Particle> particlesInRange = new ArrayList<>();
        double cellSize = (double) this.spaceSize / this.gridM;
        int row = (int) (particle.getPosition().getX() / cellSize);
        int col = (int) (particle.getPosition().getY() / cellSize);

        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (!isPeriodic && (i < 0 || i >= gridM || j < 0 || j >= gridM))
                    continue;

                particlesInRange.addAll(cells[Math.floorMod(i, gridM)][Math.floorMod(j, gridM)]
                        .getParticles().stream().filter(particle::isColliding)
                        .collect(Collectors.toList()));
//                for (Particle neighbour: cells[i][j].getParticles()) {
//                    if (particle.isColliding(neighbour)) {
//                        particlesInRange.add(neighbour);
//                    }
//                }
            }
        }
        return particlesInRange;
    }
}