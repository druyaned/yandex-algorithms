package druyaned.yandexalgorithms.train2.divb.lesson2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class HW5DiplomasInFolders {
    
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
        String[] elems = reader.readLine().split(" ");
        int[] diplomas = new int[n];
        for (int i = 0; i < n; ++i) {
            diplomas[i] = Integer.parseInt(elems[i]);
        }
        Arrays.sort(diplomas);
        int sum = 0;
        for (int i = 0; i < n - 1; ++i) {
            sum += diplomas[i];
        }
        writer.write(sum + "\n");
    }
    
}
