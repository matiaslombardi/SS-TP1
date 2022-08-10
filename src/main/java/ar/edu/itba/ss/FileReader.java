package main.java.ar.edu.itba.ss;

import java.io.File;
import java.util.Objects;

public class FileReader {
    public File getFile(String path) {
        try {
            return new File(Objects.requireNonNull(getClass().getClassLoader()
                    .getResource(path)).getFile());
        } catch (NullPointerException e) {
            throw new RuntimeException(e);
        }
    }
}
