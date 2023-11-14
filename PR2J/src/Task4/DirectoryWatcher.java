package Task4;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class DirectoryWatcher {
    public static void main(String[] args) {
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();

            Path directory = Paths.get("C:\\Users\\Эльмира\\IdeaProjects\\PR2J\\Watcher");
            directory.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);

            System.out.println("Наблюдение за каталогом " + directory + " начато...");

            while (true) {
                WatchKey key;
                try {
                    key = watchService.take();
                } catch (InterruptedException ex) {
                    return;
                }

                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();

                    if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                        Path fileName = (Path) event.context();
                        System.out.println("Создан новый файл: " + fileName);
                    } else if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
                        Path fileName = (Path) event.context();
                        System.out.println("Файл изменен: " + fileName);
                        Path filePath = directory.resolve(fileName);
                        List<String> previousLines = getLines(filePath);
                        List<String> currentLines = Files.readAllLines(filePath);

                        List<String> addedLines = new ArrayList<>(currentLines);
                        addedLines.removeAll(previousLines);

                        List<String> removedLines = new ArrayList<>(previousLines);
                        removedLines.removeAll(currentLines);

                        System.out.println("Добавленные строки: " + addedLines);
                        System.out.println("Удаленные строки: " + removedLines);
                    } else if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
                        Path fileName = (Path) event.context();
                        System.out.println("Файл удален: " + fileName);
                        Path filePath = directory.resolve(fileName);
                        // реализация  вывода контрольной суммы удаленного файла
                        // невозможна, так как после удаления файла,
                        // он больше не доступен для чтения
                    }
                }

                boolean valid = key.reset();
                if (!valid) {
                    break;
                }
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    private static List<String> getLines(Path filePath) throws IOException {
        return Files.readAllLines(filePath);
    }
}
