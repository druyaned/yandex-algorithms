package druyaned.yandexalgorithms.train1.l4maps;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW4Keyboard {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        int n = Integer.parseInt(reader.readLine());
        int[] limits = new int[n + 1];
        String[] words = reader.readLine().split(" ");
        for (int key = 1; key <= n; ++key) {
            limits[key] = Integer.parseInt(words[key - 1]);
        }
        int[] counts = new int[n + 1];
        int k = Integer.parseInt(reader.readLine());
        words = reader.readLine().split(" ");
        for (int i = 0; i < k; ++i) {
            int key = Integer.parseInt(words[i]);
            ++counts[key];
        }
        for (int key = 1; key <= n; ++key) {
            if (limits[key] < counts[key]) {
                writer.write("YES\n");
            } else {
                writer.write("NO\n");
            }
        }
    }
    
}
