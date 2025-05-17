package repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileDataManager {
    public static void save(String fileName, String data) throws IOException {
        Path path = Paths.get("src/main/resources/data/" + fileName);
        Files.write(path, data.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    public static String load(String fileName) throws IOException {
        Path path = Paths.get("src/main/resources/data/" + fileName);
        if (Files.exists(path)) {
            return new String(Files.readAllBytes(path));
        }
        return null;
    }
}