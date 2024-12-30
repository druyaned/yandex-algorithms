package druyaned.yandexalgorithms.train2.divb.lesson2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW1AmountEqualToMaximum {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        int MAX_N = 10001;
        int[] buf = new int[MAX_N];
        int n = 0;
        int num;
        int maxNum = 0;
        while ((num = Integer.parseInt(reader.readLine())) != 0) {
            if (maxNum < num) {
                maxNum = num;
            }
            buf[n++] = num;
        }
        int cnt = 0;
        for (int i = 0; i < n; ++i) {
            if (buf[i] == maxNum) {
                ++cnt;
            }
        }
        writer.write(cnt + "\n");
    }
    
}
