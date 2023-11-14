package Task3;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ChecksumCalculator {
    public static short calculateChecksum(String filePath) {
        short checksum = 0;
        try (FileInputStream fis = new FileInputStream(filePath);
             FileChannel channel = fis.getChannel()) {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int bytesRead;
            while ((bytesRead = channel.read(buffer)) != -1) {
                buffer.flip();
                while (buffer.hasRemaining()) {
                    checksum = (short) ((checksum >>> 1) + ((checksum & 1) << 15));
                    checksum += buffer.get() & 0xFF;
                    checksum &= 0xFFFF;
                }
                buffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return checksum;
    }

    public static void main(String[] args) {
        String filePath = "sourcefile.txt";
        short checksum = calculateChecksum(filePath);
        System.out.println("Checksum for file " + filePath + ": " + checksum);
    }
}