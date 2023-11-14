package Task2;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;


public class FileCopyBenchmark {
    public static void main(String[] args) {
        String sourceFile = "sourcefile.txt";
        String destFile1 = "streamCopy.txt";
        String destFile2 = "channelCopy.txt";
        String destFile4 = "filesClassCopy.txt";

        // Метод 1: FileInputStream / FileOutputStream
        long startTime1 = System.nanoTime();
        copyUsingStream(sourceFile, destFile1);
        long endTime1 = System.nanoTime();
        printTimeAndMemory("FileInputStream / FileOutputStream", startTime1, endTime1);

        // Метод 2: FileChannel
        long startTime2 = System.nanoTime();
        copyUsingFileChannel(sourceFile, destFile2);
        long endTime2 = System.nanoTime();
        printTimeAndMemory("FileChannel", startTime2, endTime2);


        // Метод 4: Files class
        long startTime4 = System.nanoTime();
        copyUsingFilesClass(sourceFile, destFile4);
        long endTime4 = System.nanoTime();
        printTimeAndMemory("Files class", startTime4, endTime4);
    }

    private static void copyUsingStream(String source, String destination) {
        try (InputStream is = new FileInputStream(source);
             OutputStream os = new FileOutputStream(destination)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void copyUsingFileChannel(String source, String destination) {
        try (FileChannel sourceChannel = new FileInputStream(source).getChannel();
             FileChannel destChannel = new FileOutputStream(destination).getChannel()) {
            destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private static void copyUsingFilesClass(String source, String destination) {
        try {
            Path sourcePath = Path.of(source);
            Path destPath = Path.of(destination);
            Files.copy(sourcePath, destPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printTimeAndMemory(String method, long startTime, long endTime) {
        long elapsedTime = (endTime - startTime) / 1_000_000; // в миллисекундах
        System.out.println("Метод: " + method);
        System.out.println("Время выполнения: " + elapsedTime + " мс");
        Runtime runtime = Runtime.getRuntime();
        long memory = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Использованная память: " + (memory / (1024 * 1024)) + " МБ");
        System.out.println();
    }
}