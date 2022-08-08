package main.java;

import main.java.models.Particle;
import main.java.models.Space;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ParticlesParser {
    public static void main(String[] args) {
        FileReader reader = new FileReader();

        File staticFile = reader.getFile("static.txt");
        int totalParticles = 0;
        int spaceSize = 0;
        List<Particle> particleList = new ArrayList<>();
        try(Scanner myReader = new Scanner(staticFile)) {
            totalParticles = Integer.parseInt(myReader.nextLine().trim());
            spaceSize = Integer.parseInt(myReader.nextLine().trim());
            for (int i = 0; i < totalParticles; i++) {
                String line = myReader.nextLine();
                String[] tokens = line.trim().split(" {4}");
                if (tokens.length != 2)
                    throw new RuntimeException("AAA"); // TODO custom exception

                double radius = Double.parseDouble(tokens[0]);
//                double prop = Double.parseDouble(tokens[1]);
                Particle particle = new Particle(radius, i);
//                Particle.setInteractRadius(prop);
                particleList.add(particle);
            }
        } catch (FileNotFoundException | RuntimeException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        File dynamicFile = reader.getFile("dynamic.txt");
        try(Scanner myReader = new Scanner(dynamicFile)) {
            while (myReader.hasNext()) {
                myReader.nextLine();
                for (int i = 0; i < totalParticles; i++) {
                    String line = myReader.nextLine();
                    String[] tokens = line.trim().split(" {3}");

                    if (tokens.length != 2)
                        throw new RuntimeException("AAA"); // TODO custom exception

                    double x = Double.parseDouble(tokens[0]);
                    double y = Double.parseDouble(tokens[1]);
                    particleList.get(i).setPosition(x, y);
                }

                // TODO los calculos son en cada t_i


            }
        } catch (FileNotFoundException | RuntimeException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        Particle.setInteractRadius(6); // TODO config
        Space space = new Space(spaceSize, 6, Particle.getInteractRadius(), particleList);

        long start = System.currentTimeMillis();

        try (FileWriter writer = new FileWriter("out.txt")) {
            for (Particle particle : particleList) {
                List<Particle> neighbours = space.getParticlesInRange(particle, false);
                StringBuilder out = new StringBuilder(String.format("%d", particle.getId()));
                for (Particle neighbour : neighbours)
                    out.append(String.format(" %d", neighbour.getId()));

                out.append("\n");
                writer.write(out.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();
        System.out.printf("Time: %dms", end - start);

    }
}
