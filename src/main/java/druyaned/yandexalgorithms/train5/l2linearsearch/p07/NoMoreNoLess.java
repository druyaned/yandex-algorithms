package druyaned.yandexalgorithms.train5.l2linearsearch.p07;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class NoMoreNoLess {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        final int MAX_ARRAY_SIZE = (int)10e5;
        int testCount = readInt(reader);
        int[] a = new int[MAX_ARRAY_SIZE];
        for (int test = 0; test < testCount; test++) {
            int n = readInt(reader);
            int cutCount = 0;
            int minElementInCut = n;
            int cutLength = 0;
            for (int i = 0; i < n; i++) {
                int element = readInt(reader);
                if (minElementInCut > element) {
                    minElementInCut = element;
                }
                if (minElementInCut >= cutLength + 1) {
                    cutLength++;
                } else {
                    a[cutCount] = cutLength;
                    minElementInCut = element;
                    cutLength = 1;
                    cutCount++;
                }
            }
            a[cutCount++] = cutLength;
            writer.write(String.format("%d\n%d", cutCount, a[0]));
            for (int i = 1; i < cutCount; i++) {
                writer.write(" " + a[i]);
            }
            writer.write('\n');
        }
    }
    
    private static final char[] buf = new char[16];
    private static int readInt(Reader reader) throws IOException {
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
    
}
