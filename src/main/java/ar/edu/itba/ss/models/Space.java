package main.java.ar.edu.itba.ss.models;

import java.util.List;
import java.util.stream.Collectors;

public class Space {
    private final static int[][] DIRECTIONS = new int[][]{new int[]{-1, 0}, new int[]{-1, 1},
            new int[]{0, 0}, new int[]{0, 1}, new int[]{1, 1}};

    protected Cell[][] cells;
    private final int spaceSize;
    protected int gridM;
    protected double cellSize;
    protected List<Particle> particleList;

    public Space(int spaceSize, int gridM, double interactionRadius, List<Particle> particles) {
        /*
        double maxRadius = particles.stream()
                .max((p1, p2) -> (int) (p1.getRadius() - p2.getRadius()))
                .orElseThrow(RuntimeException::new).getRadius();

         */

        this.spaceSize = spaceSize;
        this.gridM = gridM;//(int) Math.floor(spaceSize / (interactionRadius + 2 * maxRadius));
        this.cellSize = (double) spaceSize / gridM;

        this.cells = new Cell[gridM][gridM];

        this.particleList = particles;
        this.positionParticles(particles);
    }

    protected void positionParticles(List<Particle> particles) {
        for (Particle particle : particles) {
            Point position = particle.getPosition();
            int row = getRow(position);
            int col = getCol(position);
            if (cells[row][col] == null)
                cells[row][col] = new Cell();
            cells[row][col].addParticle(particle);
        }
    }

    public void solve(boolean isPeriodic) {

        if (isPeriodic) {
            periodicSet();
        }
        else{
            setNeighbours();
        }
    }

    private void setNeighbours() {
        this.particleList.forEach(particle -> {
            Point position = particle.getPosition();
            int row = getRow(position);
            int col = getCol(position);

            for (int[] dir : DIRECTIONS) {
                int currRow = row + dir[0];
                int currCol = col + dir[1];

                if (currRow < 0 || currRow >= gridM || currCol < 0
                        || currCol >= gridM || cells[currRow][currCol] == null)
                    continue;

                cells[currRow][currCol].getParticles().stream()
                        .filter(p -> particle.isColliding(p, spaceSize, gridM))
                        .forEach(p -> {
                            particle.addNeighbour(p);
                            p.addNeighbour(particle);
                        });
            }
        });

        System.out.println("Despues hay: "+ (Integer) particleList.stream().map(p -> p.getNeighbours().size()).mapToInt(s -> s).sum());
    }

    private void periodicSet() {
        this.particleList.forEach(particle -> {
            Point position = particle.getPosition();
            int row = getRow(position);
            int col = getCol(position);

            for (int[] dir : DIRECTIONS) {
                int currRow = Math.floorMod(row + dir[0], gridM);
                int currCol = Math.floorMod(col + dir[1], gridM);

                if (cells[currRow][currCol] == null)
                    continue;

                cells[currRow][currCol].getParticles().stream()
                        .filter(p -> particle.isColliding(p, spaceSize, gridM))
                        .forEach(p -> {
                            particle.addNeighbour(p);
                            p.addNeighbour(particle);
                        });
            }
        });
    }

    public void bruteForceSolve() {
        particleList.forEach(particle -> particleList.stream()
                .filter(p -> particle.isColliding(p, spaceSize, gridM))
                .forEach(p -> {
                    particle.addNeighbour(p);
                    p.addNeighbour(particle);
                }));
    }

    private int getRow(Point position) {
        return (int) (position.getX() / cellSize);
    }

    private int getCol(Point position) {
        return (int) (position.getY() / cellSize);
    }
}
