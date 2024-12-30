package druyaned.yandexalgorithms.train5.l3setmap.p03;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DeletingNumbers {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        final int MAX_NUMBER = (int)1e5;
        int numberCount = readInt(reader);
        int[] numberToCount = new int[MAX_NUMBER + 1];
        int size = 0;
        for (int i = 0; i < numberCount; i++) {
            if (numberToCount[readInt(reader)]++ == 0) {
                size++;
            }
        }
        if (size <= 1) {
            writer.write("0\n");
            return;
        }
        int first = 0;
        for (; first <= MAX_NUMBER && numberToCount[first] == 0; first++) {}
        int second = first + 1;
        for (; second <= MAX_NUMBER && numberToCount[second] == 0; second++) {}
        int maxPairSum = second - first == 1 ? numberToCount[first] + numberToCount[second] : 1;
        while (second <= MAX_NUMBER) {
            int pairSum = numberToCount[first] + numberToCount[second];
            if (second - first == 1 && maxPairSum < pairSum) {
                maxPairSum = pairSum;
            }
            first = second++;
            for (; second <= MAX_NUMBER && numberToCount[second] == 0; second++) {}
        }
        writer.write(Integer.toString(numberCount - maxPairSum) + "\n");
    }
    
    private static final char[] buf = new char[(int)2e6];
    
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
