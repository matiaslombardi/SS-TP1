package main.java.models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Space {
    private final Cell[][] cells;
    private int spaceSize;
    private int gridM;
    // private double interactionRadius;

    private void validateParams(int spaceSize, int gridM, double interactionRadius,
                                   List<Particle> particles) {
        double maxRadius = particles.stream()
                .max((p1, p2) -> (int) (p1.getRadius() - p2.getRadius()))
                .orElseThrow(RuntimeException::new).getRadius();

        if ((double) spaceSize / gridM <= interactionRadius + 2 * maxRadius)
            throw new RuntimeException("L/M");
    }

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
        double cellSize = (double) spaceSize / gridM;
        for (Particle particle : particles) {
            Point position = particle.getPosition();
            int row = (int) (position.getX() / cellSize);
            int col = (int) (position.getY() / cellSize);
            if (cells[row][col] == null)
                cells[row][col] = new Cell();
            cells[row][col].addParticle(particle);
        }
    }

    public List<Particle> getParticlesInRange(Particle particle, boolean isPeriodic) {
        List<Particle> particlesInRange = new ArrayList<>();
        double cellSize = (double) spaceSize / gridM;
        int row = (int) (particle.getPosition().getX() / cellSize);
        int col = (int) (particle.getPosition().getY() / cellSize);

        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                int currRow = Math.floorMod(i, gridM);
                int currCol = Math.floorMod(j, gridM);
                if (cells[currRow][currCol] == null
                        || (!isPeriodic && (i < 0 || i >= gridM || j < 0 || j >= gridM)))
                    continue;

                particlesInRange.addAll(cells[currRow][currCol].getParticles().stream().
                        filter(other -> other.getId() != particle.getId()
                                && particle.isColliding(other, isPeriodic, spaceSize, gridM))
                        .collect(Collectors.toList()));
            }
        }
        return particlesInRange;
    }
}
