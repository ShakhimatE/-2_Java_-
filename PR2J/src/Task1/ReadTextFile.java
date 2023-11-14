package Task1;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ReadTextFile {
    public static void main(String[] args) {
        Path currentDirectory = Path.of("");
        Path filePath = Path.of("sample.txt");
        try {
            List<String> lines = Files.readAllLines(filePath);
            for (String line : lines) {
                System.out.println(line);
            }
            System.out.println("Working directory is: " + currentDirectory.toAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}