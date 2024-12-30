package druyaned.yandexalgorithms.train3.warmup;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;

public class HW3CollectorDiego {
    
    private static final char[] buf = new char[16];
    
    private static int readInt(BufferedReader reader) throws IOException {
        int chVal = reader.read();
        while (chVal != -1 && chVal != '-' && (chVal < '0' || '9' < chVal)) {
            chVal = reader.read();
        }
        int l = 0;
        buf[l++] = (char)chVal;
        chVal = reader.read();
        while ('0' <= chVal && chVal <= '9') {
            buf[l++] = (char)chVal;
            chVal = reader.read();
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
        int[] numbers = new int[n];
        HashSet<Integer> used = new HashSet();
        int numberCount = 0;
        for (int i = 0; i < n; ++i) {
            int number = readInt(reader);
            if (used.add(number)) {
                numbers[numberCount++] = number;
            }
        }
        int k = readInt(reader);
        int[] minStickers = new int[k];
        for (int i = 0; i < k; ++i) {
            minStickers[i] = readInt(reader);
        }
        if (n == 0) {
            for (int i = 0; i < k; ++i) {
                writer.write("0\n");
            }
            return;
        }
        Arrays.sort(numbers, 0, numberCount);
        for (int i = 0; i < k; ++i) {
            int left = 0, right = numberCount - 1, mid;
            while (left < right) {
                mid = (left + right + 1) / 2;
                if (numbers[mid] < minStickers[i]) {
                    left = mid;
                } else {
                    right = mid - 1;
                }
            }
            int cnt = numbers[right] < minStickers[i] ? right + 1 : 0;
            writer.write(cnt + "\n");
        }
    }
    
}
/*
input:
5 
1 50 50 100 100
8
0 1 2 50 75 100 101 300
output:
0
0
1
1
2
2
3
3
*/