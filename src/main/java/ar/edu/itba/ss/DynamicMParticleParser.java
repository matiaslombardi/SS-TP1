package main.java.ar.edu.itba.ss;

import main.java.ar.edu.itba.ss.models.Particle;
import main.java.ar.edu.itba.ss.models.PeriodicPoint;
import main.java.ar.edu.itba.ss.models.Point;
import main.java.ar.edu.itba.ss.models.SpaceM;
import main.java.ar.edu.itba.ss.utils.FileReader;
import main.java.ar.edu.itba.ss.utils.ParticleGenerator;
import main.java.ar.edu.itba.ss.utils.PointGetter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class DynamicMParticleParser {
    public static void main(String[] args) {
        Particle.setInteractRadius(1.0);
        PointGetter pointGetter = PeriodicPoint::new;
        //ParticleGenerator.generate("src/files/staticM.txt", "src/files/dynamicM.txt", 2.5, 1.0, 55, 200);

        FileReader reader = new FileReader();

        File staticFile = reader.getFile("staticM.txt");
        int totalParticles = 0;
        int spaceSize = 0;
        List<Particle> particleList = new ArrayList<>();
        try (Scanner myReader = new Scanner(staticFile)) {
            totalParticles = Integer.parseInt(myReader.nextLine());//.trim());
            spaceSize = Integer.parseInt(myReader.nextLine());//.trim());
            for (int i = 0; i < totalParticles; i++) {
                String line = myReader.nextLine();
                String[] tokens = line.split(" ");//.trim().split(" {4}");
                if (tokens.length != 2)
                    throw new IllegalArgumentException("Invalid static.txt file");

                double radius = Double.parseDouble(tokens[0]);
                double property = Double.parseDouble(tokens[1]);
                Particle particle = new Particle(radius, property);
                particleList.add(particle);
            }
        } catch (FileNotFoundException | NoSuchElementException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

        File dynamicFile = reader.getFile("dynamicM.txt");
        try (Scanner myReader = new Scanner(dynamicFile)) {
            while (myReader.hasNext()) {
                myReader.nextLine();
                for (int i = 0; i < totalParticles; i++) {
                    String line = myReader.nextLine();
                    String[] tokens = line.split(" ");//.trim().split(" {3}");

                    if (tokens.length != 2)
                        throw new IllegalArgumentException("Invalid dynamic.txt file");

                    double x = Double.parseDouble(tokens[0]);
                    double y = Double.parseDouble(tokens[1]);
                    particleList.get(i).setPosition(pointGetter.getPoint(x, y));
                }
            }
        } catch (FileNotFoundException | NoSuchElementException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

        HashMap<Integer, Long> times = new HashMap<>();

        double maxRadius = particleList.stream()
                .max((p1, p2) -> (int) (p1.getRadius() - p2.getRadius()))
                .orElseThrow(RuntimeException::new).getRadius();

        int maxGridM = (int) Math.floor(spaceSize / (Particle.getInteractRadius() + 2 * maxRadius));

        for (int gridM = 1; gridM <= maxGridM; gridM ++) {
            SpaceM space = new SpaceM(spaceSize, Particle.getInteractRadius(), particleList, gridM);

            long start = System.currentTimeMillis();
            space.solve(false);
            long end = System.currentTimeMillis();
//            System.out.println(start + " " + end);
            System.out.println(gridM + " " + (end - start) + "ms");
            times.put(gridM, end - start);
        }

        try (FileWriter writer = new FileWriter("outM.txt")) {
            times.forEach((k, v) -> {
                try {
                    writer.write(String.format("%d %d\n", k, v));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
}
