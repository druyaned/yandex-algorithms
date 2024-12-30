package druyaned.yandexalgorithms.train3.warmup;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW1Histogram {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        final int TABLE_SIZE = 1 << 8;
        int[] countTable = new int[TABLE_SIZE];
        int chVal;
        while ((chVal = reader.read()) != -1) {
            if (chVal != ' ' && chVal != '\n') {
                ++countTable[chVal];
            }
        }
        int n = 0;
        char[] chars = new char[TABLE_SIZE];
        int[] counts = new int[TABLE_SIZE];
        int maxCount = 0;
        for (int ch = 0; ch < TABLE_SIZE; ++ch) {
            if (countTable[ch] != 0) {
                chars[n] = (char)ch;
                counts[n++] = countTable[ch];
                if (maxCount < countTable[ch]) {
                    maxCount = countTable[ch];
                }
            }
        }
        for (int count = maxCount; count > 0; --count) {
            for (int j = 0; j < n; ++j) {
                if (count <= counts[j]) {
                    writer.write('#');
                } else {
                    writer.write(' ');
                }
            }
            writer.write('\n');
        }
        for (int i = 0; i < n; ++i) {
            writer.write(chars[i]);
        }
        writer.write('\n');
    }
    
}
