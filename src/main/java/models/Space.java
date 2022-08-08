package main.java.models;

import com.sun.xml.internal.ws.wsdl.writer.document.Part;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Space {
    private final static int[][] DIRECTIONS =
            new int[][]{new int[]{-1, 0}, new int[]{-1, 1}, new int[]{0, 0}, new int[]{0, 1}, new int[]{1, 1}};

    private final Cell[][] cells;
    private final int spaceSize;
    private final int gridM;
    private final List<Particle> particleList;
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
        validateParams(spaceSize, gridM, interactionRadius, particles);

        this.cells = new Cell[gridM][gridM];
        this.spaceSize = spaceSize;
        this.gridM = gridM;
        this.positionParticles(particles);
        this.particleList = particles;
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

    public void solve(boolean isPeriodic) {
        for (Particle particle: this.particleList)
            particle.removeAllNeighbours();

        for (Particle particle: this.particleList) {
            List<Particle> neighbours = getParticlesInRange(particle, isPeriodic);
            neighbours.forEach(n -> {
                particle.addNeighbour(n);
                n.addNeighbour(particle);
            });
        }
    }

    private List<Particle> getParticlesInRange(Particle particle, boolean isPeriodic) {
        List<Particle> particlesInRange = new ArrayList<>();
        double cellSize = (double) spaceSize / gridM;
        int row = (int) (particle.getPosition().getX() / cellSize);
        int col = (int) (particle.getPosition().getY() / cellSize);

        for (int[] dir : DIRECTIONS) {
            int currRow = row + dir[0];
            int currCol = col + dir[1];

            if ((!isPeriodic && (currRow < 0 || currRow >= gridM || currCol >= gridM)) ||
                    cells[currRow][currCol] == null)
                continue;

            particlesInRange.addAll(cells[Math.floorMod(currRow, gridM)][Math.floorMod(currCol, gridM)]
                    .getParticles().stream().filter(other -> other.getId() != particle.getId()
                            && particle.isColliding(other, isPeriodic, spaceSize, gridM))
                    .collect(Collectors.toList()));
        }

        return particlesInRange;
    }

    public void bruteForceSolve(boolean isPeriodic) {
        for (Particle particle: this.particleList)
            particle.removeAllNeighbours();

        for (Particle particle: this.particleList) {
            List<Particle> neighbours = bruteForceGetParticlesInRange(particle, isPeriodic);
            neighbours.forEach(particle::addNeighbour);
        }
    }

    private List<Particle> bruteForceGetParticlesInRange(Particle particle, boolean isPeriodic) {
        return particleList.stream()
                .filter(other -> other.getId() != particle.getId()
                        && particle.isColliding(other, isPeriodic, spaceSize, gridM))
                .collect(Collectors.toList());
    }
}
