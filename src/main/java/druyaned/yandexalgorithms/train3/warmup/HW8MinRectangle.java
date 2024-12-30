package druyaned.yandexalgorithms.train3.warmup;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW8MinRectangle {
    
    private static final char[] buf = new char[16];
    
    private static int readInt(BufferedReader reader) throws IOException {
        int c;
        while ((c = reader.read()) != -1 && c != '+' && c != '-' && !('0' <= c && c <= '9')) {}
        if (c == -1) {
            throw new NumberFormatException("empty input");
        }
        buf[0] = (char)c;
        int l = 1;
        while ((c = reader.read()) != -1 && '0' <= c && c <= '9') {
            buf[l++] = (char)c;
        }
        return Integer.parseInt(new String(buf, 0, l));
    }
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        int n = readInt(reader);
        int xMin = readInt(reader), xMax = xMin;
        int yMin = readInt(reader), yMax = yMin;
        for (int i = 1; i < n; ++i) {
            int x = readInt(reader);
            int y = readInt(reader);
            if (xMin > x) {
                xMin = x;
            }
            if (xMax < x) {
                xMax = x;
            }
            if (yMin > y) {
                yMin = y;
            }
            if (yMax < y) {
                yMax = y;
            }
        }
        writer.write(xMin + " " + yMin + " " + xMax + " " + yMax + "\n");
    }
    
}
