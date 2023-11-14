package Task2;

import java.io.FileWriter;
import java.io.IOException;

public class CreateTextFile {
    public static void main(String[] args) {
        try (FileWriter writer = new FileWriter("sourcefile.txt")) {
            writer.write("Это первая строка текста.\n");
            writer.write("Это вторая строка текста.\n");
            writer.write("И это третья строка текста.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}