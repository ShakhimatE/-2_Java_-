package Task1;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

public class CreateAndWriteTextFile {
    public static void main(String[] args) {
        List<String> lines = Arrays.asList(
                "Это первая строка текста.",
                "Это вторая строка текста.",
                "И это третья строка текста."
        );

        Path filePath = Path.of("sample.txt");

        try {
            Files.write(filePath, lines, StandardOpenOption.CREATE);
            System.out.println("Файл успешно создан и заполнен.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

