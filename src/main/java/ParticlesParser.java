package main.java;

import main.java.models.Particle;
import main.java.models.Space;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.*;

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
                String[] tokens = line.split(" {4}");
                System.out.println(Arrays.toString(tokens));
                if (tokens.length != 3)
                    throw new RuntimeException("AAA"); // TODO custom exception

                double radius = Double.parseDouble(tokens[1]);
                double prop = Double.parseDouble(tokens[2]); // TODO: Ver que es esto
                Particle particle = new Particle(radius, i);
                Particle.setInteractRadius(prop);
                particleList.add(particle);
            }
        } catch (FileNotFoundException | RuntimeException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        File dynamicFile = reader.getFile("dynamic.txt");
        try(Scanner myReader = new Scanner(dynamicFile)) {
            myReader.nextLine();
            for (int i = 0; i < totalParticles; i++) {
                String line = myReader.nextLine();
                String[] tokens = line.split(" {3}");

                if (tokens.length != 3)
                    throw new RuntimeException("AAA"); // TODO custom exception

                double x = Double.parseDouble(tokens[1]);
                double y = Double.parseDouble(tokens[2]);
                particleList.get(i).setPosition(x, y);
            }
        } catch (FileNotFoundException | RuntimeException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        Space space = new Space(spaceSize, 10, Particle.getInteractRadius(), particleList);

        List<Particle> neighbours = space.getParticlesInRange(particleList.get(0), false);
        neighbours.forEach(System.out::println);
    }
}
