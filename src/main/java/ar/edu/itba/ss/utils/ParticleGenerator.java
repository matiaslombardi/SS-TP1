package main.java.ar.edu.itba.ss.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class ParticleGenerator {
    public static void generate(String staticFile, String dynamicFile, double radius, double property,
                                int totalParticles, int spaceSize) {
        double max = spaceSize - radius;
        try (FileWriter writer = new FileWriter(staticFile)) {
            writer.write(totalParticles + "\n");
            writer.write(spaceSize + "\n");
            for (int i = 0; i < totalParticles; i++) {
                writer.write(radius + " " + property + "\n");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

        try (FileWriter writer = new FileWriter(dynamicFile)) {
            writer.write("0\n");
            for (int i = 0; i < totalParticles; i++) {
                writer.write(ThreadLocalRandom.current().nextDouble(radius, max) + " " +
                        ThreadLocalRandom.current().nextDouble(radius, max) + "\n");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
}
