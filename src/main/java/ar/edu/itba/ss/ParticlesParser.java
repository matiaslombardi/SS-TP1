package main.java.ar.edu.itba.ss;

import main.java.ar.edu.itba.ss.models.Particle;
import main.java.ar.edu.itba.ss.models.Space;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class ParticlesParser {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: ParticlesParser <RC> <output name> <isPeriodic>");
            System.exit(0);
        }
        Particle.setInteractRadius(Double.parseDouble(args[0]));
        boolean isPeriodic = Boolean.parseBoolean(args[2]);
        System.out.println(Particle.getInteractRadius());
        System.out.println(args[1]);
        System.out.println(isPeriodic);

        FileReader reader = new FileReader();

        File staticFile = reader.getFile("static.txt");
        int totalParticles = 0;
        int spaceSize = 0;
        List<Particle> particleList = new ArrayList<>();
        try (Scanner myReader = new Scanner(staticFile)) {
            totalParticles = Integer.parseInt(myReader.nextLine().trim());
            spaceSize = Integer.parseInt(myReader.nextLine().trim());
            for (int i = 0; i < totalParticles; i++) {
                String line = myReader.nextLine();
                String[] tokens = line.trim().split(" {4}");
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

        File dynamicFile = reader.getFile("dynamic.txt");
        try (Scanner myReader = new Scanner(dynamicFile)) {
            while (myReader.hasNext()) {
                myReader.nextLine();
                for (int i = 0; i < totalParticles; i++) {
                    String line = myReader.nextLine();
                    String[] tokens = line.trim().split(" {3}");

                    if (tokens.length != 2)
                        throw new IllegalArgumentException("Invalid dynamic.txt file");

                    double x = Double.parseDouble(tokens[0]);
                    double y = Double.parseDouble(tokens[1]);
                    particleList.get(i).setPosition(x, y);
                }
            }
        } catch (FileNotFoundException | NoSuchElementException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }


        Space space = new Space(spaceSize, Particle.getInteractRadius(), particleList);

        long start = System.currentTimeMillis();
        space.solve(isPeriodic);
        long end = System.currentTimeMillis();
        System.out.println("CIM time: " + (end - start) + "ms");

        try (FileWriter writer = new FileWriter(args[1])) {
            for (Particle particle : particleList) {
                Set<Particle> neighbours = particle.getNeighbours();
                StringBuilder out = new StringBuilder(String.format("%d", particle.getId()));
                for (Particle neighbour : neighbours)
                    out.append(String.format(" %d", neighbour.getId()));

                out.append("\n");
                writer.write(out.toString());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

        particleList.forEach(Particle::removeAllNeighbours);

        start = System.currentTimeMillis();
        space.bruteForceSolve(isPeriodic);
        end = System.currentTimeMillis();
        System.out.println("Brute force time: " + (end - start) + "ms");

//        particleList.forEach(p -> {
//            System.out.println(p.getId() + " " + p.getNeighbours()
//                    .stream().map(Particle::getId).collect(Collectors.toList()));
//        });

    }
}
